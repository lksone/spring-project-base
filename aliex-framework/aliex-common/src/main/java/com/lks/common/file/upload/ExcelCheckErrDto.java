package com.lks.common.file.upload;

import lombok.Data;

/**
 * @author zhy
 * @title: ExcelCheckErrDto
 * @projectName cec-moutai-bd-display
 * @description: excel数据导入错误结果（单条）
 * @date 2019/12/2318:23
 */
@Data
public class ExcelCheckErrDto<T> {

    private T t;

    private String errMsg;

    public ExcelCheckErrDto(){}

    public ExcelCheckErrDto(T t, String errMsg){
        this.t = t;
        this.errMsg = errMsg;
    }
}
