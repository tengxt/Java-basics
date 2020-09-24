package tengxt;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
public class TestNonBlockNIO2 {

    @Test
    public void receive() {
        try {
            DatagramChannel dChannel = DatagramChannel.open();
            dChannel.configureBlocking(false);
            dChannel.bind(new InetSocketAddress(9001));
            Selector selector = Selector.open();
            dChannel.register(selector, SelectionKey.OP_READ);
            while (selector.select() > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey sk = it.next();
                    if (sk.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        dChannel.receive(buffer);
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, buffer.limit()));
                        buffer.clear();
                    }
                }
                it.remove();
            }

            // 关闭连接
            dChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void send() {
        try {
            DatagramChannel dChannel = DatagramChannel.open();
            dChannel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            String str = "啊哈哈哈哈";
            buffer.put((new Date().toString() + "\t" + str).getBytes());
            buffer.flip();
            dChannel.send(buffer, new InetSocketAddress("127.0.0.1", 9001));
            buffer.clear();

            // 关闭连接
            dChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
