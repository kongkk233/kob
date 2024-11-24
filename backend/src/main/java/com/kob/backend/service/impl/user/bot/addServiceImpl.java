package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class addServiceImpl implements AddService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> addBot(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();


        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> res = new HashMap<>();

        if(title == null || title.isEmpty()) {
            res.put("error_message", "标题不能为空");
            return res;
        }

        if(title.length() > 100) {
            res.put("error_message", "标题长度不能超过100");
            return res;
        }

        if(description == null || description.isEmpty()) {
            description = "这个用户很懒，什么也没留下~";
        }

        if(description.length() > 300) {
            res.put("error_message", "Bot描述的长度不能超过300");
            return res;
        }

        if(content == null || content.isEmpty()) {
            res.put("error_message", "内容不能为空");
            return res;
        }

        if(content.length() > 1000) {
            res.put("error_message", "内容长度不能超过1000");
            return res;
        }
        Date now = new Date();
        Bot bot = new Bot(null, user.getId(), title, description, content, 1500, now, now);
        botMapper.insert(bot);
        res.put("error_message", "success");
        return res;
    }
}
