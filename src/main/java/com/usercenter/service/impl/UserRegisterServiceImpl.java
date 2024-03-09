package com.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.usercenter.dto.req.UserRegisterRequestDTO;
import com.usercenter.entity.User;
import com.usercenter.mapper.UserMapper;
import com.usercenter.service.UserRegisterService;
import com.usercenter.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    @Resource
    private UserMapper userMapper;
    @Override
    public Boolean userRegister(UserRegisterRequestDTO userRegisterRequestDTO) {
        String userAccount = userRegisterRequestDTO.getUserAccount();
        String userPassword = userRegisterRequestDTO.getUserPassword();
        String checkPassword = userRegisterRequestDTO.getCheckPassword();
        String planetCode = userRegisterRequestDTO.getPlanetCode();
        //根据账号查询，账号不能重复
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserAccount,userAccount);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (user != null){
            return Boolean.FALSE;
        }
        //星球编号不能重复
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPlanetCode,planetCode);
        User userCode = userMapper.selectOne(queryWrapper);
        if (userCode != null){
            return Boolean.FALSE;
        }
        //密码加密
        String md5 = MD5Util.MD5(userPassword);
        User dbUser = new User();
        dbUser.setUserAccount(userAccount);
        dbUser.setUserPassword(md5);
        dbUser.setPlanetCode(planetCode);
        int result = userMapper.insert(dbUser);
        return result>0?Boolean.TRUE:Boolean.FALSE;
    }
}
