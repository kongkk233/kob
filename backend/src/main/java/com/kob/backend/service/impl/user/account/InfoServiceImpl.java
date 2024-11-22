package com.kob.backend.service.impl.user.account;

import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.account.InfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.kob.backend.service.user.account.InfoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {
    @Override
    public Map<String, String> getInfo() {
        Map<String, String> response = new HashMap<>();
        try{
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
            User user = loginUser.getUser();
            response.put("error_message", "success");
            response.put("userid", user.getId().toString());
            response.put("username", user.getUsername());
            response.put("photo", user.getPhoto());
        } catch (Exception e) {
            response.put("error_message", e.toString());
        }
        return response;
    }
}
