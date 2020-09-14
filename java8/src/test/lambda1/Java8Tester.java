package test.lambda1;

import org.junit.jupiter.api.Test;

import java.util.*;

public class Java8Tester {

    final static int salutation =0;

    public static void main(String[] args) {
//        GreetingService greetingService = message -> System.out.println(salutation + message);
//        greetingService.sayMessage("Runoob");


        GreetingService greetingService1 = message -> {
            System.out.println(salutation + message);
        };
        greetingService1.sayMessage("Runoob");
    }

    @Test
    public void test1(){
        show(new HashMap<>());
    }

    private void show(Map<String, Integer> map){ }

    private void lambdaTester() {
        Java8Tester tester = new Java8Tester();

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        // 没有大括号和返回语句
        MathOperation division = (int a, int b) -> a / b;

//        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
//        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
//        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
//        System.out.println("10 / 5 = " + tester.operate(10, 5, division));


        // 不用括号
        GreetingService greetingService1 = message -> System.out.println("Hello " + message);

        // 用括号
        GreetingService greetingService2 = (message -> System.out.println("hello " + message));

        greetingService1.sayMessage("Runoob");
        greetingService2.sayMessage("Google");
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }


    private void testSort() {
        List<String> names = new ArrayList<String>();
        names.add("Google ");
        names.add("Runoob ");
        names.add("Taobao ");
        names.add("Baidu ");
        names.add("Sina ");

        System.out.println("排序前的数据：");
        System.out.println(names);

        Java8Tester tester = new Java8Tester();
        System.out.println("使用java7的语法格式：");
        tester.sortJava7(names);
        System.out.println(names);

        System.out.println("使用java8的语法格式：");
        tester.sortJava8(names);
        System.out.println(names);
    }

    /**
     * 使用 java 7 排序
     *
     * @param names
     */
    private void sortJava7(List<String> names) {
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * 使用 java 8 排序
     *
     * @param names
     */
    private void sortJava8(List<String> names) {
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
    }
}

