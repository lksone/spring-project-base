package com.lks.thread.callback;

import lombok.Data;

/**
 * @author lks
 * @description 包装类
 * @e-mail 1056224715@qq.com
 * @date 2023/12/28 11:02
 */
@Data
public class Wrapper {

    private String param;
    private Worker worker;
    private Listener listener;


    /**
     * 添加回调器
     *
     * @param listener
     */
    public void addListener(Listener listener) {
        this.listener = listener;
    }
}
