package com.lks.scheduler.service.impl;

import com.lks.scheduler.config.SysJobStatus;
import com.lks.scheduler.entity.CronJob;
import com.lks.scheduler.register.CronTaskRegistrar;
import com.lks.scheduler.repo.CronJobRepo;
import com.lks.scheduler.service.CronTaskService;
import com.lks.scheduler.thread.CronTaskRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lks
 * @description 控制
 * @e-mail 1056224715@qq.com
 * @date 2023/12/7 14:58
 */
@Service
@Slf4j
public class CronTaskServiceImpl implements CronTaskService {


    private final CronJobRepo cronJobRepo;

    private final CronTaskRegistrar cronTaskRegistrar;

    @Autowired
    public CronTaskServiceImpl(CronJobRepo cronJobRepo, CronTaskRegistrar cronTaskRegistrar) {
        this.cronJobRepo = cronJobRepo;
        this.cronTaskRegistrar = cronTaskRegistrar;
    }

    /**
     * 添加任务信息
     *
     * @param cronJob 添加任务信息
     * @return
     */
    @Override
    public boolean addCronTask(CronJob cronJob) {
        //存入数据库
        CronJob success = cronJobRepo.save(cronJob);
        if (success == null) {
            return Boolean.FALSE;
        } else {
            //如果是正常状态就直接启动
            if (cronJob.getJobStatus().equals(SysJobStatus.NORMAL)) {
                CronTaskRunnable task = new CronTaskRunnable(success.getJobId(), cronJob.getBeanName(), cronJob.getMethodName(), cronJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, cronJob.getCronExpression());
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 删除定时任务信息
     *
     * @param cronJob 删除定时任务信息
     * @return
     */
    @Override
    public boolean deleteCronTask(CronJob cronJob) {
        cronJobRepo.deleteById(cronJob.getJobId());
        if (cronJob.getJobStatus().equals(SysJobStatus.NORMAL)) {
            cronTaskRegistrar.removeCronTaskByTaskId(cronJob.getJobId());
        }
        return Boolean.TRUE;
    }

    /**
     * 更新定时任务信息
     *
     * @param cronJob
     * @return
     */
    @Override
    public boolean updateCronTask(CronJob cronJob) {
        CronJob success = cronJobRepo.saveAndFlush(cronJob);
        if (success == null) {
            return Boolean.FALSE;
        } else {
            //先移除再添加
            if (cronJob.getJobStatus().equals(SysJobStatus.NORMAL)) {
                cronTaskRegistrar.removeCronTaskByTaskId(success.getJobId());
            }
            //再添加任务
            if (success.getJobStatus().equals(SysJobStatus.NORMAL)) {
                CronTaskRunnable task = new CronTaskRunnable(success.getJobId(), success.getBeanName(), success.getMethodName(), success.getMethodParams());
                cronTaskRegistrar.addCronTask(task, success.getCronExpression());
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 控制定时任务
     *
     * @param cronJob
     * @return
     */
    @Override
    public boolean controlCronTask(CronJob cronJob) {
        CronJob existedSysJob = cronJobRepo.getReferenceById(cronJob.getJobId());
        if (cronJob.getJobStatus().equals(SysJobStatus.NORMAL)) {
            CronTaskRunnable task = new CronTaskRunnable(cronJob.getJobId(), existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
            cronTaskRegistrar.addCronTask(task, existedSysJob.getCronExpression());
        } else {
            cronTaskRegistrar.removeCronTaskByTaskId(cronJob.getJobId());
        }
        return Boolean.TRUE;
    }

    @Override
    public List<CronJob> findCronTaskList() {
        return cronJobRepo.findAll();
    }
}
