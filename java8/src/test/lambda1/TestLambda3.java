package test.lambda1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * java 8 内置的四大核心函数式接口
 * 
 * Consumer<T> : 消费型接口
 *  void accept(T t);
 * 
 * Supplier<T> : 供给型接口
 *  T get();
 * 
 * Function<T, R>： 函数型接口
 *  R apply(T t);
 * 
 * Predicate<T> : 断言型接口
 *  boolean test(T t);
 */
public class TestLambda3 {

    // Predicate<T> : 断言型接口
    @Test
    public void test4() {
        List<String> strList = Arrays.asList("Hello", "world", "hahaha", "xixixi", "ok");
        List<String> filterStr = filterStr(strList, (str) -> str.length() > 5);
        for (String s : filterStr) {
            System.out.println(s);
        }
    }

    // 将满足条件的字符串，放入集合中
    public List<String> filterStr(List<String> list, Predicate<String> pre) {
        List<String> retList = new ArrayList<>();
        for (String s : list) {
            if (pre.test(s)) {
                retList.add(s);
            }
        }
        return retList;
    }

    // Function<T, R>： 函数型接口
    @Test
    public void test3() {
        String newStr = strHandler("\t\t\t\t 哈哈哈哈哈    ", (str) -> str.trim());
        System.out.println(newStr);

        String subStr = strHandler("\t\t\t\t 哈哈哈哈哈    ", (str) -> str.substring(7, 10));
        System.out.println(subStr);
    }

    // 处理字符串
    public String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }


    // Supplier<T> : 供给型接口
    @Test
    public void test2() {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 10 + 1));
        for (Integer integer : numList) {
            System.out.println(integer);
        }
    }

    // 产生指定个数的整数，并放入集合中
    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> retList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            retList.add(sup.get());
        }
        return retList;
    }

    // consumer<T> 消费型接口：有参数无返回值
    @Test
    public void test1() {
        happy(1000, (m) -> System.out.println("收款：" + m + "元"));
    }

    public void happy(double money, Consumer<Double> con) {
        con.accept(money);
    }
}
