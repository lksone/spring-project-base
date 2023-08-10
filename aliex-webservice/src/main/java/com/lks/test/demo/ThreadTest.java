package com.lks.test.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/8/10 21:50
 */
@Slf4j
public class ThreadTest {

    @Autowired
    private ThreadPoolTaskExecutor commonTaskExecutor;


    @Async(value = "commonTaskExecutor")
    public void test(){
        log.info("异步线程启动 started.");
    }
}
