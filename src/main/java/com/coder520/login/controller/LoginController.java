package com.coder520.login.controller;

import com.coder520.common.utils.SecurityUtils;
import com.coder520.user.entity.User;
import com.coder520.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     *@Author Ajie [1134846386@qq.com]
     *@Date 2018/6/7 0007 21:54
     *@Description 登录页面
     */
    @RequestMapping
    public String login(){

        return "login";
    }

    /**
     *@Author Ajie [1134846386@qq.com]
     *@Date 2018/6/7 0007 21:55
     *@Description 校验登录
     */
    @RequestMapping("/check")
    @ResponseBody
    public String checkLogin(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String username = request.getParameter("username");
        String pwd = request.getParameter("password");

        User user = userService.findUserByName(username);
        if (user!=null){
            if (SecurityUtils.checkPassword(pwd,user.getPassword())){
                request.getSession().setAttribute("userinfo",user);
                return "login_success";
            }else {
                return "login_fail";
            }
        }else {
            return "login_fail";
        }
    }
    @RequestMapping("/register")
    @ResponseBody
    public String register(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        userService.createUser(user);
        return "succ";
    }
}
