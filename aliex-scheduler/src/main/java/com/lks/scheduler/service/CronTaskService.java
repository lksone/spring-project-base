package com.lks.scheduler.service;

import com.lks.scheduler.entity.CronJob;

import java.util.List;

/**
 * @author lks
 * @description 定时任务的处理逻辑
 * @e-mail 1056224715@qq.com
 * @date 2023/12/7 14:53
 */
public interface CronTaskService {

    /**
     * 添加任务信息
     *
     * @param cronJob 添加任务信息
     * @return
     */
    boolean addCronTask(CronJob cronJob);

    /**
     * 删除定时任务信息
     *
     * @param cronJob 删除定时任务信息
     * @return
     */
    boolean deleteCronTask(CronJob cronJob);


    /**
     * 更新定时任务信息
     *
     * @param cronJob
     * @return
     */
    boolean updateCronTask(CronJob cronJob);

    /**
     * 控制定时任务
     *
     * @param cronJob
     * @return
     */
    boolean controlCronTask(CronJob cronJob);


    /**
     * 查询获取所有的任务信息
     *
     * @return
     */
    List<CronJob> findCronTaskList();
}
