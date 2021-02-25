package test.review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Test01 {
    public static void main(String[] args) {
//        Man man = new Man("李四", 28);
//        int i = f(2);
//        System.out.println(i);

//        Scanner scanner = new Scanner(System.in);
//        String nextStr = scanner.nextLine();
//        System.out.println(nextStr);
//        scanner.close();

//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            String readLine = bufferedReader.readLine();
//            System.out.println(readLine);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bufferedReader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


//        String str = null;
//        if(str.equals("hahah")){ // NullPointerException
//            System.out.println(str);
//        }

//        if ("hahah".equals(str)) { // false
//            System.out.println(true);
//        }

//        if (Objects.equals(str, "hahah")) { // false
//            System.out.println(true);
//        }
//        System.out.println(false);

//        float a = 1.0f - 0.9f;
//        float b = 0.9f - 0.8f;
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(a == b);


        BigDecimal a = new BigDecimal("1.0");
        BigDecimal b = new BigDecimal("0.9");
        BigDecimal c = new BigDecimal("0.05");
        BigDecimal d = new BigDecimal("0.05");

        BigDecimal x = a.subtract(b);
        BigDecimal y = c.add(d);

        // 0.1
        System.out.println(x);
        // 0.10
        System.out.println(y);
        // false
        System.out.println(Objects.equals(x, y));
        // a.compareTo(b) : 返回 -1 表示 a 小于 b，0 表示 a 等于 b ， 1表示 a 大于 b。
        // 0
        System.out.println(x.compareTo(y));

        BigDecimal m = new BigDecimal("3.1415926");
        BigDecimal scale = m.setScale(3, BigDecimal.ROUND_DOWN);
        System.out.println(scale);


        String[] strArray = {"apple", "banana", "orange"};
        // Arrays.asList() 返回由指定数组支持的固定大小的列表，将数组转换为集合后,底层其实还是数组
        List<String> strList = Arrays.asList(strArray);
        System.out.println(strList);
        strArray[0] = "小王";
        System.out.println(strList);

        Integer[] myArry = {1, 2, 3};
        List list = Arrays.asList(myArry);
        // class java.util.Arrays$ArrayList
        System.out.println(list.getClass());
        // 1
        System.out.println(list.size());
        // [I@2503dbd3
        System.out.println(list.get(0));
        // 报错： ArrayIndexOutOfBoundsException
        System.out.println(list.get(1));



        // 如何正确的将数组转换为ArrayList?
        // 1. 自己实现
        Integer[] array1 = {1, 2, 3};
        // class java.util.ArrayList
        System.out.println(arrayToList(array1).getClass());

        // 2. 最简便的方法(推荐)
        List<Integer> list1 = new ArrayList<>(Arrays.asList(array1));
        // class java.util.ArrayList
        System.out.println(list1.getClass());

        // 3. 使用 Java8 的Stream(推荐)
        List<Integer> list2 = Arrays.stream(array1).collect(Collectors.toList());
        // class java.util.ArrayList
        System.out.println(list2.getClass());
        // 基本类型也可以实现转换（依赖boxed的装箱操作）
        int[] array2 = {1, 2, 3};
        List<Integer> list3 = Arrays.stream(array2).boxed().collect(Collectors.toList());
        System.out.println(list3);



        // 不要在 foreach 循环里进行元素的 remove/add 操作
        List<String> list4 = new ArrayList<>(Arrays.asList("1", "2"));
        // 使用迭代器 Iterator
        Iterator<String> iterator = list4.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (Objects.equals(item, "2")) {
                iterator.remove();
            }
        }
        // [1]	class java.util.ArrayList
        System.out.println(list4 + "\t" + list4.getClass());

        // 使用 foreach 遍历
        for (String item : list4) {
            if (Objects.equals(item, "2")) {
                // 报错：ConcurrentModificationException
                list4.remove(item);
            }
        }
        // [1]	class java.util.ArrayList
        System.out.println(list4 + "\t" + list4.getClass());

    }

    static <T> List<T> arrayToList(final T [] array){
        final List<T> l = new ArrayList<>(array.length);

        for (final T t : array) {
            l.add(t);
        }

        return l;
    }


    public static int f(int val) {
        try {
            return val * val;
        } finally {
            if (val == 2) {
                return 0;
            }
        }
    }
}

class Person {
    private String name;
    private Integer age;

    public Person() {
        System.out.println("调用父类无参构造器");
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
        System.out.println("调用父类的有参构造器");
    }
}

class Man extends Person {

    public Man(String name, Integer age) {
//        super();
        System.out.println("name=" + name + " age=" + age);
    }
}
