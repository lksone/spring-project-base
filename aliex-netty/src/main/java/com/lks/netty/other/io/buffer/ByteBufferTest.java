package com.lks.netty.other.io.buffer;

import com.lks.netty.other.io.utils.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author lks
 * @description byteBuffer的使用和调用
 * @e-mail 1056224715@qq.com
 * @date 2023/6/28 21:59
 *
 * <p>
 * Buffer clear() //清空缓冲区并返回对缓冲区的引用(缓冲区中的数据依然存在，但是处于被遗忘状态)
 * Buffer flip() //为将缓冲区的界限设置为当前位置,并将当前位置重置为0
 * int capacity() //返回Buffer的capacity大小
 * boolean hasRemaining() //判断缓冲区中是否还有元素
 * int limit() //返回Buffer的界限(limit)的位置
 * Buffer limit(int n) //将设置缓冲区界限为n,并返回一个具有新limit的缓冲区对象
 * Buffer mark() //对缓冲区设置标记
 * int position() //返回缓冲区的当前位置position
 * Buffer position(int n) //将设置缓冲区的当前位置为n,并返回修改后的Buffer对象
 * int remaining() //返回position和limit之间的元素个数
 * Buffer reset() //将位置position转到以前设置的mark所在的位置
 * Buffer rewind() //将位置设为0,取消设置的mark
 * </p>
 */
@Slf4j
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
        System.out.println("                                                                ");
        System.out.println("                                                                ");
        System.out.println("                                                                ");
        //直接将post转换为读模式，并且position为0
        buffer.flip();
        ByteBufferUtil.debugAll(buffer);
        System.out.println("                                                                ");
        System.out.println("                                                                ");
        System.out.println("                                                                ");
        System.out.println(buffer.get());
        ByteBufferUtil.debugAll(buffer);
        buffer.mark();
        ByteBufferUtil.debugAll(buffer);
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        ByteBufferUtil.debugAll(buffer);
        System.out.println("                                                                ");
        System.out.println("                                                                ");
        System.out.println("                                                                ");

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

    private static void learnTest() {
        // 1.分配一个缓冲区,容量设置成10
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //log.info("\u001B[34m"+"position:{},limit:{}.capacity:{}",buffer.position(),buffer.limit(),buffer.capacity()+ "\u001B[0m");
        System.out.println("--------------------");
    }

    public static void main(String[] args) {
        learnTest();
        /*strToBuffer();
        strToBuffer2();*/
    }
}
