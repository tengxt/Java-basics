package tengxt;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 一、缓冲区（Buffer）：在Java NIO 中负责数据的存储。缓冲区就是数组。用于存储不同类型的数据
 *
 * 根据数据类型的不同（Boolean 除外），有以下 Buffer 常用子类
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *
 * 上述缓冲区的管理方式几乎一致，通过allocate()获取缓冲区
 *
 * 二、缓冲区存取数据的两个核心方法
 * put() : 存入数据到缓冲区中
 * get() : 获取缓冲区中的数据
 *
 * 三、缓冲区中的四个核心属性
 * capacity：容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变
 * limit： 界限，表示缓冲区中可以操作数据的大小。（limit 后面的数据不能进行读写）
 * position： 位置，表示缓冲区中正在操作的数据
 * mark : 标记，表示记录当前 position 的位置，可以通过 reset() 回复到 mark 的位置
 *
 * 0 <= mark <= position <= limit <= capacity
 *
 * 四、直接缓冲区与非直接缓冲区
 * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区： 通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 *
 */
public class TestBuffer {

    @Test
    public void test3() {
        // 分配直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        // 判断是不是直接缓冲区
        System.out.println(buffer.isDirect());
    }

    @Test
    public void test2() {
        String str = "abcde";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());

        buffer.flip();
        byte[] dist = new byte[buffer.limit()];
        buffer.get(dist, 0, 2);
        System.out.println(new String(dist, 0, 2)); // ab
        System.out.println(buffer.position()); // 2

        // mark():  标记
        buffer.mark();

        buffer.get(dist, 2, 2);
        System.out.println(new String(dist, 2, 2)); // cd
        System.out.println(buffer.position()); // 4

        // reset(): 恢复到 mark 的位置
        buffer.reset();
        System.out.println(buffer.position()); // 2

        // 判断缓冲区是否还有剩余数据
        if (buffer.hasRemaining()) {
            // 获取缓冲区中可以操作的数量
            System.out.println(buffer.remaining());
        }
    }


    @Test
    public void test1() {
        // 1. 分配一个指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("----------allocate()----------");
        System.out.println(buffer.position());  // 0
        System.out.println(buffer.limit());     // 1024
        System.out.println(buffer.capacity());  // 1024

        // 2. 利用 put() 存入数据到缓冲区中
        String str = "abcde";

        buffer.put(str.getBytes());
        System.out.println("----------put()----------");
        System.out.println(buffer.position());  // 5
        System.out.println(buffer.limit());     // 1024
        System.out.println(buffer.capacity());  // 1024

        // 3. flip() 切换读取数据模式
        buffer.flip();
        System.out.println("----------flip()----------");
        System.out.println(buffer.position());  // 0
        System.out.println(buffer.limit());     // 5
        System.out.println(buffer.capacity());  // 1024

        // 4. 利用get() 读取数据缓冲区中的数据
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst);

        System.out.println("----------get()----------");
        System.out.println(new String(dst, 0, dst.length));   //abcde

        System.out.println(buffer.position());  // 5
        System.out.println(buffer.limit());     // 5
        System.out.println(buffer.capacity());  // 1024

        // 5. rewind() ： 可重复读数据
        buffer.rewind();
        System.out.println("----------rewind()----------");
        System.out.println(buffer.position());  // 0
        System.out.println(buffer.limit());     // 5
        System.out.println(buffer.capacity());  // 1024

        // 6. clear() : 清空缓冲区，但是缓冲区的数据依然存在，但是处于“被遗忘”状态
        buffer.clear();
        System.out.println("----------clear()----------");
        System.out.println(buffer.position());  // 0
        System.out.println(buffer.limit());     // 1024
        System.out.println(buffer.capacity());  // 1024

        System.out.println((char) buffer.get());

    }
}
