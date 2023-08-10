package com.lks.netty.other.service.BigFile.protocol;

import com.lks.netty.other.service.BigFile.protocol.command.Command;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * @author lks
 * @description 文件传输模型
 * @e-mail 1056224715@qq.com
 * @date 2023/8/9 16:29
 */
@Data
public class FilePacket extends Packet implements Serializable {

    /**
     * 文件
     */
    private File file;

    /**
     * 文件Md5
     */
    private String file_md5;

    /**
     * 开始pos
     */
    private int startPos;

    /**
     * 缓存存储的数据信息
     */
    private byte[] bytes;

    /**
     * 结束节点Index
     */
    private int endPos;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFile_md5() {
        return file_md5;
    }

    public void setFile_md5(String file_md5) {
        this.file_md5 = file_md5;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    @Override
    public Byte getCommand() {
        return Command.FILE;
    }
}
