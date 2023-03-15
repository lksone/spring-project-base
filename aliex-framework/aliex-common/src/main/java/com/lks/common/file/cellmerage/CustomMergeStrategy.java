package com.lks.common.file.cellmerage;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 单元格合并(自定义数据信息合并)
 *
 * @author lks
 * @e-mail 1056224715@qq.com
 * @date 2023/3/15 22:57
 */
public class CustomMergeStrategy extends AbstractMergeStrategy {

    /**
     * 分组，每几行合并一次
     */
    private List<Integer> exportFieldGroupCountList;

    /**
     * 目标合并列Index
     */
    private Integer targetColumnIndex;

    /**
     * 需要开始合并的开始行数
     */
    private Integer rowIndex;

    public CustomMergeStrategy(List<Integer> exportFieldGroupCountList, Integer targetColumnIndex) {
        this.exportFieldGroupCountList = exportFieldGroupCountList;
        this.targetColumnIndex = targetColumnIndex;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        if (null == rowIndex) {
            rowIndex = cell.getRowIndex();
        }
        // 仅从首行以及目标列的单元格开始合并，忽略其他
        if (cell.getRowIndex() == rowIndex && cell.getColumnIndex() == targetColumnIndex) {
            mergeGroupColumn(sheet);
        }
    }

    private void mergeGroupColumn(Sheet sheet) {
        int rowCount = rowIndex;
        for (Integer count : exportFieldGroupCountList) {
            if(count == 1) {
                rowCount += count;
                continue ;
            }
            // 合并单元格
            CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCount, rowCount + count - 1, targetColumnIndex, targetColumnIndex);
            sheet.addMergedRegionUnsafe(cellRangeAddress);
            rowCount += count;
        }
    }

    /**
     * 该方法将目标列根据值是否相同连续可合并，存储可合并的行数
     *
     * @param exportDataList
     * @return
     */
    private List<Integer> getGroupCountList(List<String> exportDataList){
        if (CollectionUtils.isEmpty(exportDataList)) {
            return new ArrayList<>();
        }

        List<Integer> groupCountList = new ArrayList<>();
        int count = 1;
        for (int i = 1; i < exportDataList.size(); i++) {
            if (exportDataList.get(i).equals(exportDataList.get(i - 1))) {
                count++;
            } else {
                groupCountList.add(count);
                count = 1;
            }
        }
        // 处理完最后一条后
        groupCountList.add(count);
        return groupCountList;
    }
}
