package test.single;

import single.Singleton1;

public class TestSingleton1 {
    public static void main(String[] args) {
        Singleton1 s = Singleton1.INSTANCE;
        System.out.println(s);
    }
}
