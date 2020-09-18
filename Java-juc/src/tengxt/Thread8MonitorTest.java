package tengxt;

/**
 * 线程八锁
 *     非静态方法的锁 this，静态方法的锁 对应的Class实例
 *     某一个时刻内，只能由一个线程持有锁，无论几个方法
 *  1. 两个同步方法，两个线程，打印 one  two
 *  2. 新增 Thread.sleep() 给 getOne() ,打印 one two
 *  3. 新增普通方法 getThree，打印 three one two
 *  4. 注释 getThree，number2.getTwo，打印 two one
 *  5. 修改 getOne 为静态同步方法，改为 number.getTwo，打印 two one
 *  6. 两个方法都为静态同步方法，一个 number 对象，打印 one two
 *  7. getOne 为静态同步方法，getTwo 为同步方法，改为 number2.getTwo,打印 two one
 *  8. 两个静态同步方法，两个number对象，打印 one two
 */
public class Thread8MonitorTest {
    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 1,2,3
//                number.getTwo();
                // 4
//                number2.getTwo();
                // 5,6
//                number.getTwo();
                // 7
                number2.getTwo();
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                number.getThree();
//            }
//        }).start();
    }
}

class Number {
    public static synchronized void getOne() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }
}
