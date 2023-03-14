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
 * @author liuhaiyang
 * @version V1.0
 * easyExcel工具类
 * @date 2019/10/14 10:13
 */
@Component
public class EasyExcelUtil {

    private static final String EXCEL_SECRET_CODE = "intelligence-password";

    private static final String EXCEL_VERSION = "1.0";

    private EasyExcelUtil() {
    }

    /**
     * 导出2007版Excel
     *
     * @param params 导出参数
     * @throws IOException IO异常
     * @author 刘海洋
     * creat_date: 2019/10/14
     * creat_time: 11:49
     */
    public static void exportExcel2007Format(EasyExcelParams params) throws IOException {
        exportExcel(params);
    }

    /**
     * 导出Excel实现
     *
     * @param params 导出参数
     * @throws IOException IO异常
     * @author 刘海洋
     * creat_date: 2019/10/14
     * creat_time: 11:51
     */
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

    /**
     * 将文件输出到浏览器(导出)
     *
     * @author 刘海洋
     * creat_date: 2019/10/14
     * creat_time: 14:50
     */
    private static void prepareResponds(String fileName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8'zh_cn'" + fileName + ExcelTypeEnum.XLSX.getValue());
    }

    /**
     * 校验导入文件是否是Excel格式
     *
     * @param file 导入文件
     * @author 刘海洋
     * creat_date: 2019/12/9
     * creat_time: 15:45
     */
    public static boolean checkExcelStyle(MultipartFile file) {
        String filename = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        return filename.equals(ExcelTypeEnum.XLSX.getValue());
    }

    /**
     * 检查Excel的密码
     *
     * @param secretCode 导入密码
     * @author 刘海洋
     * creat_date: 2019/12/9
     * creat_time: 15:50
     */
    public static boolean checkExcelPassword(String secretCode) {
        return EXCEL_SECRET_CODE.equals(secretCode);
    }

    /**
     * 检查Excel的密码
     *
     * @param version 导入密码
     * @author 刘海洋
     * creat_date: 2019/12/9
     * creat_time: 15:50
     */
    public static boolean checkExcelVersion(String version) {
        return EXCEL_VERSION.equals(version);
    }
}
