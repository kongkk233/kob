package com.kob.backend.controller.user.bot;

import com.kob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateController {
    @Autowired
    UpdateService updateService;

    @PostMapping("/user/bot/update/")
    public Map<String, String> updateBot(@RequestBody Map<String, String> data){
        return updateService.updateBot(data);
    }
}
