package com.lks.common.file.upload;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/**
 * Excel导入和导出，将文件写入到流数据中
 *
 * @author lks
 */
public class EasyExcelUtils {

    private EasyExcelUtils() {
    }


    @SuppressWarnings("rawtypes")
    public static void webWriteExcel(HttpServletResponse response, List objects, Class clazz, String fileName) throws IOException {
        String sheetName = fileName;
        webWriteExcel(response, objects, clazz, fileName, sheetName);
    }


    @SuppressWarnings("rawtypes")
    public static void webWriteExcel(HttpServletResponse response, List objects, Class clazz, String fileName, String sheetName) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //设置文件下载乱码的问题
        response.setHeader("Content-disposition", "attachment;filename*=utf-8'zh_cn'" + fileName + ".xlsx");
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为白
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            EasyExcelFactory.write(outputStream, clazz).registerWriteHandler(horizontalCellStyleStrategy).sheet(sheetName).doWrite(objects);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }
    }
}
