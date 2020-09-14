package test.lambda1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestLambda2 {
    List<Employee> employees = Arrays.asList(
            new Employee(18, "张三", 9999.99),
            new Employee(48, "李四", 5555.55),
            new Employee(58, "王五", 7777.77),
            new Employee(8, "赵六", 3333.33)
    );

    @Test
    public void test1() {
        Collections.sort(employees, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return -Integer.compare(e1.getAge(), e2.getAge());
            }
        });

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void test2() {
        String trimStr = strHandler("\t\t\t  哈哈哈哈  ", (str) -> str.trim());
        System.out.println(trimStr);

        String upperStr = strHandler("abcdefg", (str) -> str.toUpperCase());
        System.out.println(upperStr);

        String subStr = strHandler("abcdefg", (s) -> s.substring(2, 6));
        System.out.println(subStr);
    }

    // 字符串相关处理
    public String strHandler(String str, MyFunction mf) {
        return mf.getValue(str);
    }

    @Test
    public void test3() {
        op(100L, 200L, (x, y) -> x + y);

        op(100L, 200L, (x, y) -> x * y);
    }

    // 对于两个 long 型数据进行处理
    public void op(Long l1, Long l2, MyFunction2<Long, Long> mf) {
        System.out.println(mf.getValue(l1, l2));
    }
}
