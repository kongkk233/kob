package com.kob.backend.controller.user.bot;

import com.kob.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddController {
    @Autowired
    private AddService addService;

    @PostMapping("/user/bot/add/")
    public Map<String, String> addBot(@RequestBody Map<String, String> data) {
        return addService.addBot(data);
    }

}
