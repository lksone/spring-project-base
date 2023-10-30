package com.lks.netty.day007.file.domain;

/**
 * @author lks
 * @description 文件描述信息
 * @e-mail 1056224715@qq.com
 * @date 2023/9/20 23:06
 */
public class FileDescInfo {

    private String fileUrl;
    private String fileName;
    private Long fileSize;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
