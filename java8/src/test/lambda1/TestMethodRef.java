package test.lambda1;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 一,方法引用： 若 Lambda 体中的内容有方法已经实现了，我们可以使用"方法引用"
 *          (可以理解为方法引用是 Lambda 表达式的另外一种表现形式)
 * * 主要有三种语法形式：
 *
 * 对象::实例方法名
 *
 * 类::静态方法名
 *
 * 类::实例方法名
 *
 * 注意
 *  1. Lambda 体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的参数列表和返回值类型保持一致。
 *  2. 若 Lambda 参数列表中第一个参数是实例方法的调用者，而第二个参数是实例方法的参数时，可以使用 ClassName::Method。
 *
 * 二，构造器引用
 *
 * 格式：ClassName::new
 *  注意： 需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致。
 *
 * 三，数组引用
 *  格式：Type[]::new
 */
public class TestMethodRef {

    // 数组引用
    @Test
    public void test6() {
        Function<Integer, String[]> func = (x) -> new String[x];
        String[] strings = func.apply(10);
        System.out.println(strings.length);

        Function<Integer, String[]> func2 = String[]::new;
        String[] strings2 = func2.apply(20);
        System.out.println(strings2.length);
    }

    // 构造器引用
    @Test
    public void test5() {
        Supplier<Employee> sup = () -> new Employee();

        // 构造器引用方式
        Supplier<Employee> sup2 = Employee::new;
        Employee employee = sup2.get();
        System.out.println(employee);
    }


    // 类::实例方法名
    @Test
    public void test4() {
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);

        BiPredicate<String, String> bp1 = String::equals;
    }

    // 类::静态方法名
    @Test
    public void test3() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

        Comparator<Integer> com1 = Integer::compare;
    }


    // 对象::实例方法名
    @Test
    public void test1() {
        PrintStream ps = System.out;
        Consumer<String> con = (x) -> ps.println(x);

        PrintStream ps1 = System.out;
        Consumer<String> con1 = ps1::println;

        Consumer<String> con2 = System.out::println;
        con2.accept("abcdef");
    }

    @Test
    public void test2() {
        Employee employee = new Employee();
        Supplier<String> sup = () -> employee.getName();
        String name = sup.get();
        System.out.println(name);

        Supplier<String> sup1 = employee::getName;
        System.out.println(sup1.get());

        Supplier<Double> sup2 = employee::getSalary;
        System.out.println(sup2.get());
    }
}
