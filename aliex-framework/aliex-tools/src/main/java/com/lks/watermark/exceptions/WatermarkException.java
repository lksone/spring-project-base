package com.lks.watermark.exceptions;

/**
 * @author lks
 * @description 水印异常
 * @e-mail 1056224715@qq.com
 * @date 2023/12/2 14:39
 */
public class WatermarkException extends RuntimeException {

    public WatermarkException(String msg) {
        super(msg);
    }

    public WatermarkException(String msg, Exception e) {
        super(msg, e);
    }

    public WatermarkException(String msg, Throwable t) {
        super(msg, t);
    }
}