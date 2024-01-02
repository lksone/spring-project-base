package com.lks.thread.callback;

/**
 * @author lks
 * @description 工作单元
 * @e-mail 1056224715@qq.com
 * @date 2023/12/28 11:01
 */
public interface Worker {

    /**
     * 耗时的操作，网络请求/IO等
     *
     * @param obj
     * @return
     */
    String action(String obj);
}
