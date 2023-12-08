package com.lks.scheduler.thread;

import com.lks.scheduler.config.SpringContextUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author lks
 * @description 任务线程
 * @e-mail 1056224715@qq.com
 * @date 2023/12/7 10:16
 */
@Data
public class CronTaskRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CronTaskRunnable.class);

    private String beanName;

    private String methodName;

    private String params;

    private String taskId;

    public CronTaskRunnable() {
    }

    public CronTaskRunnable(String taskId, String beanName, String methodName, String params) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
        this.taskId = taskId;
    }

    /**
     * 执行任务
     *
     * @author CC
     * @since 2023/4/21 0021
     **/
    @Override
    public void run() {
        StopWatch watch = new StopWatch();
        watch.start();
        //具体的任务。
       // log.info("定时任务开始执行 - taskId:{},bean：{}，方法：{}，参数：{}", taskId, beanName, methodName, params);
        //任务处理的方式：
        //todo  1就在这里执行：模拟任务
        //todo  2开启策略模式，根据任务类型 调度不同的任务
        //todo  3使用反射：传来bean名字，方法名字，调用不同的任务
        //todo  4开启队列，把要执行的任务放到队列中，然后执行 —— 使用场景：每个任务执行很耗时的情况下使用
        //后续更新，采用队列的方式去注入，任务比较耗时
        // 这里采用的是第三种方法，注入的类名和类方法进行定时任务注册
        try {
            Object target = SpringContextUtils.getBean(beanName);
            Method method = null;
            if (StringUtils.isNotEmpty(params)) {
                method = target.getClass().getDeclaredMethod(methodName, String.class);
            } else {
                method = target.getClass().getDeclaredMethod(methodName);
            }
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotEmpty(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception ex) {
            log.error(String.format("定时任务执行异常 - taskId:{},bean：%s，方法：%s，参数：%s ", taskId, beanName, methodName, params), ex);
        }
        watch.stop();
      //  log.info("定时任务执行结束 - taskId:{},bean：{}，方法：{}，参数：{}，耗时：{} 毫秒", taskId, beanName, methodName, params, watch.getTotalTimeMillis());

    }

    /**
     * 这里主要是为了匹配是否相同的任务（可以通过任务ID,可以通过beanClass 和 methodName）
     * 这里采用的是 beanClass 和 methodName
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CronTaskRunnable that = (CronTaskRunnable) o;
        if (params == null) {
            return beanName.equals(that.beanName) &&
                    methodName.equals(that.methodName) &&
                    that.params == null;
        }

        return beanName.equals(that.beanName) &&
                methodName.equals(that.methodName) &&
                params.equals(that.params);
    }

    @Override
    public int hashCode() {
        if (params == null) {
            return Objects.hash(beanName, methodName);
        }
        return Objects.hash(beanName, methodName, params);
    }
}
