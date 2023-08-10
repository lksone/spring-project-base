package com.lks.netty.other.service.BigFile.protocol;

import lombok.Data;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/8/9 16:28
 */
@Data
public abstract class Packet {

    private Byte type;

    public abstract Byte getCommand();
}
