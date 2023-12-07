package com.lks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 * @author Administrator
 */
@Slf4j
@EnableScheduling
@EnableJpaAuditing
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class WebServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(WebServiceApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        log.info("项目启动成功：env={},", environment.getProperty("system.os"));
    }
}