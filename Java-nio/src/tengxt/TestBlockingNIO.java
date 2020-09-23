package tengxt;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 一、使用 NIO 完成网络通信的三个核心
 *
 * 1. 通道（Channel） : 负责连接
 *      java.nio.channels.Channel 接口
 *          |- SelectableChannel
 *              |- SocketChannel
 *              |- ServerSocketChannel
 *              |- DatagramChannel
 *
 *              |- Pipe.SinkChannel
 *              |- Pipe.SourceChannel
 * 2. 缓冲区（Buffer）: 负责数据的存取
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器，用于监控是 SelectableChannel 的IO状况
 */
public class TestBlockingNIO { // 阻塞型 IO

    // 服务端
    @Test
    public void server(){
        try {
            ServerSocketChannel ssChannel = ServerSocketChannel.open();
            FileChannel outChannel = FileChannel.open(Paths.get("E:/2.jpg"),
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            ssChannel.bind(new InetSocketAddress(9999));
            SocketChannel sChannel = ssChannel.accept();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (sChannel.read(buffer) != -1){
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }

            // 发送反馈给客户端
            buffer.put("服务器接收数据成功".getBytes());
            buffer.flip();
            sChannel.write(buffer);

            // 关闭连接
            sChannel.close();
            outChannel.close();
            ssChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 客户端
    @Test
    public void client(){
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
            FileChannel inChannel = FileChannel.open(Paths.get("E:/1.jpg"), StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) != -1 ){
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }

            // 关闭发送通道，表明发送完毕
            socketChannel.shutdownOutput();

            // 接收服务端的反馈
            int len = 0;
            while ((len = socketChannel.read(buffer)) != -1){
                buffer.flip();
                System.out.println(new String(buffer.array(),0,len));
                buffer.clear();
            }

            // 关闭连接
            inChannel.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
