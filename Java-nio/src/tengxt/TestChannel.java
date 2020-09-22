package tengxt;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * 一、通道（channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 *
 * 二、通道的主要实现类
 *  java.nio.channels.Channel 接口：
 *      |- FileChannel
 *      |- SocketChannel
 *      |- ServerSocketChannel
 *      |- DatagramChannel
 *
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 *      本地 IO：
 *      FileInputStream/FileOutputStream
 *      RandomAccessFile
 *
 *      网络 IO：
 *      Socket
 *      ServerSocket
 *      DatagramSocket
 *
 * 2. 在 JDk 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 *
 * 四、通道之间的数据传输
 *  transferFrom()
 *  transferTo()
 *
 * 五、分散(Scatter) 与聚集(Gather)
 *  分散读取（Scattering Reads）： 将通道中的数据分散到多个缓冲区
 *  聚集写入（Gathering writes）: 将多个缓存区中的数据聚集到通道中
 *
 * 六、字符集 CharSet
 *  编码： 字符串 -> 字符数组
 *  解码： 字符数组 -> 字符串
 *
 */
public class TestChannel {

    // 利用通道完成文件的复制(非直接缓冲区)
    @Test
    public void test1() {
        long start = System.currentTimeMillis();
        try {
            FileInputStream fis = new FileInputStream("E:/1.jpg");
            FileOutputStream fos = new FileOutputStream("E:/2.jpg");

            // 1. 获取通道
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();

            // 2. 分配指定大小的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            // 3. 将通道中的数据存入缓冲区
            while (inChannel.read(buffer) != -1) {
                // 切换读取数据的模式
                buffer.flip();
                // 4. 将缓冲区中的数据写入通道中
                outChannel.write(buffer);
                // 清空缓冲区
                buffer.clear();
            }

            // 关闭连接
            outChannel.close();
            inChannel.close();
            fos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间：" + (end - start));//耗费时间：24
    }

    // 使用直接缓冲区完成文件的复制(内存映射文件)
    @Test
    public void test2() {
        long start = System.currentTimeMillis();
        try {
            FileChannel inChannel = FileChannel.open(Paths.get("E:/1.jpg"), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get("E:/2.jpg"),
                    StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

            // 内存映射文件
            MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

            // 直接对缓冲区进行数据的读写操作
            byte[] dst = new byte[inMappedBuf.limit()];
            inMappedBuf.get(dst);
            outMappedBuf.put(dst);

            //  关闭连接
            outChannel.close();
            inChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间：" + (end - start));//耗费时间：18
    }

    // 通道之间的数据传输(直接缓冲区)
    @Test
    public void test3() {
        long start = System.currentTimeMillis();
        try {
            FileChannel inChannel = FileChannel.open(Paths.get("E:/1.jpg"), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get("E:/2.jpg"),
                    StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

            // inChannel.transferTo(0, inChannel.size(), outChannel);
            outChannel.transferFrom(inChannel, 0, inChannel.size());

            //  关闭连接
            outChannel.close();
            inChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间：" + (end - start));//耗费时间：15
    }

    // 输出支持的字符集
    @Test
    public void test4() {
        Map<String, Charset> maps = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = maps.entrySet();
        for (Map.Entry<String, Charset> entry : entries) {
            System.out.println(entry.getKey() + "==>" + entry.getValue());
        }
    }

    // 字符集
    @Test
    public void test5() {
        Charset gbk = Charset.forName("GBK");
        // 获取编码器
        CharsetEncoder encd = gbk.newEncoder();
        // 获取解码器
        CharsetDecoder decd = gbk.newDecoder();

        CharBuffer buf1 = CharBuffer.allocate(1024);
        buf1.put("啊哈哈哈哈哈哈");
        buf1.flip();

        // 编码
        ByteBuffer bbuf = null;
        try {
            bbuf = encd.encode(buf1);
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 12; i++) {
            System.out.println(bbuf.get());
        }

        // 解码
        bbuf.flip();
        CharBuffer cbuf = null;
        try {
            cbuf = decd.decode(bbuf);
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        System.out.println(cbuf.toString());

        System.out.println("----------");
        //
        Charset charset = Charset.forName("GBK");
        bbuf.flip();
        try {
            CharBuffer decode1 = charset.decode(bbuf);
            System.out.println(decode1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 分散和聚集
    @Test
    public void test6(){
        try {
            RandomAccessFile raf = new RandomAccessFile("F:/jsp.txt","rw");
            // 1. 获取通道
            FileChannel channel = raf.getChannel();
            // 2. 分配指定大小的缓冲区
            ByteBuffer buffer1 = ByteBuffer.allocate(100);
            ByteBuffer buffer2 = ByteBuffer.allocate(1024);

            // 3. 分散读取
            ByteBuffer[] buffers = {buffer1,buffer2};
            channel.read(buffers);
            for (ByteBuffer buffer : buffers) {
                buffer.flip();
            }
            System.out.println(new String(buffers[0].array(), 0, buffers[0].limit()));
            System.out.println("------------");
            System.out.println(new String(buffers[1].array(), 0, buffers[1].limit()));

            // 4. 聚集写入
            RandomAccessFile raf2 = new RandomAccessFile("F:/jsp2.txt","rw");
            FileChannel channel2 = raf2.getChannel();
            channel2.write(buffers);

            // 关闭连接
            channel2.close();
            raf2.close();
            channel.close();
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
