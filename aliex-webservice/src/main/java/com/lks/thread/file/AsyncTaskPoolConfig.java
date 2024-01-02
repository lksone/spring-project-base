package com.lks.thread.file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author lks
 * @description 生成线程池
 * @e-mail 1056224715@qq.com
 * @date 2023/12/29 15:12
 */
@Configuration
@EnableAsync
public class AsyncTaskPoolConfig {

    /**
     * 默认线程池
     *
     * @return
     */
    @Bean("taskExecutor")
    public Executor taskExecutor() {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("系统最大线程数：" + i);
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        taskExecutor.setCorePoolSize(i);
        //最大线程数
        taskExecutor.setMaxPoolSize(i);
        //队列大小
        taskExecutor.setQueueCapacity(99999);
        //60秒不适用就自动销毁线程
        taskExecutor.setKeepAliveSeconds(60);
        //任务名称
        taskExecutor.setThreadNamePrefix("taskFileExecutor--");
        //等待任务在关机时完成
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //等待终止秒数
        taskExecutor.setAwaitTerminationSeconds(60);
        return taskExecutor;
    }
}
