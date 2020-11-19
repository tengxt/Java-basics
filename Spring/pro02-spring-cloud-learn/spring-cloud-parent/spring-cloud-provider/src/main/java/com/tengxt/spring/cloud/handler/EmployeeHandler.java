package com.tengxt.spring.cloud.handler;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tengxt.spring.cloud.entity.Employee;
import com.tengxt.spring.cloud.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeHandler {

    private static List<Employee> empList = null;

    static {
        empList = new ArrayList<Employee>();
        Employee emp1 = new Employee(111, "tom-111", 111.111);
        Employee emp2 = new Employee(222, "tom-222", 222.222);
        Employee emp3 = new Employee(333, "tom-333", 333.333);
        empList.add(emp1);
        empList.add(emp2);
        empList.add(emp3);
    }

    // @HystrixCommand 注解指定当前方法出问题时调用的备用方法（使用fallbackMethod属性指定）
    @HystrixCommand(fallbackMethod = "getEmpBreakerBackup")
    @RequestMapping("/provider/get/emp/breaker")
    public ResultEntity<Employee> getEmpBreaker(@RequestParam("signal") String signal) throws InterruptedException {
        if ("bang-low".equals(signal)) {
            Thread.sleep(5000);
        }
        return ResultEntity.successWithData(new Employee(666, "empName666", 666.666));
    }

    public ResultEntity<Employee> getEmpBreakerBackup(@RequestParam("signal") String signal) {
        String message = "当调用getEmpBreaker()超时时,会执行断路方法" + signal;
        return ResultEntity.faild(message);
    }


    @RequestMapping("/provider/employee/remote")
    public Employee getEmployeeRemote() {
        return new Employee(555, "tom555", 5555.55);
    }

    @RequestMapping("/provider/employee/list/remote")
    public List<Employee> getEmpListRemote(@RequestParam("keyword") String keyword) {
        List<Employee> retList = new ArrayList<Employee>();
        if ("".equals(keyword) || null == keyword) {
            return null;
        }
        if (null != empList && empList.size() > 0) {
            for (Employee employee : empList) {
                String empName = employee.getEmpName();
                if (empName.contains(keyword)) {
                    retList.add(employee);
                }
            }
        }
        return retList;
    }

    @RequestMapping("/provider/get/employee/remote")
    public Employee getEmployeeRemote(HttpServletRequest request) {
        // 获取当前服务的端口号
        int port = request.getServerPort();

        return new Employee(555, "tom555 - " + port, 5555.55);
    }
}
