package tengxt;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 * 写写/读写    都需要“互斥”
 * 读读        不需要互斥
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {

        ReadWriteLockDemo rw = new ReadWriteLockDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                rw.set((int) (Math.random() * 101));
            }
        },"write").start();


        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    rw.get();
                }
            },"read").start();
        }
    }
}

class ReadWriteLockDemo {
    private int number = 0;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 读
    public void get() {
        try {
            readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + ":" + number);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    // 写
    public void set(int number) {
        try {
            readWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

}
