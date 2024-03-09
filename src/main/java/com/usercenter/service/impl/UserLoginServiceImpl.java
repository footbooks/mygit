package com.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.usercenter.entity.User;
import com.usercenter.mapper.UserMapper;
import com.usercenter.service.UserLoginServie;
import com.usercenter.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginServie {
    @Resource
    private UserMapper userMapper;
    @Override
    public User userLogin(String userAccount, String userPassword) {
        //根据用户账号在数据库查找用户信息
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUserAccount,userAccount);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user==null){
            log.info("账号错误，用户不存在");
            return null;
        }
        //对用户输入的密码进行加密，与数据库进行比较
        String md5Password = MD5Util.MD5(userPassword);
        String dbPassword = user.getUserPassword();
        if(!dbPassword.equalsIgnoreCase(md5Password)){
            log.info("密码错误");
            return null;
        }
        return user;
    }
}
