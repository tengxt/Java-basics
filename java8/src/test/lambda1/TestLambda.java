package test.lambda1;

import org.junit.jupiter.api.Test;

import java.util.*;

public class TestLambda {
    // 匿名内部类
    @Test
    public void test1() {
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> ts = new TreeSet<>(com);
    }

    // Lambda 表达式
    @Test
    public void test2() {
        // Lamda表达式： 是一个匿名函数，是一段可以传递的代码（将代码像数据一样传递）
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> ts = new TreeSet<>(com);
    }

    List<Employee> employees = Arrays.asList(
            new Employee(18, "张三", 9999.99),
            new Employee(48, "李四", 5555.55),
            new Employee(58, "王五", 7777.77),
            new Employee(8, "赵六", 3333.33)
    );

    @Test
    public void test3() {
        List<Employee> employeeList = filterEmployees(this.employees);
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }
    }

    // 获取员工大于35的员工信息
    public List<Employee> filterEmployees(List<Employee> list) {
        List<Employee> retList = new ArrayList<>();
        for (Employee employee : list) {
            if (employee.getAge() > 35) {
                retList.add(employee);
            }
        }
        return retList;
    }

    // 优化方式一： 策略设计模式
    @Test
    public void test4() {
        List<Employee> list1 = filterEmployee(employees, new FilterEmployeeByAge());
        for (Employee employee : list1) {
            System.out.println(employee);
        }
        System.out.println("-------------------------------------");
        List<Employee> list2 = filterEmployee(employees, new FilterEmployeeBySalary());
        for (Employee employee : list2) {
            System.out.println(employee);
        }
    }

    public List<Employee> filterEmployee(List<Employee> emps, MyPredicate<Employee> mp) {
        List<Employee> retList = new ArrayList<>();

        for (Employee emp : emps) {
            if (mp.test(emp)) {
                retList.add(emp);
            }
        }
        return retList;
    }

    // 优化方式二：匿名内部类
    @Test
    public void test5() {
        List<Employee> retList = filterEmployee(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() <= 5000;
            }
        });

        for (Employee employee : retList) {
            System.out.println(employee);
        }
    }

    // 优化方式三：Lambda 表达式
    @Test
    public void test6() {
        List<Employee> list = filterEmployee(employees, (e) -> e.getSalary() <= 7000);
        list.forEach(System.out::println);
    }

    // 优化方式四：Stream API
    @Test
    public void test7() {
        employees.stream()
                .filter((e) -> e.getSalary() >= 5000)
                .limit(2)
                .forEach(System.out::println);
        System.out.println("---------------------------");
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
    }
}
