package tengxt;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 一、创建执行线程的方式三 ： 实现Callable 接口。相较于实现Runnable 接口的方式，方式可以有返回值，并且可以抛出异常
 */
public class CallableTest {

    public static void main(String[] args) {
        CallableDemo cd = new CallableDemo();
        // 执行 Callable方式，需要FutureTask 实现类的支持，用于接收运算后的结果
        FutureTask<Integer> task = new FutureTask(cd);
        new Thread(task).start();
        try {
            // 接收线程运算后的结果
            Integer sum =task.get(); // FutureTask 可用于闭锁
            System.out.println(sum);
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class CallableDemo implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }
}

/*class RunnableDemo implements Runnable{
    @Override
    public void run() {

    }
}*/
