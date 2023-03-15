package com.lks.common.result;

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
