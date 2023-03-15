package com.lks.common;

import lombok.Data;

/**
 * @author lks
 */
@Data
public class Result <T>{

    private boolean result;

    private String code;

    private String message;

    private String token;

    private Long count;

    private T data;
}
