package com.lks.test.file.controller;


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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Administrator
 */
@RestController
@RequestMapping("user")
public class FileController extends BaseRest {


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
     * 文件导入测试
     *
     * @param response
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/importExcel")
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
}
