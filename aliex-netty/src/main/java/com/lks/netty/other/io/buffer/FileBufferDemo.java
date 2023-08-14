package com.lks.netty.other.io.buffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * buffer的使用
 * @author lks
 * @description Buffer的數據演示
 * @e-mail 1056224715@qq.com
 * @date 2023/6/27 23:30
 */
public class FileBufferDemo {


    public static void main(String[] args) {
        demo2();
        demo3();
    }

    private static void demo1() {
        try (FileChannel fileChannel = new FileInputStream("D:\\workspace-soft\\spring-project-base\\aliex-netty\\target\\classes\\a.txt").getChannel();){
            ByteBuffer buffer = ByteBuffer.allocate(10);

            fileChannel.read(buffer);
            //切换模式，讀模式
            buffer.flip();
            while (buffer.hasRemaining()){
                System.out.println((char) buffer.get());
            }
            buffer.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取所有的数据信息
     */
    private static void demo2() {
        System.out.println("這裡是clear的數據讀取長度-------------------------------");
        try (FileChannel fileChannel = new FileInputStream("D:\\workspace-soft\\spring-project-base\\aliex-netty\\target\\classes\\a.txt").getChannel();){
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true){
                int read = fileChannel.read(buffer);
                System.out.println("長度"+read);
                if(read==-1){
                    break;
                }
                //切換讀模式
                buffer.flip();

                while (buffer.hasRemaining()){
                    System.out.println((char) buffer.get());
                }
                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * buffer.compact()和clear的区别
     *
     *
     */
    private static void demo3() {
        System.out.println("這裡是compact的數據讀取長度-------------------------------");
        try (FileChannel fileChannel = new FileInputStream("D:\\workspace-soft\\spring-project-base\\aliex-netty\\target\\classes\\a.txt").getChannel();){
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true){
                int read = fileChannel.read(buffer);
                System.out.println("長度"+read);
                if(read==-1){
                    break;
                }
                //切換讀模式
                buffer.flip();

                for (int i = 0; i < 8; i++) {
                    System.out.println((char) buffer.get());
                }
                buffer.compact();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
