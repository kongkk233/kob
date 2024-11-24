package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.bot.RemoveService;
import com.kob.backend.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveServiceImpl implements RemoveService {

    @Autowired
    private BotMapper botMapper;
    @Override
    public Map<String, String> removeBot(Map<String, String> data) {
        User user = UserUtil.getuser();
        int bot_id = Integer.parseInt(data.get("bot_id"));

        Bot bot = botMapper.selectById(bot_id);
        Map<String, String> res = new HashMap<>();
        if(bot == null){
            res.put("error_message", "Bot不存在或已被删除");
            return res;
        }
        if(!bot.getUserId().equals(user.getId())){
            res.put("error_message", "无权限删除该Bot");
            return res;
        }
        botMapper.deleteById(bot_id);
        res.put("message", "success");
        return res;
    }
}
