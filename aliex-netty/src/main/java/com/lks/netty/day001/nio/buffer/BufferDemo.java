package com.lks.netty.day001.nio.buffer;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author lks
 * @description 要想了解Nio就必须了解
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
 * @e-mail 1056224715@qq.com
 * @date 2023/8/10 23:50
 */
@Slf4j
public class BufferDemo {

    public static void log(ByteBuffer buffer){
        log.info("\u001B[34m" +"position:[{}],limit:[{}],remaining:[{}],capacity:[{}]",buffer.position(),buffer.limit(),buffer.remaining(),buffer.capacity()+ "\u001B[0m");
        log.info("\u001B[34m" +"mode:{}",(buffer.isReadOnly()?"读模式":"写模式")+"\u001B[0m");
    }

    public static void test(){
        // 1.分配一个缓冲区,容量设置成10
        ByteBuffer buffer = ByteBuffer.allocate(10);
        log(buffer);
        // 2.put()往缓冲区中添加数据
        buffer.put("name".getBytes(StandardCharsets.UTF_8));
        log(buffer);
        //3.flip()为将缓冲区的界限设置为当前位置,并将当前位置重置为0 可读模式
        buffer.flip();
        log(buffer);
        //4、獲取數據信息
        byte[] buffer1 = new byte[2];
        buffer.get(buffer1);
        System.out.println(new String(buffer1));
        log(buffer);

        //5、clear()清除缓冲区中的数据 并没有真正清除数据,只是让position的位置恢复到初始位置,后续添加数据的时候才会覆盖每个位置的数据
        System.out.println("------------------------------------------------------------------------------------");
       // buffer.clear();
       /* log(buffer);
        byte[] aaa = new byte[5];
        buffer.get(aaa);
        System.out.println(new String(aaa));*/
        log(buffer);
        // 回到标记位置,将posiation的位置回到上次mark的位置
        buffer.reset();
        if (buffer.hasRemaining()) {
            System.out.println(buffer.remaining());
        }
    }

    public static void test2(){
        //直接內存，上面的是內存
        ByteBuffer buffer = ByteBuffer.allocateDirect(10);
        buffer.put("access".getBytes(StandardCharsets.UTF_8));
        log(buffer);
        buffer.flip();
        //4、獲取數據信息
        byte[] buffer1 = new byte[2];
        buffer.get(buffer1);
        System.out.println(new String(buffer1));
        log(buffer);

        //標記位置
        buffer.mark();
        byte[] buffer2 = new byte[4];
        buffer.get(buffer2);
        System.out.println(new String(buffer2));
        log(buffer);
        //回到mark的位置
        buffer.reset();
        log(buffer);
    }
    public static void main(String[] args) {
        //
        test2();
    }
}
