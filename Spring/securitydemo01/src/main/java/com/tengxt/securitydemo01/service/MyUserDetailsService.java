package com.tengxt.securitydemo01.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tengxt.securitydemo01.entity.Users;
import com.tengxt.securitydemo01.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
                AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_sale,ROLE_admin");
        // 从查询数据库返回users对象，得到用户名和密码并返回
        return new User(users.getUsername(), new BCryptPasswordEncoder().encode(users.getPassword()), auths);
    }
}
