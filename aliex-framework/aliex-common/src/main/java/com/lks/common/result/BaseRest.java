package com.lks.common.result;

public class BaseRest {


    public Result addSucResult() {
        return this.addResult(true, ResultCodeEnum.SUCCESS.getValue(), ResultCodeEnum.SUCCESS.getLabel(), (Object)null);
    }

    public Result addErrResult(String code, String message) {
        return this.addResult(true, code, message, (Object)null);
    }

    public <T> Result addResult(boolean result, String code, String message, T data) {
        Result<T> rs = new Result();
        rs.setResult(result);
        rs.setCode(code);
        rs.setMessage(message);
        rs.setData(data);
        return rs;
    }
}