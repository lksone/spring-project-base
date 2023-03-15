package com.lks.common.file.download;


import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * @author lks
 */
@Slf4j
public class ExcelStyleConfig implements CellWriteHandler {

    private List<Integer> columnList;

    private CellStyle cellStyle;

    /**
     * 隐藏索引数
     */
    private List<Integer> hiddenIndices;

    public ExcelStyleConfig(List<Integer> columnList, List<Integer> hiddenIndices) {
        this.columnList = columnList;
        this.hiddenIndices = hiddenIndices;
    }

    public ExcelStyleConfig(List<Integer> columnList) {
        this.columnList = columnList;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        cellStyle = writeSheetHolder.getSheet().getWorkbook().createCellStyle();
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        // 下边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        // 左边框
        cellStyle.setBorderLeft(BorderStyle.THIN);
        // 上边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        // 右边框
        cellStyle.setBorderRight(BorderStyle.THIN);
        // 水平对齐方式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setLocked(false);
        if (!CollectionUtils.isEmpty(hiddenIndices) && hiddenIndices.contains(cell.getColumnIndex())) {
            // 设置隐藏列
            writeSheetHolder.getSheet().setColumnHidden(cell.getColumnIndex(), true);
        }
        if (!CollectionUtils.isEmpty(columnList) && columnList.contains(cell.getColumnIndex())) {
            // 设置表单保护密码
            writeSheetHolder.getSheet().protectSheet("123456789a");
            // 设置锁定单元格
            cellStyle.setLocked(true);
        }
        // 填充单元格样式
        cell.setCellStyle(cellStyle);
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }
}
