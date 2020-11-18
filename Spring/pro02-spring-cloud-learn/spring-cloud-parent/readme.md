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