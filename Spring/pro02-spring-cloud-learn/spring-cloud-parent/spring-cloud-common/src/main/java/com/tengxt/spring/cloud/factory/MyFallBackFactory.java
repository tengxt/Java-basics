package com.tengxt.spring.cloud.factory;

import com.tengxt.spring.cloud.api.EmployeeRemoteService;
import com.tengxt.spring.cloud.entity.Employee;
import com.tengxt.spring.cloud.util.ResultEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 实现 consumer 端服务降级
 * 实现 FallbackFactory 接口时要传入 @FeignClient 注解标记的接口类型
 * 在 create() 方法中返回 @FeignClient 注解标记的j接口类型的对象，当 provider 调用失败后，会执行这个对象的方法
 * 这个类必须使用 @Component 注解将当前类的对象加入IOC容器，当前类必须能够被扫描到
 */
@Component
public class MyFallBackFactory implements FallbackFactory<EmployeeRemoteService> {

    @Override
    public EmployeeRemoteService create(Throwable throwable) {
        return new EmployeeRemoteService() {
            @Override
            public Employee getEmployeeRemote() {
                return null;
            }

            @Override
            public List<Employee> getEmpListRemote(String keyword) {
                return null;
            }

            @Override
            public ResultEntity<Employee> getEmpBreaker(String signal) {
                return ResultEntity.faild(throwable.getMessage() + "使用 FallbackFactory 服务降级");
            }
        };
    }
}
