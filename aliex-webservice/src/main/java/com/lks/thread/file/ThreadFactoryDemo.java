package com.lks.thread.file;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lks
 * @description NamedThreadFactory 线程池工厂
 * @e-mail 1056224715@qq.com
 * @date 2023/12/29 11:06
 */
public class ThreadFactoryDemo implements ThreadFactory {

    private final AtomicInteger poolNumber = new AtomicInteger(1);

    private final ThreadGroup threadGroup;

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public final String namePrefix;


    public ThreadFactoryDemo(String name) {
        SecurityManager s = System.getSecurityManager();
        threadGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        if (null == name || "".equals(name.trim())) {
            name = "pool";
        }
        namePrefix = name + "-" + poolNumber.getAndIncrement() + "-thread-";
    }

    /**
     * 线程创建一个新的线程
     *
     * @param r
     * @return
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(threadGroup, r, namePrefix + threadNumber.getAndIncrement(), 0);
        //是否是守护线程，是守护线程给他设为false
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
