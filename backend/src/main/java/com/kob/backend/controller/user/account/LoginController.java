package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/account/token/")
    public Map<String, String> getToken(@RequestBody Map<String, String> map) throws Exception{
        String username = map.get("username");
        String password = map.get("password");
//        System.out.println("username: " + username + " password: " + password);
        return loginService.getToken(username, password);
    }
}
