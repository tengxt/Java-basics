package com.tengxt.spring.cloud.handler;

import com.tengxt.spring.cloud.api.EmployeeRemoteService;
import com.tengxt.spring.cloud.entity.Employee;
import com.tengxt.spring.cloud.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeignHumanResourceHandler {

    // 装配调用远程微服务的接口，后面向调用本地方法一样直接使用
    @Autowired
    private EmployeeRemoteService employeeRemoteService;

    @RequestMapping("/feign/consumer/get/emp")
    public Employee getEmployeeRemote() {
        return employeeRemoteService.getEmployeeRemote();
    }

    @RequestMapping("/feign/consumer/get/empList")
    public List<Employee> getEmpListRemote(@RequestParam("keyword") String keyword) {
        return employeeRemoteService.getEmpListRemote(keyword);
    }

    @RequestMapping("/feign/consumer/get/emp/breaker")
    public ResultEntity<Employee> getEmpBreaker(@RequestParam("signal") String signal) {
        return employeeRemoteService.getEmpBreaker(signal);
    }
}
