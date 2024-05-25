package com.lks.tools;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author lks
 * @description 事件工具类
 * @e-mail 1056224715@qq.com
 * @date 2024/5/24 23:29
 */
public class TransactionUtil {

    /**
     * 处理之前的事务之后，是活跃状态，注入新的事物
     *
     * @param doTransactionCompletion
     */
    public static void doAfterTransaction(DoTransactionCompletion doTransactionCompletion) {
        //判断上下文中是否有事务被激活
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            //注入这个事务
            TransactionSynchronizationManager.registerSynchronization(doTransactionCompletion);
        }
    }

    @Transactional
    public void dosomeThing() {
        //事物之前
        TransactionUtil.doAfterTransaction(new DoTransactionCompletion(() -> {
            //处理发送MQ,异步线程事项，主要是控制异步导致的线程失败问题
        }));
        //事物结束之后
    }
}
