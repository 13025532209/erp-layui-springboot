package com.example.demo.controller;

import com.example.demo.entity.Emp;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by more-time on 2019/6/30.
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/login")
    public ResponseResult login(String username, String pwd){
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
        // 执行认证登陆
        try {
            subject.login(token);
            return new ResponseResult(true,"登录成功");
        } catch (Exception e) {
            return new ResponseResult(false,"用户名或密码不正确");
        }
//
//        Emp emp = loginService.findEmpByUsername(username, pwd);
//        if(emp==null){
//            return new ResponseResult(false,"用户名或密码不正确");
//        }else{
//            return new ResponseResult(true,"登录成功");
//        }

      /*  if (subject.isAuthenticated()) {
            return new ResponseResult(true,"登录成功");
        } else {
            token.clear();
            return new ResponseResult(false,"登录失败");
        }*/

    }

    @GetMapping("/showName")
    public String showName(){
        Subject subject = SecurityUtils.getSubject();
        Emp emp = (Emp)subject.getPrincipal();
        return emp.getName();
    }
}
