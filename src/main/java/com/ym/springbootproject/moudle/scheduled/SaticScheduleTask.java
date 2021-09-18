package com.ym.springbootproject.moudle.scheduled;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDateTime;

/**
 * @author Meng
 * @Description: TODO 定时任务测试类
 * @date 2021/8/10 10:10
 */
@Api(value = "定时任务")
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableAsync        // 2.开启多线程 异步线程
public class SaticScheduleTask {

    //3.添加定时任务
    //@Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    @Async
    //@Scheduled(cron = "${scheduled.cronTime}")
    public void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }

}
