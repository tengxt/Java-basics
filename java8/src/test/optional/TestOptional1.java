package test.optional;

import org.junit.jupiter.api.Test;
import test.lambda1.Employee;

import java.util.Optional;

public class TestOptional1 {
    /**
     * Optional 容器类的常用方法：
     * 1. Optional.of(T t): 创建一个 Optional 实例
     * 2. Optional.empty(): 创建一个空的 Optional 实例
     * 3. Optional.ofNullable(T t): 若 t 不为null，创建 Optional 实例，否则创建空实例
     * 4. isPresent(): 判断是否包含值
     * 5. orElse(T t): 如果调用对象包含值，返回该值，否则返回t
     * 6. orElseGet(Supplier s): 如果调用对象包含值，返回该值，否则返回 s 获取的值
     * 7. map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
     * 8. flatMap(Function mapper): 与map类似，要求返回值必须是Optional
     */

    @Test
    public void test1() {
        // 创建一个 Optional 实例
        Optional<Employee> emp = Optional.of(new Employee()); // null
        System.out.println(emp.get());
    }

    @Test
    public void test2() {
        // 创建一个空的 Optional 实例
        Optional<Employee> empty = Optional.empty();
        System.out.println(empty.get());
    }

    @Test
    public void test3() {
        // Optional.ofNullable(T t): 若 t 不为null，创建 Optional 实例，否则创建空实例
        Optional<Employee> emp = Optional.ofNullable(new Employee()); // null
        System.out.println(emp.get());
    }

    @Test
    public void test4() {
        // isPresent(): 判断是否包含值
        Optional<Employee> emp = Optional.ofNullable(null);
        if (emp.isPresent()) {
            System.out.println(emp.get());
        }
        System.out.println(emp.get());
    }

    @Test
    public void test5() {
        // orElse(T t): 如果调用对象包含值，返回该值，否则返回t
        Optional<Employee> emp = Optional.ofNullable(null);
        Employee employee = emp.orElse(new Employee(100, "哈哈哈", 333.33));
        System.out.println(employee);
    }

    @Test
    public void test6() {
        // orElseGet(Supplier s): 如果调用对象包含值，返回该值，否则返回 s 获取的值
        Optional<Employee> emp = Optional.of(new Employee());
        Employee employee = emp.orElseGet(() -> emp.get());
        System.out.println(emp);
    }

    @Test
    public void test7() {
        // map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
        String name = "哈哈哈哈";
        Optional<String> optional = Optional.of(new Employee(123, "嘻嘻嘻", 333.33, Employee.Status.BUSY))
                .map((e) -> e.getName());
        if (optional.isPresent()) {
            name = optional.get();
        }
        System.out.println(name);
    }

    @Test
    public void test8() {
        // flatMap(Function mapper): 与map类似，要求返回值必须是Optional
        Optional<Object> op = Optional.of(new Employee());
    }
}
