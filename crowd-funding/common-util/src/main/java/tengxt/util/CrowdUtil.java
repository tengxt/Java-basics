package tengxt.util;

import tengxt.constant.CrowdConstant;
import tengxt.exception.LoginFailedException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {
    /**
     * 判断当前请求是否为Ajax请求
     *
     * @param request 请求对象
     * @return true：是Ajax请求 false：不是Ajax对象
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 获取请求消息头
        String accept = request.getHeader("Accept");
        String header = request.getHeader("X-Requested-With");

        return (accept != null && accept.contains("application/json"))
                ||
                (header != null && header.equals("XMLHttpRequest"));
    }

    /**
     * 给字符串进行md5加密的工具方法
     *
     * @param source 传入要加密的内容
     * @return 加密后的结果
     */
    public static String md5(String source) {

        if (source == null || source.length() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        // 表示算法名
        String algorithm = "md5";
        try {
            // 得到MessageDigest对象，设置加密方式为md5
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 将获得的明文字符串转换为字节数组
            byte[] input = source.getBytes();

            // 对转换得到的字节数组进行md5加密
            byte[] output = messageDigest.digest(input);

            // 设置BigInteger的signum
            // signum : -1表示负数、0表示零、1表示正数
            int signum = 1;

            // 将字节数组转换成Big Integer
            BigInteger bigInteger = new BigInteger(signum, output);

            // 设置将bigInteger的值按照16进制转换成字符串，最后全部转换成大写，得到最后的加密结果
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();

            // 返回加密后的字符串
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
