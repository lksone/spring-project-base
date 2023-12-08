package com.lks.scheduler.thread;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/**
 * @author lks
 * @description CronTaskFuture类中使用的是ScheduledFuture对象来表示定时任务的执行结果。
 * ——最后ps：也可以不要这个记录类，直接缓存ScheduledFuture对象。
 * 用来记录单独的Future、定时任务注册任务后产生的
 * @e-mail 1056224715@qq.com
 * @date 2023/12/7 9:56
 */
public final class CronTaskFuture {


    public ScheduledFuture<?> future;


    /**
     * 取消定时任务
     */
    public void cancel() {
        try {
            ScheduledFuture<?> future = this.future;
            if (Objects.nonNull(future)) {
                future.cancel(true);
            }
        } catch (Exception e) {
            throw new RuntimeException("销毁定时任务失败！");
        } finally {
        }

    }
}
