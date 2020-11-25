package test.thread;

public class ThreadTest2 implements Runnable {

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new ThreadTest2()).start();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(
                    "Name: "+Thread.currentThread().getName()
                            + "\t Value: "+ i
                            + "\t ID: "+ Thread.currentThread().getId());
        }
    }
}
