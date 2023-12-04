package com.lks.watermark.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常枚举
 *
 * @author Rong.Jia
 * @date 2021/08/29
 */
public enum ExceptionEnum {

    // 必须是xxx类型文件
    THE_FILE_MUST_BE_OF_TYPE_XXX("The file must be of type %s"),


    ;

    ExceptionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;


}
