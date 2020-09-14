package test.stream1;

import org.junit.jupiter.api.Test;
import test.lambda1.Employee;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 一，Stream 的三个操作步骤
 * 1. 创建Stream
 * 2. 中间操作
 * 2. 终止操作（终端操作）
 */
public class TestStreamApi3 {

    List<Employee> employees = Arrays.asList(
            new Employee(18, "张三", 9999.99, Employee.Status.BUSY),
            new Employee(48, "李四", 5555.55, Employee.Status.FREE),
            new Employee(58, "王五", 7777.77, Employee.Status.BUSY),
            new Employee(8, "赵六", 3333.33, Employee.Status.FREE),
            new Employee(8, "赵六", 3333.33, Employee.Status.FREE),
            new Employee(38, "田七", 6666.66, Employee.Status.VOCATION)
    );

    // 终止操作

    /**
     * 收集
     * collect: 将流转换为其他形式，接收一个 Collector 接口的实现，用于给 Stream 中元素做汇总的方法
     */
    @Test
    public void test10() {
        String str = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(","));
        System.out.println(str);
    }

    @Test
    public void test9() {
        DoubleSummaryStatistics dss = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss.getMax());
        System.out.println(dss.getMin());
        System.out.println(dss.getAverage());
        System.out.println(dss.getCount());
        System.out.println(dss.getSum());
    }

    // 分区
    @Test
    public void test8() {
        Map<Boolean, List<Employee>> map = employees.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() > 8888));
        System.out.println(map);
    }

    // 多级分组
    @Test
    public void test7() {
        Map<Employee.Status, Map<String, List<Employee>>> map = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if (e.getAge() <= 35) {
                        return "青年";
                    } else if (e.getAge() <= 50) {
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));

        System.out.println(map);

    }

    // 分组
    @Test
    public void test6() {
        Map<Employee.Status, List<Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);
    }

    @Test
    public void test5() {
        // 总数
        Long count = employees.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        // 平均值
        Double avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        // 总和
        Double sum = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        // 最大值
        Optional<Employee> max = employees.stream()
                .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(max.get());

        // 最小值
        Optional<Double> min = employees.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());

    }

    @Test
    public void test4() {
        List<String> list = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("---------------------------");

        Set<String> set = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("---------------------------");

        HashSet<String> hashSet = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hashSet.forEach(System.out::println);
    }


    /**
     * 归约
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator): 可以将流中元素反复结合起来，得到一个值。
     */
    @Test
    public void test3() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        System.out.println("-------------------------------");

        Optional<Double> op = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op.get());
    }

    /**
     * 查找与匹配
     * allMatch: 检查是否匹配所有元素
     * anyMatch: 检查是否至少匹配一个元素
     * noneMatch: 检查是否没有匹配所有元素
     * findFirst: 返回第一个元素
     * findAny: 返回当前流中的任意元素
     * count: 返回流中元素的总个数
     * max: 返回流中最大值
     * min: 返回流中最小值
     */
    @Test
    public void test2() {
        // count: 返回流中元素的总个数
        long count = employees.stream()
                .count();
        System.out.println(count);

        // max: 返回流中最大值
        Optional<Employee> op1 = employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(op1.get());

        // min: 返回流中最小值
        Optional<Double> op2 = employees.stream()
                .map(Employee::getSalary)
                .min(Double::compare);
        System.out.println(op2.get());
    }


    @Test
    public void test1() {
        // allMatch: 检查是否匹配所有元素
        boolean b1 = employees.stream()
                .allMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);

        // anyMatch: 检查是否至少匹配一个元素
        boolean b2 = employees.stream()
                .anyMatch((e) -> e.getStatus().equals(Employee.Status.VOCATION));
        System.out.println(b2);

        // noneMatch: 检查是否没有匹配所有元素
        boolean b3 = employees.stream()
                .noneMatch((e) -> e.getStatus().equals(Employee.Status.FREE));
        System.out.println(b3);

        // findFirst: 返回第一个元素
        Optional<Employee> op1 = employees.parallelStream()
                .filter((e) -> e.getStatus().equals(Employee.Status.BUSY))
                .findFirst();
        Employee emp = op1.get();
        System.out.println(emp);

        // findAny: 返回当前流中的任意元素
        Optional<Employee> op2 = employees.parallelStream()
                .filter((e) -> e.getStatus().equals(Employee.Status.FREE))
                .findAny();
        System.out.println(op2.get());
    }
}

