package test.jvm;

public class ClinitTest1 {

    static class Father {
        public static int A = 1;

        static {
            A = 2;
        }
    }

    static class Son extends Father {
        public static int B = A;
    }

    public static void main(String[] args) {
        // 先加载 Father 类，其次加载 Son 类
        System.out.println(Son.B); // 2
    }
}
