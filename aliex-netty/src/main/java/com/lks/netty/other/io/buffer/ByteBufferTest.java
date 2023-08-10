package com.lks.netty.other.io.buffer;

import com.lks.netty.other.io.utils.ByteBufferUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author lks
 * @description byteBuffer的使用和调用
 * @e-mail 1056224715@qq.com
 * @date 2023/6/28 21:59
 */
public class ByteBufferTest {

    /**
     * reset的方法使用
     */
    private static void reset() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 向buffer中写入1个字节的数据
        buffer.put(new byte[]{98, 99, 100, 101});
        // 使用工具类，查看buffer状态
        ByteBufferUtil.debugAll(buffer);
        buffer.flip();
        ByteBufferUtil.debugAll(buffer);
        System.out.println(buffer.get());
        buffer.mark();
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        ByteBufferUtil.debugAll(buffer);

        //将数据回到mark标记的位置
        buffer.reset();
        ByteBufferUtil.debugAll(buffer);
    }

    /**
     * 字符串转换为ByteBuffer中互换，将string 转换为ByteBuffer
     * <p>
     * 编码：字符串调用getByte方法获得byte数组，将byte数组放入ByteBuffer中
     * <p>
     * 解码：先调用ByteBuffer的flip方法，然后通过StandardCharsets的decoder方法解码
     */
    private static void strToBuffer() {
        // 准备两个字符串
        String str1 = "hello";
        String str2 = "";


        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        // 通过字符串的getByte方法获得字节数组，放入缓冲区中
        buffer1.put(str1.getBytes());
        ByteBufferUtil.debugAll(buffer1);
        buffer1.flip();
        // 通过StandardCharsets解码，获得CharBuffer，再通过toString获得字符串
        str2 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(str2);
        ByteBufferUtil.debugAll(buffer1);
    }


    /** 
     * 通过StandardCharsets的encode方法获得ByteBuffer，此时获得的ByteBuffer为读模式，无需通过flip切换模式
     */
    private static void strToBuffer2() {
        String str1 = "1231231";
        String str2 = "";

        //将数据str转换为
        ByteBuffer encode = StandardCharsets.UTF_8.encode(str1);
        ByteBufferUtil.debugAll(encode);
    }

    public static void main(String[] args) {
        strToBuffer2();
    }
}
