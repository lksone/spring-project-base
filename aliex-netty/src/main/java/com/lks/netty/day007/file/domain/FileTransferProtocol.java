package com.lks.netty.day007.file.domain;

/**
 * @author lks
 * @description 文件传输协议
 * @e-mail 1056224715@qq.com
 * @date 2023/9/20 22:28
 */
public class FileTransferProtocol {

    /**
     *  eg:
     *  0请求传输文件、
     *  1文件传输指令、
     *  2文件传输数据
     */
    private Integer transferType;

    /**
     * 数据对象；(0)FileDescInfo、(1)FileBurstInstruct、(2)FileBurstData
     */
    private Object transferObj;


    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public Object getTransferObj() {
        return transferObj;
    }

    public void setTransferObj(Object transferObj) {
        this.transferObj = transferObj;
    }
}
