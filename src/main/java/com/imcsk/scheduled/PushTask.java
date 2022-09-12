package com.imcsk.scheduled;

import com.imcsk.service.IPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Description 定时任务推送
 * @Author csk
 * @Date 2022/9/11
 */
@EnableScheduling
@Configuration
public class PushTask {
    @Autowired
    private IPushService iPushService;

    // 定时 早8点推送  0秒 0分 8时
    @Scheduled(cron = "0 0 9 * * ?")
    public void push(){
        iPushService.push();
    }
}
