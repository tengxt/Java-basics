### 服务治理
由于Spring Cloud为服务治理做了一层抽象接口，所以在Spring Cloud应用中可以支持多种不同的服务治理框架，比如：`Netflix Eureka、Consul、Zookeeper`。在Spring Cloud服务治理抽象层的作用下，我们可以无缝地切换服务治理实现，并且不影响任何其他的服务注册、服务发现、服务调用等逻辑。

#### Spring Cloud Eureka
Spring Cloud Eureka 来实现服务治理。

Spring Cloud Eureka 是Spring Cloud Netflix项目下的服务治理模块。而Spring Cloud Netflix项目是Spring Cloud的子项目之一，主要内容是对Netflix公司一系列开源产品的包装，它为Spring Boot应用提供了自配置的Netflix OSS整合。通过一些简单的注解，开发者就可以快速的在应用中配置一下常用模块并构建庞大的分布式系统。它主要提供的模块包括：服务发现（Eureka），断路器（Hystrix），智能路由（Zuul），客户端负载均衡（Ribbon）等。

##### Eureka Server
提供服务注册和发现

**添加依赖**

在项目 `spring-cloud-eureka` 的 `pom.xml`中引入依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
</dependencies>
```
**开启服务注册**

通过`@EnableEurekaServer`注解启动一个服务注册中心提供给其他应用进行对话,这个注解需要在 Spring Boot工程的启动类。
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer // 启动 Eureka 服务器端功能
@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {
        SpringApplication.run(com.tengxt.spring.cloud.SpringBootMainClass.class, args);
    }
}
```
**添加配置**

该服务注册中心也会将自己作为客户端来尝试注册它自己，所以我们需要禁用它的客户端注册行为，配置文件
```properties
# 自定义端口, eureka 默认端口是 8761
server.port=5000

# 配置当前 eureka 服务的主机地址
eureka.instance.hostname=localhost
# 当前服务本身就是注册中心，不必“自己注册自己”
eureka.client.register-with-eureka=false
# 当前服务本身就是注册中心，不必“从注册中心取回信息”
eureka.client.fetch-registry=false
# 客户端（consumer，provider）访问当前注册中心时使用的地址
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}/${server.port}/eureka
```
**访问服务**

启动工程后，访问：`http://localhost:5000/`

##### provider Server
服务提供、将自身服务注册到 Eureka 注册中心，从而使服务消费（consumer）能够找到

**添加依赖**
在项目 `spring-cloud-provider` 的 `pom.xml`中引入依赖
```xml
<dependencies>
    <ependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

**开启服务注册**
在应用主类中通过加上 `@EnableEurekaClient`，但只有Eureka 可用，可以使用 `@EnableDiscoveryClient`。需要配置才能找到Eureka注册中心服务器
```java
@EnableEurekaClient
@RestController
@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainClass.class, args);
    }
    
    @RequestMapping("/provider/employee/remote")
    public Employee getEmployeeRemote() {
        return new Employee(555, "tom555", 5555.55);
    }
}
```

**添加配置**
```properties
# 端口
server.port=3000

# 指定当前微服务名称，以便将来通过微服务名称调用当前微服务时能够找到
spring.application.name=tengxt-provider
# 配置当前微服务作为 eureka 客户端访问 eureka 服务器端时使用的地址
# defaultZone 是一个魔术字符串后备值，为任何不表示首选项的客户端提供服务URL
eureka.client.service-url.defaultZone=http://localhost:5000/eureka
```

##### Hystrix
服务熔断、降级

一个用于处理分布式系统的延迟和容错的开源库，hystrix可以保证在一个依赖出现问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。
“断路器”本身是一种开关装置，当某个服务单元发生故障时，通过断路器的故障监控（类似与熔断丝），向调用方返回一个预期的可处理的备选响应，而不是长时间的等待或抛出无法处理的异常，这样就可以保证调用方不会长时间的、不必要的占用，从而避免故障在分布式系统中的蔓延乃至雪崩。

Hystrix的工作原理：

- 防止任何单个依赖项耗尽所有容器（例如Tomcat）用户线程
- 减少负载并快速失败，而不是排队
- 在可行的情况下提供备用，以保护用户免受故障的影响
- 使用隔离技术（例如隔板，泳道和断路器模式）来限制任何一种依赖关系的影响
- 通过近实时指标，监视和警报优化发现时间
- 通过在Hystrix的大多数方面中以低延迟传播配置更改来优化恢复时间，并支持动态属性更改，这使您可以通过低延迟反馈回路进行实时操作修改。
- 防止整个依赖客户端执行失败，而不仅仅是网络通信失败

###### 服务熔断
对微服务雪崩效应的一种链路保护机制，当扇出链路中微服务响应时间过长或不可用时，会进行服务的降级，进而熔断该服务节点的调用，快速返回错误的响应信息，
当检测到该链路访问正常时恢复该链路的正常调用。spring cloud中hystrix会监控微服务的调用，当失败调用达到一定的阈值，默认为5秒20次失败调用就会启动熔断机制，熔断机制的注解为`@HystrixCommand`唯一的注解

**添加依赖**
在项目 `spring-cloud-provider` 的 `pom.xml`中引入依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

**熔断器配置**
```java
// @HystrixCommand 注解指定当前方法出问题时调用的备用方法（使用fallbackMethod属性指定）
    @HystrixCommand(fallbackMethod = "getEmpBreakerBackup")
    @RequestMapping("/provider/get/emp/breaker")
    public ResultEntity<Employee> getEmpBreaker(@RequestParam("signal") String signal) throws InterruptedException {
        // 当请求参数 signal=bang-low，线程休眠；触发熔断机制 
        if ("bang-low".equals(signal)) {
            Thread.sleep(5000);
        }
        return ResultEntity.successWithData(new Employee(666, "empName666", 666.666));
    }
    
    public ResultEntity<Employee> getEmpBreakerBackup(@RequestParam("signal") String signal) {
        String message = "当调用getEmpBreaker()超时时,会执行断路方法" + signal;
        return ResultEntity.faild(message);
    }
```

**启动类开启熔断机制**
```java
// 使用 @EnableCircuitBreaker 注解开启断路器功能
@EnableCircuitBreaker
@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainClass.class, args);
    }
}
```

###### 服务降级
服务的降级处理是在消费（consumer）实现的

在项目 `spring-cloud-common` 中编写服务降级类
```java
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
```

关联客户端接口
```java
// 表示当前接口和一个 provider 对应, value 属性指定要调用的 provider 的微服务名称
// fallbackFactory 属性指定 provider 不可用时提供备用方案的工厂类型
@FeignClient(value = "tengxt-provider", fallbackFactory = MyFallBackFactory.class)
public interface EmployeeRemoteService {

    /**
     * 远程调用的接口方法
     * 要求 @RequestMapping 注解映射的地址一致
     * 要求方法声明一致
     * 用来获取请求参数 @RequestParam、@PathVariable、@RequestBody 不能省略，两边一致
     * @return
     */
    @RequestMapping("/provider/employee/remote")
    Employee getEmployeeRemote();

    @RequestMapping("/provider/employee/list/remote")
    List<Employee> getEmpListRemote(@RequestParam("keyword") String keyword);

    @RequestMapping("/provider/get/emp/breaker")
    ResultEntity<Employee> getEmpBreaker(@RequestParam("signal") String signal);
}
```

在配置文件中开启服务降级
```properties
# 开启服务降级
feign.hystrix.enabled=true
```

##### Hystrix Dashboard
仪表盘

**添加依赖**
在项目 `spring-cloud-dashboard` 的 `pom.xml`中引入依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
    </dependency>
</dependencies>
```

**开启服务注册**
```java
@EnableHystrixDashboard     //  启用仪表盘监控功能
@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainClass.class, args);
    }
}
```

**添加配置**
```properties
server.port=8000

spring.application.name=tengxt-dashboard
```

启动应用，在浏览器中输入 `http://localhost:8000/hystrix`

单体应用的监控：通过 `URL:http://applicationName:port/actuator/hystrix.stream` 开启，实现对具体某个服务实例的监控。

例如：对 provider 服务监控

在`spring-cloud-provider`项目的 `pom.xml`中添加依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
``` 
添加配置
```properties
management.endpoints.web.exposure.include=hystrix.stream
server.port=2000
``` 
在 `http://localhost:8000/hystrix` 的页面中输入 `http://localhost:2000/actuator/hystrix.stream` 查看监控信息

##### Zuul
在项目 `spring-cloud-zuul` 的 `pom.xml`中引入依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
```

启用 Zuul 的网关代理
```java
@EnableZuulProxy        // 启用 Zuul 的网关代理功能
@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainClass.class, args);
    }
}
```

添加配置
```properties
zuul.ignored-services='*'
# zuul 的访问前缀
zuul.prefix=/tengxt
# 配置路由的目标服务名
zuul.routes.employee.serviceId=tengxt-feign-consumer
# 匹配的路由规则
zuul.routes.employee.path=/zuul-emp/**
```





