package com.ssh.scheduletask.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务并行执行
 * @author: ssh
 * @email:
 * @Date: 2020/5/13 10:15
 */
@Slf4j
@EnableAsync
@Component
public class AsyncScheduledTasks {

    /**
     * fixedDelay：固定延迟执行。距离上一次调用成功后2秒才执。
     */
//    @Async
//    @Scheduled(fixedDelay = 2000)
    public void reportCurrentTimeWithFixedDelay() {
        try {
            TimeUnit.SECONDS.sleep(3);
            log.info("Fixed Delay Task : The time is now {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
