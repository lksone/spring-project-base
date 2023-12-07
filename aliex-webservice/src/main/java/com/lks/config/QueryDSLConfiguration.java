package com.lks.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class QueryDSLConfiguration {

	@Autowired
    private EntityManager entityManager;
	
	//实例化控制器完成后执行该方法实例化JPAQueryFactory
	@Bean
    public JPAQueryFactory initFactory()
    {
        return new JPAQueryFactory(entityManager);
    }
}
