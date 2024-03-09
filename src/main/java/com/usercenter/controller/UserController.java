package com.usercenter.controller;

import com.usercenter.api.base.BaseApiController;
import com.usercenter.api.base.BaseResponse;
import com.usercenter.dto.req.UserLoginRequestDTO;
import com.usercenter.dto.req.UserRegisterRequestDTO;
import com.usercenter.entity.User;
import com.usercenter.service.UserLoginServie;
import com.usercenter.service.UserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin//解决跨域问题
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseApiController {
    @Resource
    private UserLoginServie userLoginServie;
    @Resource
    private UserRegisterService userRegisterService;
    @PostMapping("userLogin")
    public BaseResponse userLogin (@RequestBody UserLoginRequestDTO userLoginRequestDTO){
        log.info("参数校验中");
        //1.参数验证
        String userAccount = userLoginRequestDTO.getUserAccount();
        String userPassword = userLoginRequestDTO.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            return setResultError("账号或密码为空");
        }
        if( userAccount.length() < 4){
            return setResultError("账号格式不规范");
        }
        if ( userPassword.length() < 8){
            return setResultError("密码格式不规范");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            return setResultError("账号格式不规范");
        }
        //2.用户登录
        log.info("正在登录............");
        User user = userLoginServie.userLogin(userAccount, userPassword);
        if (user == null){
            return setResultError("账号或密码错误");//账号或密码错误
        }
        //3.生成用户token
        log.info("登录成功，正在生成token");
        //4.异步记录登录日志

        return setResultSuccessData(user);
    }
    @PostMapping("userRegister")
    public BaseResponse userRegister(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO){
        //参数校验
        String userAccount = userRegisterRequestDTO.getUserAccount();
        String userPassword = userRegisterRequestDTO.getUserPassword();
        String checkPassword = userRegisterRequestDTO.getCheckPassword();
        String planetCode = userRegisterRequestDTO.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            return setResultError("请输入完整信息");
        }
        if (userAccount.length() < 4){
            return setResultError("账号长度不能小于四位");
        }
        //判断用户两次输入的密码是否一致
        if (!userPassword.equals(checkPassword)){
            return setResultError("输入密码不一致");
        }
        if (userPassword.length() < 8){
            return setResultError("密码长度不能小于8位");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            return setResultError("账号格式不规范");
        }
        //用户注册
        Boolean result = userRegisterService.userRegister(userRegisterRequestDTO);
        return result?setResultSuccess():setResultError();
    }
}
