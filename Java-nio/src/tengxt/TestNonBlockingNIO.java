package tengxt;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;

/**
 * 非阻塞 IO
 */
public class TestNonBlockingNIO {

    // 服务端
    @Test
    public void server() {
        try {
            // 1. 获取通道
            ServerSocketChannel ssChannel = ServerSocketChannel.open();
            // 2. 切换非阻塞模式
            ssChannel.configureBlocking(false);
            // 3. 绑定连接
            ssChannel.bind(new InetSocketAddress(8888));
            // 4. 获取选择器
            Selector selector = Selector.open();
            // 5. 将通道注册到选择器上，并且指定“监听接收时间”
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);
            // 6. 轮询式的获取选择器上已经“准备就绪”的事件
            while (selector.select() > 0) {
                // 7. 获取当前选择器中所有注册的“选择器（已就绪的监听事件）”
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    // 8. 获取准备“就绪”的事件
                    SelectionKey sk = it.next();
                    // 9. 判断具体是什么时间准备就绪
                    if (sk.isAcceptable()) {
                        // 10. “接收就绪” 获取客户端连接
                        SocketChannel sChannel = ssChannel.accept();
                        // 11. 切换非阻塞模式
                        sChannel.configureBlocking(false);
                        // 12. 将该通道注册到选择器上
                        sChannel.register(selector, SelectionKey.OP_READ);
                    } else if (sk.isReadable()) {
                        // 13. 获取当前选择器上“读就绪”状态的通道
                        SocketChannel sChannel = (SocketChannel) sk.channel();
                        // 14. 读取数据
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int len = 0;
                        while ((len = sChannel.read(buffer)) > 0) {
                            buffer.flip();
                            // 存在不会打印客户端传递过来的数据 ???
                            System.out.println(new String(buffer.array(), 0, len));
                            buffer.clear();
                        }
                    }

                    // 15. 取消选择键SelectionKey
                    it.remove();
                }
            }

            // 关闭连接
            ssChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 客户端
    @Test
    public void client() {
        try {
            // 1. 获取通道
            SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
            // 2. 切换非阻塞模式
            sChannel.configureBlocking(false);
            // 3. 分配指定大小的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 4. 发送数据到服务端
            String str = "啊哈哈哈哈哈哈哈";
            buffer.put((new Date().toString() + "\t" + str).getBytes());
            buffer.flip();
            sChannel.read(buffer);
            buffer.clear();

            // 关闭通道
            sChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
