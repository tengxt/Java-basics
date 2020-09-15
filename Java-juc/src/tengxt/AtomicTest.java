package tengxt;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一、i++ 的原子性问题，i++ 的操作实际上分为三个步骤 “读-改-写”
 *      int i = 10；
 *      i = i++; // 10
 *
 *      // i++
 *      int temp = i;
 *      i = i + 1;
 *      i = temp;
 *
 * 二、原子变量： jdk1.5 后 java.util.concurrent.atomic.Atomic 包下提供了常用的原子变量
 *  1. volatile 保证内存可见性
 *  2. CAS（Compare-And-Swap）算法 保证数据的原子性
 *      CAS算法是硬件对于并发操作共享数据的支持
 *      包含了三个操作数：
 *      内存值 V
 *      预估值 A
 *      更新值 B
 *      当且仅当 V == A 时，V 赋值（=） B，否则，将不做任何操作
 */
public class AtomicTest {
    public static void main(String[] args) {
        AtomicDemo atomicDemo = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(atomicDemo).start();
        }
    }
}

class AtomicDemo implements Runnable {

//    private volatile int serialNumber = 0;

    private AtomicInteger serialNumber = new AtomicInteger();

    private int getSerialNumber() {
//        return serialNumber++;
        return serialNumber.getAndIncrement();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(getSerialNumber());
    }
}

