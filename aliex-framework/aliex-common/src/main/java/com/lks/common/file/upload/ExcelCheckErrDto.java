package com.lks.common.file.upload;

import lombok.Data;


/**
 * excel校验报错对象
 *
 * @author lks
 */
@Data
public class ExcelCheckErrDto<T> {

    private T t;

    private String errMsg;

    public ExcelCheckErrDto() {
    }

    public ExcelCheckErrDto(T t, String errMsg) {
        this.t = t;
        this.errMsg = errMsg;
    }
}
