package com.lks.common.file.download;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.Validate;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


/**
 * @author lks
 */
@Component
public class EasyExcelUtil {

    private static final String EXCEL_SECRET_CODE = "intelligence-password";

    private static final String EXCEL_VERSION = "1.0";

    private EasyExcelUtil() {
    }


    public static void exportExcel2007Format(EasyExcelParams params) throws IOException {
        exportExcel(params);
    }


    private static void exportExcel(EasyExcelParams params) throws IOException {
        Validate.isTrue(params.isValid(), "参数错误!");
        prepareResponds(params.getFileName(), params.getResponse());
        ServletOutputStream outputStream = params.getResponse().getOutputStream();
        ExcelWriterBuilder builder = new ExcelWriterBuilder();
        builder.sheet(params.getSheetName());
        builder.head(params.getDataModelClazz());
        builder.file(outputStream);
        builder.excelType(ExcelTypeEnum.XLSX);
        builder.needHead(true);
        builder.registerWriteHandler(params.getStyleConfig());
        WriteSheet sheet = new WriteSheet();
        sheet.setSheetName(params.getSheetName());
        sheet.setSheetNo(1);
        ExcelWriter writer = builder.build();
        writer.write(params.getData(), sheet);
        if (!CollectionUtils.isEmpty(params.getMergeCellIndices())) {
            for (EasyExcelParams.MergeCellIndex mergeCellIndex : params.getMergeCellIndices()) {
                writer.merge(mergeCellIndex.getFirstRowIndex(), mergeCellIndex.getLastRowIndex(), mergeCellIndex.getFirstColumnIndex(), mergeCellIndex.getLastColumnIndex());
            }
        }
        writer.finish();
        outputStream.close();
    }


    private static void prepareResponds(String fileName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8'zh_cn'" + fileName + ExcelTypeEnum.XLSX.getValue());
    }


    public static boolean checkExcelStyle(MultipartFile file) {
        String filename = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        return filename.equals(ExcelTypeEnum.XLSX.getValue());
    }


    public static boolean checkExcelPassword(String secretCode) {
        return EXCEL_SECRET_CODE.equals(secretCode);
    }


    public static boolean checkExcelVersion(String version) {
        return EXCEL_VERSION.equals(version);
    }
}
