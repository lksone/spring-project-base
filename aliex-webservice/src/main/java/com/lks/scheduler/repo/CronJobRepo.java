package com.lks.scheduler.repo;

import com.lks.scheduler.entity.CronJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author lks
 * @description 定时任务
 * @e-mail 1056224715@qq.com
 * @date 2023/12/7 14:41
 */
public interface CronJobRepo extends JpaRepository<CronJob, String> {

    /**
     * 查询
     *
     * @param normal
     * @return
     */
    @Query("select jt from com.lks.scheduler.repo.CronJob jt where jt.jobStatus=:normal")
    List<CronJob> getSysJobListByStatus(Integer normal);
}
