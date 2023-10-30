package com.lks.netty.day006;

import java.io.File;
import java.io.Serializable;

/**
 * @author lks
 * @description 指定的类
 * @e-mail 1056224715@qq.com
 * @date 2023/9/17 22:54
 */
public class FileUploadFile implements Serializable {


    /**
     * 文件类型
     */
    private File file;

    /**
     * 文件名称
     */
    private String fileMd5;

    /**
     * 读取文件开始位置
     */
    private int startPosition;

    /**
     * 读取文件结束位置
     */
    private int endPosition;


    /**
     * 文件中的字节数组
     */
    private byte[] buffer;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
}
