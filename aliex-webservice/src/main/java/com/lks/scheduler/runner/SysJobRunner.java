package com.lks.scheduler.runner;

import com.lks.scheduler.config.SysJobStatus;
import com.lks.scheduler.entity.CronJob;
import com.lks.scheduler.register.CronTaskRegistrar;
import com.lks.scheduler.repo.CronJobRepo;
import com.lks.scheduler.thread.CronTaskRunnable;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lks
 * @description
 *      当项目启动的时候;
 *      开启查询查询所有正常启动的定时任务，进行启动;
 * @e-mail 1056224715@qq.com
 * @date 2023/12/7 15:14
 */
@Service
public class SysJobRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);

    @Autowired
    private CronJobRepo cronJobRepo;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务
        List<CronJob> jobList = cronJobRepo.getSysJobListByStatus(SysJobStatus.NORMAL);
        if (CollectionUtils.isNotEmpty(jobList)) {
            for (CronJob job : jobList) {
                CronTaskRunnable task = new CronTaskRunnable(job.getJobId(),job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }
            logger.info("定时任务已加载完毕...");
        }
    }
}
