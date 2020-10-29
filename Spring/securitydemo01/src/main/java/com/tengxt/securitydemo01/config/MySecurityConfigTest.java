package com.tengxt.securitydemo01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MySecurityConfigTest extends WebSecurityConfigurerAdapter {

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 退出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();

        // 配置没有权限访问跳转的自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        http.formLogin()        // 自定义登录页面
            .loginPage("/login.html")       // 登录页面路径
            .loginProcessingUrl("/user/login")     // 登录访问的地址
            .defaultSuccessUrl("/test/index").permitAll()       // 登录成功之后，跳转的路径
            .and().authorizeRequests()
            .antMatchers("/","/test/hello","/user/login").permitAll() // 设置哪些路径可以直接访问，不需要认证
            // hasAnyAuthority : 当前登录用户，只有具有 admins 权限才能访问这个路径
            //    .antMatchers("/test/index").hasAnyAuthority("admins")
            // hasAnyAuthority : 当前登录用户，有 admins 或者 manager 权限都能访问
            //    .antMatchers("/test/index").hasAnyAuthority("admins,manager")
            // hasRole :
               .antMatchers("/test/index").hasRole("sale")
            // hasAnyRole
            //    .antMatchers("/test/index").hasAnyRole("admin")
            .anyRequest().authenticated()
            .and().csrf().disable();    // 关闭csrf防护
    }
}
