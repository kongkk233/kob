package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pk/")
public class BotinfoController {
    @RequestMapping("getbotinfo/")
    public Map<String, String> getBotInfo() {
        Map<String, String> Bot1 = new HashMap<>();
        Bot1.put("name", "triger");
        Bot1.put("rating", "1500");
        return Bot1;
    }
}
