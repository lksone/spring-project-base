package com.lks.scheduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author lks
 * @description 任务调度器配置类
 * @e-mail 1056224715@qq.com
 * @date 2023/12/7 9:51
 */
@Configuration
public class CronTaskConfig {


    /**
     * 任务调度器自定义配置
     */
    @Bean(name = "taskScheduler")
    public TaskScheduler taskScheduler() {
        // 任务调度线程池
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 定时任务执行线程池核心线程数：可同时执行4个任务
        taskScheduler.setPoolSize(4);
        taskScheduler.setRemoveOnCancelPolicy(true);
        // 线程名称前缀
        taskScheduler.setThreadNamePrefix("Cs-ThreadPool-");
        return taskScheduler;
    }
}
