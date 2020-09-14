package test.stream1;

import org.junit.jupiter.api.Test;
import test.lambda1.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 一，Stream 的三个操作步骤
 * 1. 创建Stream
 * 2. 中间操作
 * 2. 终止操作（终端操作）
 */
public class TestStreamApi2 {

    List<Employee> employees = Arrays.asList(
            new Employee(18, "张三", 9999.99),
            new Employee(48, "李四", 5555.55),
            new Employee(58, "王五", 7777.77),
            new Employee(8, "赵六", 3333.33),
            new Employee(38, "田七", 6666.66),
            new Employee(38, "田七", 6666.66),
            new Employee(38, "田七", 6666.66)
    );

    // 中间操作

    /**
     * 排序
     *  sorted(): 自然排序(Comparable)
     *  sorted(Comparator com): 定制排序
     */
    @Test
    public void test7(){
        List<String> list = Arrays.asList("ccc", "ddd", "aaa", "bbb");

        list.stream().sorted().forEach(System.out::println);

        System.out.println("----------------");

        employees.stream()
                .sorted((e1, e2) -> {
                    if (e1.getAge().equals(e2.getAge())) {
                        return e1.getName().compareTo(e2.getName());
                    } else {
                        return -e1.getAge().compareTo(e2.getAge());
                    }
                }).forEach(System.out::println);
    }


    /**
     * 映射
     *  map：接收 Lambda 将元素转换成其他形式或提取信息，接收一个函数作为参数，
     *      该函数会被应用到每个元素上，并将其映射成一个新元素。
     *  flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
     */

    @Test
    public void test6() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        list.stream()
            .map((str) -> str.toUpperCase())
            .forEach(System.out::println);

        System.out.println("----------------");

        employees.stream()
                .map(Employee::getName)
                .distinct()
                .forEach(System.out::println);

        System.out.println("----------------");

        /*Stream<Stream<Character>> stream = list.stream()
            .map(TestStreamApi2::filterCharacter); // {{a,a,a},{b,b,b}}
        stream.forEach((s) -> {
            s.forEach(System.out::println);
        });*/

        System.out.println("----------------");

        Stream<Character> stream1 = list.stream()
                .flatMap(TestStreamApi2::filterCharacter); // {a,a,a,b,b,b}
        stream1.forEach(System.out::println);
    }

    public static Stream<Character> filterCharacter(String str){
        List<Character> retList = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            retList.add(ch);
        }
        return retList.stream();
    }


    /**
     * 筛选与切片
     * filter：接收Lambda，从流中排除某些元素
     * limit：截断流，使其元素不超过给定数量。
     * skip(n)：跳过元素，返回一个扔掉前n个元素的流。若流中元素不足n个，则返回一个空流；与limit(n)互补。
     * distinct：筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素。
     */

    @Test
    public void test5() {
        employees.stream()
                .filter((e) -> e.getSalary() > 5000)
                .skip(2)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void test4() {
        employees.stream()
                .filter((e) -> e.getSalary() > 5000)
                .skip(2)
                .forEach(System.out::println);
    }

    @Test
    public void test3() {
        employees.stream()
                .filter((e) -> {
                    System.out.println("短路!"); // && ||
                    return e.getSalary() >= 5000;
                })
                .limit(2)
                .forEach(System.out::println);
    }


    // 内部迭代：迭代操作由 Stream API 完成
    @Test
    public void test1() {
        // 中间操作：不会执行任何操作
        Stream<Employee> stream = employees.stream()
                .filter((x) -> {
                    System.out.println("Stream API 的中间操作");
                    return x.getAge() > 35;
                });
        // 终止操作：一次性执行全部内容，即"惰性求值"
        stream.forEach(System.out::println);
    }

    // 外部迭代
    @Test
    public void test2() {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
