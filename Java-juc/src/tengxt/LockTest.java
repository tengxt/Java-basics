package tengxt;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 一、用于解决多线程安全问题的方式
 *
 * synchronized:
 *  1. 同步代码块
 *
 *  2. 同步方法
 *
 * jdk 5 以后
 *  3. 同步锁 Lock
 *
 * 注意：是一个显示锁，需要通过 lock()方法上锁，必须通过 unlock() 方法进行释放锁
 */
public class LockTest {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
    }
}

class Ticket implements Runnable {

    private int tick = 100;

//    private ReentrantLock lock = new ReentrantLock(false);
    private ReentrantLock lock = new ReentrantLock(true); // 公平锁，线程轮询

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (tick > 0) {
                    System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
                } else {
                    break;
                }
            } finally {
                lock.unlock(); // 解锁
            }
        }
    }
}
