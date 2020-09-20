package tengxt;

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
 */
public class TestChannel {
}
