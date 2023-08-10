package com.lks.netty.utils;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author lks
 * @description 自定义线程池
 * @e-mail 1056224715@qq.com
 * @date 2023/8/10 21:09
 */
public class ThreadPool {

    public static ThreadPoolTaskExecutor threadPoolExecutor = null;

    public ThreadPool() {

    }


    public Executor initPool() {
        synchronized (ThreadPool.class) {
            if (threadPoolExecutor == null) {
                threadPoolExecutor = new ThreadPoolTaskExecutor();
                //核心线程数
                threadPoolExecutor.setCorePoolSize(5);
                //最大线程数量
                threadPoolExecutor.setMaxPoolSize(10);
                //初始化队列大小
                threadPoolExecutor.setQueueCapacity(1000);
                //保持线程存活时间
                threadPoolExecutor.setKeepAliveSeconds(60);
                //线程前缀名称
                threadPoolExecutor.setThreadNamePrefix("netty-start-demo-");
                //核心线程会一直存活，即使没有任务需要执行。（默认false）时，核心线程会超时关闭
                threadPoolExecutor.setAllowCoreThreadTimeOut(false);
                //默认:AbortPolicy 丢弃任务，抛运行时异常
                //CallerRunsPolicy由调用线程处理该任务
                threadPoolExecutor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
                //初始化线程池
                threadPoolExecutor.initialize();
            }
        }
        return threadPoolExecutor;
    }


    /**
     * 执行
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }


}
