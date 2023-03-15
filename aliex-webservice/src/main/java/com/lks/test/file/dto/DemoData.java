package com.lks.test.file.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.lks.common.file.upload.ExcelPatternMsg;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/3/15 23:20
 */
@Data
public class DemoData implements Serializable {


    /**
     * 名称
     */
    @ExcelProperty(index = 0,value = "名称")
    @ColumnWidth(30)
    @Length(max = 10)
    private String name;

    /**
     * 性别
     */
    @ExcelProperty(index = 1,value = "性别")
    @ColumnWidth(30)
    private Date date;

    /**
     * 年龄
     */
    @ExcelProperty(index = 2,value = "年龄")
    @ColumnWidth(30)
    private Double age;

}
