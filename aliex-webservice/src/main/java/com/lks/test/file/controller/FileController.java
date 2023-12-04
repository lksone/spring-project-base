package com.lks.test.file.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson.JSON;
import com.lks.common.result.BaseRest;
import com.lks.common.result.Result;
import com.lks.common.file.upload.EasyExcelListener;
import com.lks.common.file.upload.EasyExcelUtils;
import com.lks.common.file.upload.ExcelCheckErrDto;
import com.lks.test.file.domain.User;
import com.lks.test.file.dto.UserExcelDto;
import com.lks.test.file.dto.UserExcelErrDto;
import com.lks.test.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author lks
 */
@RestController
@RequestMapping("user")
public class FileController extends BaseRest {

    /**
     * 文件内容报错写入到指定文件夹中
     */
    private String errorFilePath = "d:/error/";


    @Autowired
    private FileService userService;

    /**
     * 文件模板下载数据信息
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setName("张三");
        user1.setAge(10);
        user1.setBirthday(new Date());
        user1.setSex("男");
        userList.add(user1);
        List<UserExcelDto> userExcelDtos = JSON.parseArray(JSON.toJSONString(userList), UserExcelDto.class);
        EasyExcelUtils.webWriteExcel(response, userExcelDtos, UserExcelDto.class, "用户基本信息");
    }

    /**
     * 文件导入数据信息，报错信息直接返回数据（方案1：直接返回报错信息，对比错误信息）
     *
     * @param response
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/importExcel")
    @ResponseBody
    public Result importExcel(HttpServletResponse response, @RequestParam MultipartFile file) throws IOException {
        EasyExcelListener easyExcelListener = new EasyExcelListener(userService, UserExcelDto.class);
        EasyExcelFactory.read(file.getInputStream(), UserExcelDto.class, easyExcelListener).sheet().doRead();
        List<ExcelCheckErrDto<UserExcelDto>> errList = easyExcelListener.getErrList();
        //如果包含错误信息就导出错误信息
        if (!errList.isEmpty()) {
            List<UserExcelErrDto> excelErrDtos = errList.stream().map(excelCheckErrDto -> {
                UserExcelErrDto userExcelErrDto = JSON.parseObject(JSON.toJSONString(excelCheckErrDto.getT()), UserExcelErrDto.class);
                userExcelErrDto.setErrMsg(excelCheckErrDto.getErrMsg());
                return userExcelErrDto;
            }).collect(Collectors.toList());
            EasyExcelUtils.webWriteExcel(response, excelErrDtos, UserExcelErrDto.class, "用户导入错误信息");
        }
        return addSucResult();
    }


    /**
     * 报错信息直接存储到指定文件夹进行数据，需要的时候进行下载
     *
     * @param response
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/importExcelDownloadLocal")
    @ResponseBody
    public Result importExcelDownloadLocal(HttpServletResponse response, @RequestParam MultipartFile file) throws IOException {
        EasyExcelListener easyExcelListener = new EasyExcelListener(userService, UserExcelDto.class);
        EasyExcelFactory.read(file.getInputStream(), UserExcelDto.class, easyExcelListener).sheet().doRead();
        List<ExcelCheckErrDto<UserExcelDto>> errList = easyExcelListener.getErrList();
        //如果包含错误信息就导出错误信息
        if (!errList.isEmpty()) {
            List<UserExcelErrDto> excelErrDtos = errList.stream().map(excelCheckErrDto -> {
                UserExcelErrDto userExcelErrDto = JSON.parseObject(JSON.toJSONString(excelCheckErrDto.getT()), UserExcelErrDto.class);
                userExcelErrDto.setErrMsg(excelCheckErrDto.getErrMsg());
                return userExcelErrDto;
            }).collect(Collectors.toList());

            //创建文件和文件夹数据信息，将错误数据写入到文件中

            String filename = "123.xlsx";
            String fileAllPath = errorFilePath + filename;
            File file1 = new File(fileAllPath);
            File filePath = new File(errorFilePath);
            if(!filePath.exists()){
                filePath.mkdirs();
            }
            if(!file1.exists()){
                file1.createNewFile();
            }
            //持久化文件指定的路径 todo 导入数据列表，错误信息和成功信息，如果错误数据可以下载查看，自己的数据只能自己看到，下载下来查看错误数据原因
            EasyExcel.write(file1,UserExcelErrDto.class).sheet("用户导入错误信息").doWrite(excelErrDtos);
        }
        return addSucResult();
    }
}
