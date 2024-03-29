package com.lks.netty.day007.file.util;

import com.lks.netty.day007.file.domain.Constants;
import com.lks.netty.day007.file.domain.FileBurstData;
import com.lks.netty.day007.file.domain.FileBurstInstruct;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/9/18 23:48
 */
public class FileUtil {

    /**
     * 讀取文件內容信息
     * @param fileUrl
     * @param readPosition
     * @return
     */
    public static FileBurstData readFile(String fileUrl, Integer readPosition) throws IOException {
        File file = new File(fileUrl);
        //r: 只读模式 rw:读写模式
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        randomAccessFile.seek(readPosition);
        byte[] bytes = new byte[1024];
        int readSize = randomAccessFile.read(bytes);
        if (readSize <= 0) {
            randomAccessFile.close();
            //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
            return new FileBurstData(Constants.FileStatus.COMPLETE);
        }
        FileBurstData fileInfo = new FileBurstData();
        fileInfo.setFileUrl(fileUrl);
        fileInfo.setFileName(file.getName());
        fileInfo.setBeginPos(readPosition);
        fileInfo.setEndPos(readPosition + readSize);
        //不足1024需要拷贝去掉空字节
        if (readSize < 1024) {
            byte[] copy = new byte[readSize];
            System.arraycopy(bytes, 0, copy, 0, readSize);
            fileInfo.setBytes(copy);
            fileInfo.setStatus(Constants.FileStatus.END);
        } else {
            fileInfo.setBytes(bytes);
            fileInfo.setStatus(Constants.FileStatus.CENTER);
        }
        randomAccessFile.close();
        return fileInfo;
    }


    public static FileBurstInstruct writeFile(String baseUrl, FileBurstData fileBurstData) throws IOException {

        if (Constants.FileStatus.COMPLETE.equals(fileBurstData.getStatus())) {
            //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
            return new FileBurstInstruct(Constants.FileStatus.COMPLETE);
        }

        File file = new File(baseUrl + "/" + fileBurstData.getFileName());
        //r: 只读模式 rw:读写模式
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        //移动文件记录指针的位置,
        randomAccessFile.seek(fileBurstData.getBeginPos());
        //调用了seek（start）方法，是指把文件的记录指针定位到start字节的位置。也就是说程序将从start字节开始写数据
        randomAccessFile.write(fileBurstData.getBytes());
        randomAccessFile.close();

        if (Constants.FileStatus.END == fileBurstData.getStatus()) {
            //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
            return new FileBurstInstruct(Constants.FileStatus.COMPLETE);
        }

        //文件分片传输指令
        FileBurstInstruct fileBurstInstruct = new FileBurstInstruct();

        //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
        fileBurstInstruct.setStatus(Constants.FileStatus.CENTER);
        fileBurstInstruct.setClientFileUrl(fileBurstData.getFileUrl());      //客户端文件URL
        fileBurstInstruct.setReadPosition(fileBurstData.getEndPos() + 1);    //读取位置

        return fileBurstInstruct;
    }
}
