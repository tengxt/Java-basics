package com.tengxt.securitydemo01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 有参构造
@NoArgsConstructor // 无参构造
public class Users {
    private Integer id;
    private String username;
    private String password;
}
