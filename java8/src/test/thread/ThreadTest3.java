package test.thread;


import java.util.concurrent.Callable;

public class ThreadTest3 implements Callable<Integer> {

    public static void main(String[] args) {
        try {
            Object call = new ThreadTest3().call();
            System.out.println(call);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println(
                    "Name: "+Thread.currentThread().getName()
                            + "\t Value: "+ i
                            + "\t ID: "+ Thread.currentThread().getId());

            sum += i;
        }
        return sum;
    }
}
