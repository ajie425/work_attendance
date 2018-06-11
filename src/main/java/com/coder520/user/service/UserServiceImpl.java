package com.coder520.user.service;

import com.coder520.common.utils.SecurityUtils;
import com.coder520.user.dao.UserMapper;
import com.coder520.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    /**
     *@Author Ajie [1134846386@qq.com]
     *@Date 2018/6/7 0007 21:56
     *@Description 根据用户名查询用户
     */
    @Override
    public User findUserByName(String username) {
        User user = userMapper.selectByUserName(username);
        return user;
    }

    @Override
    public void createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setPassword(SecurityUtils.encrptyPassword(user.getPassword()));
        userMapper.insertSelective(user);
    }
}
