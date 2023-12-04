package com.lks.file;

/**
 * @author lks
 * @description 返回数据的JSON格式
 * @e-mail 1056224715@qq.com
 * @date 2023/12/4 14:40
 */
public class Result<T> {

    private int code;
    private String message;
    private T data;

    public static <T> Result<T> fail(Integer code, T data) {
        Result<T> ret = new Result<T>();
        ret.setCode(code);
        ret.setData(data);
        return ret;
    }

    public static <T> Result<T> failMessage(Integer code, String msg) {
        Result<T> ret = new Result<T>();
        ret.setCode(code);
        ret.setMessage(msg);
        return ret;
    }

    public static <T> Result<T> successMessage(Integer code, String msg) {
        Result<T> ret = new Result<T>();
        ret.setCode(code);
        ret.setMessage(msg);
        return ret;
    }

    public static <T> Result<T> success(Integer code, T data) {
        Result<T> ret = new Result<T>();
        ret.setCode(code);
        ret.setData(data);
        return ret;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
