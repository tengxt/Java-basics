package com.tengxt.spring.cloud.entity.com.tengxt.spring.cloud.api;

import com.tengxt.spring.cloud.entity.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 表示当前接口和一个 provider 对应, value 属性指定要调用的 provider 的微服务名称
@FeignClient(value = "tengxt-provider")
public interface EmployeeRemoteService {

    /**
     * 远程调用的接口方法
     * 要求 @RequestMapping 注解映射的地址一致
     * 要求方法声明一致
     * 用来获取请求参数 @RequestParam、@PathVariable、@RequestBody 不能省略，两边一致
     *
     * @return
     */
    @RequestMapping("/provider/employee/remote")
    Employee getEmployeeRemote();


    @RequestMapping("/provider/employee/list/remote")
    List<Employee> getEmpListRemote(@RequestParam("keyword") String keyword);
}
