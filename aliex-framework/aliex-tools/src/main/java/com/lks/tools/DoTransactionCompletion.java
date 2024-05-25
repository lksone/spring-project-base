package com.lks.tools;

import org.springframework.transaction.support.TransactionSynchronization;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2024/5/24 23:31
 */
public class DoTransactionCompletion implements TransactionSynchronization {

    private Runnable runnable;


    public DoTransactionCompletion(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * 异步线程通过线程去执行
     *
     * @param status completion status according to the {@code STATUS_*} constants
     */
    @Override
    public void afterCompletion(int status) {
        if (status == TransactionSynchronization.STATUS_COMMITTED) {
            runnable.run();
        }
    }
}
