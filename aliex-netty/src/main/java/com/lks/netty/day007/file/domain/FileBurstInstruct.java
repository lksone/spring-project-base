package com.lks.netty.day007.file.domain;

/**
 * @author lks
 * @description 文件分片数据
 * @e-mail 1056224715@qq.com
 * @date 2023/9/20 22:30
 */
public class FileBurstInstruct {

    /**
     * Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
     */
    private Integer status;

    /**
     * 客户端文件URL
     */
    private String clientFileUrl;

    /**
     * 读取位置
     */
    private Integer readPosition;

    public FileBurstInstruct(Integer status) {
        this.status = status;
    }

    public FileBurstInstruct() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getClientFileUrl() {
        return clientFileUrl;
    }

    public void setClientFileUrl(String clientFileUrl) {
        this.clientFileUrl = clientFileUrl;
    }

    public Integer getReadPosition() {
        return readPosition;
    }

    public void setReadPosition(Integer readPosition) {
        this.readPosition = readPosition;
    }
}
