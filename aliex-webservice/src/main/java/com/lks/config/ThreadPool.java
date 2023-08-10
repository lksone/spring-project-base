package com.lks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lks
 * @description 定義多個線程池
 * @e-mail 1056224715@qq.com
 * @date 2023/8/10 21:45
 */
@Configuration
public class ThreadPool {

    /**
     * 核心线程数 默认的核心线程数为1
     */
    private static final int CORE_POOL_SIZE = 2;
    /**
     * 最大线程数 默认的最大线程数是Integer.MAX_VALUE
     */
    private static final int MAX_POOL_SIZE = 20;
    /**
     * 缓冲队列数 默认的缓冲队列数是Integer.MAX_VALUE
     */
    private static final int QUEUE_CAPACITY = 50;
    /**
     * 允许线程空闲时间 默认的线程空闲时间为60秒
     */
    private static final int KEEP_ALIVE_SECONDS = 30;


    private AsyncTaskExecutor getAsyncTaskExecutor(String threadNamePrefix
            , int MAX_POOL_SIZE, int QUEUE_CAPACITY) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        taskExecutor.setThreadNamePrefix(threadNamePrefix);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return taskExecutor;
    }


    @Bean("destroyResource")
    public AsyncTaskExecutor destroyImGroupTaskExecutor() {
        return getAsyncTaskExecutor("del-resource-td-", MAX_POOL_SIZE, QUEUE_CAPACITY);
    }

    @Bean("statisticsData")
    public AsyncTaskExecutor statisticsDataExecutor() {
        return getAsyncTaskExecutor("save-data-td-", MAX_POOL_SIZE, QUEUE_CAPACITY);
    }

    @Bean("commonTaskExecutor")
    public AsyncTaskExecutor get() {
        return getAsyncTaskExecutor("common-ex-td-", MAX_POOL_SIZE, QUEUE_CAPACITY);
    }
}