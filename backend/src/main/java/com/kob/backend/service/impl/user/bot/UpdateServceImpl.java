package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.bot.UpdateService;
import com.kob.backend.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;
    @Override
    public Map<String, String> updateBot(Map<String, String> data) {
        User user = UserUtil.getuser();

        int bot_id = Integer.parseInt(data.get("bot_id"));
        String title = data.get("title");
        String description = data.get("description");
        String content =  data.get("content");
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
        Bot bot = botMapper.selectById(bot_id);
        if(bot == null){
            res.put("error_message", "Bot不存在或已被删除");
            return res;
        }
        if(!bot.getUserId().equals(user.getId())){
            res.put("error_message", "无权限修改该Bot");
            return res;
        }
        Bot new_bot = new Bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getRating(),
                bot.getCreatetime(),
                new Date()
        );
        botMapper.updateById(new_bot);
        res.put("error_message", "success");
        return res;
    }
}
