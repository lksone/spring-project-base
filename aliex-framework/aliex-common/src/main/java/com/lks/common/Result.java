package com.lks.common;

import lombok.Data;

/**
 * @author lks
 */
@Data
public class Result <T>{

    public static final String SUCCESS_MSG = "操作成功！";

    private boolean result;

    private String code;

    private String message;

    private String token;

    private Long count;

    private T data;
}
