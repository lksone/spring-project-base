package com.lks.scheduler.register;

import com.lks.scheduler.thread.CronTaskFuture;
import com.lks.scheduler.thread.CronTaskRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author lks
 * @description 定时任务注册器
 * @e-mail 1056224715@qq.com
 * @date 2023/12/7 9:53
 */
@Component
public class CronTaskRegistrar implements DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(CronTaskRegistrar.class);

    /**
     * 缓存任务
     * key：具体的任务
     * value：注册定时任务后返回的ScheduledFuture
     */
    private final Map<Runnable, CronTaskFuture> scheduledTasks = new ConcurrentHashMap<>(16);


    /**
     * 使用自定义的任务调度配置
     */
    @Resource(name = "taskScheduler")
    private TaskScheduler taskScheduler;

    /**
     * 获取任务调度配置
     *
     * @return 任务调度配置
     */
    public TaskScheduler getTaskScheduler() {
        return this.taskScheduler;
    }

    /**
     * 添加cronTask
     *
     * @param taskRunnable
     * @param cronExpression
     */
    public void addCronTask(Runnable taskRunnable, String cronExpression) {
        //1、第一步先验证是否正确的表达式
        boolean validExpression = CronExpression.isValidExpression(cronExpression);
        if (!validExpression) {
            throw new RuntimeException("cron表达式验证失败！");
        }
        //2、获取下次执行时间
        CronExpression parse = CronExpression.parse(cronExpression);
        LocalDateTime next = parse.next(LocalDateTime.now());
        if (Objects.nonNull(next)) {
            //定时任务下次执行的时间
            String format = next.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.info("定时任务下次执行的时间：{}", format);
        }
        //封装成 CronTask（cron任务）
        CronTask cronTask = new CronTask(taskRunnable, cronExpression);
        this.addCronTask(cronTask);
    }

    /**
     * 添加定时任务
     *
     * @param cronTask
     */
    private void addCronTask(CronTask cronTask) {
        if (Objects.nonNull(cronTask)) {
            //1有这个任务，先删除这个任务。再新增
            Runnable task = cronTask.getRunnable();
            String taskId = null;
            if (task instanceof CronTaskRunnable) {
                taskId = ((CronTaskRunnable) task).getTaskId();
            }
            //通过任务id获取缓存的任务，如果包含则删除，然后新增任务
            Runnable taskCache = this.getTaskByTaskId(taskId);
            if (Objects.nonNull(taskCache) && this.scheduledTasks.containsKey(taskCache)) {
                this.removeCronTaskByTaskId(taskId);
            }
            //2注册定时任务到调度中心
            CronTaskFuture scheduledFutureTask = this.scheduleCronTask(cronTask);
            //3缓存定时任务
            this.scheduledTasks.put(task, scheduledFutureTask);
            // 4可以将任务保存到数据库中……重新启动程序然后加载数据库中的任务到缓存中……

        }
    }

    /**
     * 注册 ScheduledTask 定时任务
     *
     * @param cronTask cronTask
     * @return 注册定时任务后返回的 ScheduledFutureTask
     */
    private CronTaskFuture scheduleCronTask(CronTask cronTask) {
        //注册定时任务后记录的Future
        CronTaskFuture scheduledTask = new CronTaskFuture();
        //开启定时任务的真正方法
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    public List<CronTaskRunnable> getScheduledTasks() {
        List<CronTaskRunnable> tasks = new ArrayList<>();
        Set<Runnable> keySet = scheduledTasks.keySet();
        //遍历所有运行的任务，如果已经存在，那么就将这个任务重新添加到列表中？
        keySet.forEach(key -> {
            CronTaskRunnable task = new CronTaskRunnable();
            if (key instanceof CronTaskRunnable) {
                CronTaskRunnable taskParent = (CronTaskRunnable) key;
                BeanUtils.copyProperties(taskParent, task);
            }
            tasks.add(task);
        });
        return tasks.stream().sorted(Comparator.comparing(CronTaskRunnable::getTaskId)).collect(Collectors.toList());
    }

    /**
     * 根据任务id删除单个定时任务
     *
     * @param taskId 任务id
     */
    public void removeCronTaskByTaskId(String taskId) {
        //通过任务id获取任务，获取所有的任务
        Runnable task = this.getTaskByTaskId(taskId);
        //需要通过任务id获取任务，然后再移除
        CronTaskFuture cronTaskFuture = this.scheduledTasks.remove(task);
        if (Objects.nonNull(cronTaskFuture)) {
            cronTaskFuture.cancel();
        }
    }

    /**
     * 通过任务id获取任务。未查询到返回null
     *
     * @param taskId 任务id
     * @return java.lang.Runnable
     * @author CC
     * @since 2023/4/21 0021
     **/
    private Runnable getTaskByTaskId(String taskId) {
        Assert.notNull(taskId, "任务id不能为空！");
        Set<Map.Entry<Runnable, CronTaskFuture>> entries = scheduledTasks.entrySet();
        //根据任务id获取该任务缓存
        Map.Entry<Runnable, CronTaskFuture> rcf = entries.stream().filter(rf -> {
            Runnable key = rf.getKey();
            String taskId1 = null;
            if (key instanceof CronTaskRunnable) {
                taskId1 = ((CronTaskRunnable) key).getTaskId();
            }
            return taskId.equals(taskId1);
        }).findAny().orElse(null);

        if (Objects.nonNull(rcf)) {
            return rcf.getKey();
        }
        return null;
    }

    /**
     * 删除所有的定时任务
     * <p>
     * DisposableBean是Spring框架中的一个接口，它定义了一个destroy()方法，
     * 用于在Bean销毁时执行清理工作。
     * 当一个Bean实现了DisposableBean接口时，
     * Spring容器会在该Bean销毁时自动调用destroy()方法，
     * 以便进行一些清理工作，例如释放资源等。
     * 如果您的Bean需要在销毁时执行一些清理工作，
     * 那么实现DisposableBean接口是一个很好的选择。
     */
    @Override
    public void destroy() {
        try {
            //关闭所有定时任务
            for (CronTaskFuture task : this.scheduledTasks.values()) {
                task.cancel();
            }
            //清空缓存
            this.scheduledTasks.clear();
            log.info("取消所有定时任务！");
            //todo 修改或删除数据库的任务,如果入库了那么就删除数据信息(调用就需要删除入库数据)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
