package test.single;

import single.Singleton4;

import java.util.concurrent.*;

public class TestSingleton4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*Singleton4 s1 = Singleton4.getInstance();
        Singleton4 s2 = Singleton4.getInstance();

        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);*/

        Callable<Singleton4> callable = new Callable<Singleton4>() {

            @Override
            public Singleton4 call() throws Exception {
                return Singleton4.getInstance();
            }
        };

        ExecutorService es = Executors.newFixedThreadPool(2);

        Future<Singleton4> f1 = es.submit(callable);
        Future<Singleton4> f2 = es.submit(callable);

        Singleton4 s1 = f1.get();
        Singleton4 s2 = f2.get();

        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);

        es.shutdown();
    }
}
