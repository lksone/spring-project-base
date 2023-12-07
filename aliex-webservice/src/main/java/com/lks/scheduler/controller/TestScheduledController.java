package com.lks.scheduler.controller;

import com.lks.scheduler.register.CronTaskRegistrar;
import com.lks.scheduler.thread.CronTaskRunnable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lks
 * @description 定时任务控制器
 * @e-mail 1056224715@qq.com
 * @date 2023/12/7 10:31
 */
@RestController
@RequestMapping("/scheduled")
public class TestScheduledController {

    @Resource
    private CronTaskRegistrar cronTaskRegistrar;

    /**
     * 获取任务列表
     *
     * @return java.util.List<com.cc.ssd.task.SchedulingRunnableTask>
     * @author CC
     * @since 2023/4/21 0021
     **/
    @GetMapping
    public List<CronTaskRunnable> getScheduledTasks() {
        return cronTaskRegistrar.getScheduledTasks();
    }

    /**
     * 添加任务(优化，将任务添加到数据库中，启动的时候添加到注册器中)
     *
     * @param param param
     * @return java.lang.String
     * @author CC
     * @since 2023/4/21 0021
     **/
    @PostMapping
    public String addCronTask(@RequestBody Map<String, Object> param) {
        //自己拿任务参数的逻辑：可以把每个任务保存到数据库，重新启动任务的同时，加载这些任务到任务调度中心
        String taskId = (String) param.get("taskId");
        Integer taskType = (Integer) param.get("taskType");
        String taskName = (String) param.get("taskName");
        Object params = param.get("params");
        //添加任务参数
      //  CronTaskRunnable task = new CronTaskRunnable(taskId, taskType, taskName, params);
        //注册任务：cron表达式，可以从传入不一样的
      //  cronTaskRegistrar.addCronTask(task, "0/5 * * * * ?");
        return "ok";
    }

    /**
     * 根据任务id删除定时任务
     *
     * @param taskId 任务id
     * @return java.lang.String
     * @author CC
     * @since 2023/4/21 0021
     **/
    @DeleteMapping
    public String removeCronTaskByTaskId(@RequestParam String taskId) {
        cronTaskRegistrar.removeCronTaskByTaskId(taskId);
        return "ok";
    }

    /**
     * 删除全部任务
     *
     * @return java.lang.String
     * @author CC
     * @since 2023/4/21 0021
     **/
    @DeleteMapping("/removeAll")
    public String removeCronTask() {
        cronTaskRegistrar.destroy();
        return "ok";
    }

}
