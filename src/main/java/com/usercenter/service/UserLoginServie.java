package com.usercenter.service;

import com.usercenter.entity.User;

public interface UserLoginServie {
    User userLogin(String userAccount, String userPassword);
}
