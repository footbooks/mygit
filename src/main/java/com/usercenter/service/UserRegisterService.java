package com.usercenter.service;

import com.usercenter.dto.req.UserRegisterRequestDTO;
import com.usercenter.entity.User;

public interface UserRegisterService {
    Boolean userRegister(UserRegisterRequestDTO userRegisterRequestDTO);
}
