package com.lks.common.file.download;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author liuhaiyang
 * @version V1.0
 * easyExcel参数类
 * @date 2019/10/14 10:15
 */
@Setter
@Getter
@ToString
public class EasyExcelParams {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * sheet名
     */
    private String sheetName;

    /**
     * 是否需要表头
     */
    private Boolean needHead;

    /**
     * 导出数据
     */
    private List data;

    /**
     * 数据模型类型
     */
    private Class dataModelClazz;

    /**
     * 响应
     */
    private HttpServletResponse response;

    /**
     * 单元格样式
     */
    private ExcelStyleConfig styleConfig;

    /**
     * 合并索引数
     */
    private List<MergeCellIndex> mergeCellIndices;

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MergeCellIndex {

        /**
         * 开始行
         */
        private Integer firstRowIndex;

        /**
         * 结束行
         */
        private Integer lastRowIndex;

        /**
         * 开始列
         */
        private Integer firstColumnIndex;

        /**
         * 结束列
         */
        private Integer lastColumnIndex;
    }

    /**
     * 不合并和不锁定构造
     */
    public EasyExcelParams(String fileName, String sheetName, Boolean needHead, List data, Class dataModelClazz, HttpServletResponse response) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.needHead = needHead;
        this.data = data;
        this.dataModelClazz = dataModelClazz;
        this.response = response;
    }

    /**
     * 对于非空字典判空
     */
    public boolean isValid() {
        return ObjectUtils.allNotNull(fileName, data, response, dataModelClazz);
    }

    public void setStyleConfig(ExcelStyleConfig styleConfig) {
        this.styleConfig = styleConfig;
    }

    public void setMergeCellIndices(List<MergeCellIndex> mergeCellIndices) {
        this.mergeCellIndices = mergeCellIndices;
    }
}
