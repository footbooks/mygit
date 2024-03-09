package com.usercenter.dto.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequestDTO implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    // https://www.code-nav.cn/
}
