package com.lks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;


/**
 * Hello world!
 */
@Slf4j
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class ScheduleApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ScheduleApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        // 获取系统相关信息
        Properties props = System.getProperties();
        // 打印系统信息
        log.info("-------------------------------系统信息---------------------------------");
        log.info("服务器安装系统：{}", props.getProperty("os.name"));
        log.info("服务器安装系统版本：{}", props.getProperty("os.version"));
        log.info("安装系统JAVA：{}", props.getProperty("java.version"));
        log.info("-------------------------------系统信息---------------------------------");
    }
}
