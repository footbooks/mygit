package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.usercenter.mapper")
public class UserCenterApp {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApp.class);
    }
}
