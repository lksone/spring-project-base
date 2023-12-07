package com.lks.scheduler;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledFuture;

/**
 * @author lks
 * @description 将定时任务注入到 定时任务中
 * @e-mail 1056224715@qq.com
 * @date 2023/12/7 9:47
 */
public class DemoScheduler {


    /**
     * 1、使用Spring自带的TaskScheduler注册任务
     * 2、注册后返回：ScheduledFuture，用于取消定时任务
     */
    @Resource
    private TaskScheduler taskScheduler;

    public void registrarTask() {
        //具体的任务Runnable（一般使用类实现Runnable接口）
        Runnable taskRunnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        //cron表达式触发器
        CronTrigger trigger = new CronTrigger("0/5 * * * * ?");
        //开启定时任务的真正方法,开启定时任务
        ScheduledFuture<?> future = this.taskScheduler.schedule(taskRunnable, trigger);
        //取消定时任务
        future.cancel(true);
    }
}
