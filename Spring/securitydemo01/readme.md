# Spring Security

Spring 是非常流行和成功的 Java 应用开发框架，Spring Security 正是 Spring 家族中的
成员。Spring Security 基于 Spring 框架，提供了一套 Web 应用安全性的完整解决方
案。
正如你可能知道的关于安全方面的两个主要区域是“认证”和“授权”（或者访问控
制），一般来说，Web 应用的安全性包括用户认证（Authentication）和用户授权
（Authorization）两个部分，这两点也是 Spring Security 重要核心功能。

（1）用户认证指的是：验证某个用户是否为系统中的合法主体，也就是说用户能否访问
该系统。用户认证一般要求用户提供用户名和密码。系统通过校验用户名和密码来完成认
证过程。通俗点说就是系统认为用户是否能登录

（2）用户授权指的是验证某个用户是否有权限执行某个操作。在一个系统中，不同用户
所具有的权限是不同的。比如对一个文件来说，有的用户只能进行读取，而有的用户可以
进行修改。一般来说，系统会为不同的用户分配不同的角色，而每个角色则对应一系列的
权限。通俗点讲就是系统判断用户是否有权限去做某些事情。

### WEB 权限方案

#### 认证
1. 设置登录的用户名和密码

    第一种方式：通过配置文件
    ```java
       server.port=8111
       spring.security.user.name=tengxt
       spring.security.user.password=tengxt
    ```
    
    第二种方式：通过配置类
    ```java
       @Configuration
       public class mySecurityConfig extends WebSecurityConfigurerAdapter {
       
           @Override
           protected void configure(AuthenticationManagerBuilder auth) throws Exception {
               BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
               String password = passwordEncoder.encode("123");
               auth.inMemoryAuthentication()
                       .withUser("lucy")
                       .password(password)
                       .roles("admin");
           }
       
       
           @Bean
           PasswordEncoder password() {
               return new BCryptPasswordEncoder();
           }
       }
    ```
    
    第三种方式：自定义编写实现类
    
    第一步 创建配置类，设置使用哪个 userDetailsService实现类
    ```java
       @Configuration
       public class mySecurityConfigTest extends WebSecurityConfigurerAdapter {
       
           @Autowired
           private UserDetailsService userDetailsService;
       
           @Override
           protected void configure(AuthenticationManagerBuilder auth) throws Exception {
               auth.userDetailsService(userDetailsService).passwordEncoder(password());
           }
       
           @Bean
           PasswordEncoder password() {
               return new BCryptPasswordEncoder();
           }
       }
    ```
    第二步 编写实现类，返回User对象，User对象有用户名密码和操作权限
    ```java
    @Service("userDetailsService")
    public class MyUserDetailsService implements UserDetailsService {
    
        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            List<GrantedAuthority> auths =
                    AuthorityUtils.commaSeparatedStringToAuthorityList("role");
            return new User("marry", new BCryptPasswordEncoder().encode("123"), auths);
        }
    }
    ```
        
#### 授权