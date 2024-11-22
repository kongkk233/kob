package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmPassword) {
        Map<String, String> response = new HashMap<>();
        try{
            if(username ==null){
                response.put("error_message", "username is null");
                return response;
            }
            if(password ==null || confirmPassword == null){
                response.put("error_message", "password is null");
                return response;
            }
            username = username.trim();
            if(username.length() < 6 || username.length() > 100){
                response.put("error_message", "username length is not valid");
                return response;
            }
            if(password.length() < 6 || password.length() > 100){
                response.put("error_message", "password length is not valid");
                return response;
            }
            if(!password.equals(confirmPassword)){
                response.put("error_message", "password is not equal to confirmPassword");
                return response;
            }
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            List<User> users = userMapper.selectList(queryWrapper);
            if(!users.isEmpty()){
                response.put("error_message", "username is already exist");
                return response;
            }
            //存入数据库
            String encodedPassword = passwordEncoder.encode(password);
            String photo = "https://cdn.acwing.com/media/user/profile/photo/91772_lg_fa5623fac1.jpg";
            User user = new User(null, username, encodedPassword, photo);
            userMapper.insert(user);
            response.put("error_message", "success");
        } catch (Exception e) {
            response.put("error_message", e.toString());
        }
        return response;
    }
}
