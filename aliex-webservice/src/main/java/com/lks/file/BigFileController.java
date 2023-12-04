package com.lks.file;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author lks
 * @description 大文件上传，分段上传问题
 * @e-mail 1056224715@qq.com
 * @date 2023/12/4 14:35
 */
@CrossOrigin
@Controller
@RequestMapping("/file/upload")
public class BigFileController {

    @PostMapping("/part")
    @ResponseBody
    public Result bigFile(HttpServletRequest request, String guid, Integer chunk, MultipartFile file, Integer chunks) {
        try {
            String projectUrl = System.getProperty("user.dir").replaceAll("\\\\", "/");
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                if (chunk == null) {
                    chunk = 0;
                }
                // 临时目录用来存放所有分片文件
                String tempFileDir = projectUrl + "/upload/" + guid;
                File parentFileDir = new File(tempFileDir);
                if (!parentFileDir.exists()) {
                    parentFileDir.mkdirs();
                }
                // 存放临时文件
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(parentFileDir, guid + "_chunk" + chunk + ".part"));
            }
        } catch (IOException e) {
            Result.failMessage(400, "上传失败");
        }
        return Result.success(200, "上传成功");
    }


    /**
     * 合并文件
     *
     * @param guid     文件标准
     * @param fileName 文件名称
     * @return
     */
    @RequestMapping("merge")
    @ResponseBody
    public Result mergeFile(String guid, String fileName) {
        try {
            // 得到 destTempFile 就是最终的文件
            String projectUrl = System.getProperty("user.dir").replaceAll("\\\\", "/");
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String finaFileName = projectUrl + "/upload/" + UUID.randomUUID() + suffixName;
            String temFileDir = projectUrl + "/upload/" + guid;
            File finalFile = new File(finaFileName);
            File allfile = new File(temFileDir);
            for (File file : Objects.requireNonNull(allfile.listFiles())) {
                FileOutputStream fileOutputStream = new FileOutputStream(finalFile, true);
                FileUtils.copyFile(file, fileOutputStream);
                fileOutputStream.close();
            }
        } catch (IOException e) {
            return Result.failMessage(400, "合并失败");
        }
        return Result.successMessage(200, "合并成功");

    }


}
