package com.tengxt.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginMemberVO implements Serializable {
    private Integer id;
    private String userName;
    private String email;
}
