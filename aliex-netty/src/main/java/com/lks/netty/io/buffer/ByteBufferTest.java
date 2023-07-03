package com.lks.netty.io.buffer;

import com.lks.netty.io.utils.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * @author lks
 * @description byteBuffer的使用和调用
 * @e-mail 1056224715@qq.com
 * @date 2023/6/28 21:59
 */
public class ByteBufferTest {


    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 向buffer中写入1个字节的数据
        buffer.put((byte)97);
        // 使用工具类，查看buffer状态
        ByteBufferUtil.debugAll(buffer);
    }
}
