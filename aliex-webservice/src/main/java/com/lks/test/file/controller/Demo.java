package com.lks.test.file.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.common.collect.Lists;
import com.lks.test.file.dto.DemoData;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/3/15 23:20
 */
@Slf4j
public class Demo {

    /**
     *  不合并直接写入到Excel数据库中
     */
    public static void writeExcel() {
        // 写excel的路径，当前项目路径下
        String fileName = getPath();
        log.info("文件名{}",fileName);
        // 构建ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(fileName).excelType(ExcelTypeEnum.XLSX).build();

        // 构建sheet
        WriteSheet writeSheet = EasyExcel.writerSheet("模板1").head(DemoData.class).build();
        // 写sheet
        excelWriter.write(data1(), writeSheet);
        excelWriter.finish();
    }

    /**
     * 直接合併數據
     */
    public static void writeExcel2() {
        String fileName = getPath();
        ExcelWriter excelWriter = EasyExcel.write(fileName).excelType(ExcelTypeEnum.XLSX).build();

        List<DemoData> demoDataList = data1();
        // 写sheet的时候注册相应的自定义合并单元格策略
//        WriteSheet writeSheet = EasyExcel.writerSheet("模板1").head(DemoData.class)
//                .registerWriteHandler(new CustomMergeStrategy(demoDataList.stream().map(DemoData::getName).collect(Collectors.toList()), 0))
//                .build();
//        excelWriter.write(demoDataList, writeSheet);
//        excelWriter.finish();
    }

    /**
     * 获取文件前端数据地址
     *
     * @return
     */
    private static String getPath() {
        return System.getProperty("user.dir") + "/" + System.currentTimeMillis() + ".xlsx";
    }

    private static List<DemoData> data1() {
        List<DemoData> list = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            DemoData data = new DemoData();
            data.setName("字符串" + 1);
            data.setDate(new Date());
            data.setAge(0.56);
            list.add(data);
        }
        for (int i = 0; i < 3; i++) {
            DemoData data = new DemoData();
            data.setName("字符串" + 2);
            data.setDate(new Date());
            data.setAge(0.56);
            list.add(data);
        }
        for (int i = 0; i < 4; i++) {
            DemoData data = new DemoData();
            data.setName("字符串" + 3);
            data.setDate(new Date());
            data.setAge(0.57);
            list.add(data);
        }
        return list;
    }

    public static void main(String[] args) {
        //不合并数据信息
       // writeExcel();
        List<DemoData> demoDataList = data1();
        demoDataList.stream().map(DemoData::getName).collect(Collectors.toList());
    }
}
