package com.kob.backend.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/user/all/")
    public List<User> getAll(){
        return userMapper.selectList(null);
    }

    @GetMapping("/user/{userid}/")
    public List<User> getuser(@PathVariable int userid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userid);
        return userMapper.selectList(queryWrapper);
    }

    @GetMapping("/user/add/{userid}/{username}/{password}/")
    public String addUser(
            @PathVariable int userid,
            @PathVariable String username,
            @PathVariable String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoded_password = passwordEncoder.encode(password);
        User user = new User(userid, username, encoded_password);
        userMapper.insert(user);
        return "add User success!";
    }

    @GetMapping("/user/delete/{userid}/")
    public String deleteUser(@PathVariable int userid){
        userMapper.deleteById(userid);
        return "delete User success!";
    }
}
