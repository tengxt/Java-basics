package test.thread;

public class ThreadTest1 extends Thread {

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new ThreadTest1()).start();
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
