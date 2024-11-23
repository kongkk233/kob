package com.kob.backend.service.user.bot;

import com.kob.backend.pojo.Bot;

import java.util.List;

public interface GetListService {
    //user信息存在token中，所以不需要传入参数
    List<Bot> getList();
}
