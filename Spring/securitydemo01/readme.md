# Spring Security

Spring 是非常流行和成功的 Java 应用开发框架，Spring Security 正是 Spring 家族中的成员。Spring Security 基于 Spring 框架，提供了一套 Web 应用安全性的完整解决方案。

正如你可能知道的关于安全方面的两个主要区域是“认证”和“授权”（或者访问控制），
一般来说，Web 应用的安全性包括**用户认证**（Authentication）和**用户授权**（Authorization）两个部分，这两点也是 Spring Security 重要核心功能。

（1）用户认证指的是：验证某个用户是否为系统中的合法主体，也就是说用户能否访问该系统。用户认证一般要求用户提供用户名和密码。系统通过校验用户名和密码来完成认证过程。通俗点说就是系统认为用户是否能登录

（2）用户授权指的是验证某个用户是否有权限执行某个操作。在一个系统中，不同用户所具有的权限是不同的。比如对一个文件来说，有的用户只能进行读取，而有的用户可以进行修改。一般来说，系统会为不同的用户分配不同的角色，而每个角色则对应一系列的权限。通俗点讲就是系统判断用户是否有权限去做某些事情。

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
    
    第一步 创建配置类，设置使用哪个 `userDetailsService` 实现类
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
2. 查询数据库完成用户认证

    **整合 MybatisPlus 完成数据库操作**
    
    第一步：引入相关依赖
    ```xml
    <!--mybatis-plus-->
    		<dependency>
    			<groupId>com.baomidou</groupId>
    			<artifactId>mybatis-plus-boot-starter</artifactId>
    			<version>3.0.5</version>
    		</dependency>
    		<!--mysql-->
    		<dependency>
    			<groupId>mysql</groupId>
    			<artifactId>mysql-connector-java</artifactId>
    		</dependency>
    ```
    第二步：创建数据库和数据表
    ```sql
    CREATE TABLE `users` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `username` varchar(20) NOT NULL,
      `password` varchar(100) DEFAULT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `username` (`username`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    ```
    第三步：创建对应的实体类
    
    第四步：整合 MybatisPlus 制作 mapper
    ```java
    @Repository
    public interface UsersMapper extends BaseMapper<Users> {}
    ```
    配置 mysql 数据库连接
    
    Spring Boot 2.2.1.RELEASE 以上，默认使用的 mysql 版本是 8.0 以上
    ```properties
    #mysql 数据库连接
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/security_demo?serverTimezone=GMT%2B8
    spring.datasource.username=root
    spring.datasource.password=root
    ```
    第五步：制作登录实现类
    ```java
    @Service("userDetailsService")
    public class MyUserDetailsService implements UserDetailsService {   
        @Autowired
        private UsersMapper usersMapper;
    
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // 调用userMapper方法，根据用户名查询数据库
            QueryWrapper<Users> wrapper = new QueryWrapper<>();
            // where username = ?
            wrapper.eq("username", username);
            Users users = usersMapper.selectOne(wrapper);
            // 判断
            if (null == users) {
                // 数据库没有改用户，认证失败
                throw new UsernameNotFoundException("用户名不存在！");
            }
    
            List<GrantedAuthority> auths =
                    AuthorityUtils.commaSeparatedStringToAuthorityList("role");
            // 从查询数据库返回users对象，得到用户名和密码并返回
            return new User(users.getUsername(), new BCryptPasswordEncoder().encode(users.getPassword()), auths);
        }
    }
    ```
    第六步：在启动类加上 MapperScan 注解
    ```java
    @SpringBootApplication
    @MapperScan("com.tengxt.securitydemo01.mapper")
    public class Securitydemo01Application {}
    ```
3. 认证授权注解使用
    **`@Secured` 注解**：用户具有某个角色，可以访问方法
    1. 在启动类（配置类）开启注解
    ```java
    @EnableGlobalMethodSecurity(securedEnabled = true)
    public class Securitydemo01Application {}
    ```
    2. 在 controller 的方法上面使用注解，设置角色
    ```java
    @GetMapping("update")
    @Secured({"ROLE_sale","ROLE_manager"})
    public String update() {}
    ```
    3. 在 MyUserDetailsService 设置用户角色
    ```java
    AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_sale,ROLE_admin");
    ```
    **`@PreAuthorize`注解**：方法之前进行校验
    1. 在启动类（配置类）开启注解
    ```java
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class Securitydemo01Application {}
    ```
    2. 在 controller 的方法上面使用注解，设置角色
    ```java
    @GetMapping("update")
    @PreAuthorize("hasAnyAuthority('admins')")
    public String update() {}
    ```
    **`@PostAuthorize`注解**：方法执行之后返回
    1. 在启动类（配置类）开启注解
    ```java
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class Securitydemo01Application {}
    ```
    2. 在 controller 的方法上面使用注解，设置角色
    ```java
    @GetMapping("update")
    @PostAuthorize("hasAnyAuthority('admins')")
    public String update() {}
    ```
    **`@PostFilter`注解**：方法返回数据进行过滤
    **`@PreFilter`注解**：传入方法数据进行过滤
4. 自动登录
    Cookie 技术、安全框架机制实现自动登录
    第一步 创建数据库表
    ```sql
    CREATE TABLE persistent_logins (
    	username VARCHAR (64) NOT NULL,
    	series VARCHAR (64) PRIMARY KEY,
    	token VARCHAR (64) NOT NULL,
    	last_used TIMESTAMP NOT NULL
    )
    ```
    第二步 配置类，注入数据源，配置操作数据库对象
    ```java
    @Autowired
    private DataSource dataSource; // 注入数据源
    // 配置对象
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 开启自动创建表 persistent_logins
       // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
    ```
    第三步 配置类配置自动登录
    ```java
    .and().rememberMe().tokenRepository(persistentTokenRepository())
    .tokenValiditySeconds(60)   // 设置有效时长，单位秒
    .userDetailsService(userDetailsService)
    ```
    第四步 登录页添加复选框 name 类型值必须是 remember-me 
    ```html
    记住我：<input type="checkbox" name="remember-me" title="记住密码"/>
    ```
#### 授权