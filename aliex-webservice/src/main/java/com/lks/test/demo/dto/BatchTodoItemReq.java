package com.lks.test.demo.dto;

import java.util.List;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/4/6 18:21
 */
public class BatchTodoItemReq {

    /**
     * 批量信息
     */
    private List<TodoItemReq> data;


    public List<TodoItemReq> getData() {
        return data;
    }

    public void setData(List<TodoItemReq> data) {
        this.data = data;
    }

}
