package com.lks.netty.day001.nio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author lks
 * @description fileChannel的例子
 * @e-mail 1056224715@qq.com
 * @date 2023/8/11 15:01
 */
@Slf4j
public class FileChannelDemo {

    /**
     *  1）本地文件写数据
     * @param filePath
     */
    public static void write(String filePath){
        try {
            //1.字节输出流通向目标文件
            FileOutputStream fos = new FileOutputStream(filePath);
            //2、得到字节输出流对应的通道Channel
            FileChannel channel = fos.getChannel();
            //3、分配缓冲區
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            buffer.put("fsdafasdfsadfa".getBytes(StandardCharsets.UTF_8));
            //4、把缓冲区切换成写出模式
            buffer.flip();
            channel.write(buffer);
            channel.close();
            System.out.println("写数据到文件中!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 讀數據信息
     * @param filePath
     */
    public static void read(String filePath){
        try {
            //1.字节输出流通向目标文件
            FileInputStream fis = new FileInputStream (filePath);
            //2、得到字节输出流对应的通道Channel
            FileChannel channel = fis.getChannel();
            //3、分配缓冲區
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //4、把缓冲区切换成写出模式
            channel.read(buffer);
            buffer.flip();
            String rs = new String(buffer.array(), 0, buffer.remaining());
            System.out.println(rs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Buffer完成文件复制
     * @param sourcePath
     * @param targetPath
     */
    public static void copy(String sourcePath,String targetPath)  {
        try {
            File file = new File(targetPath);
            if(file.exists()){
                file.createNewFile();
            }
            //1.字节输出流通向目标文件
            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileChannel fisChannel = null;
            FileChannel fosChannel = null;
            try {
                fis = new FileInputStream(sourcePath);
                //2、得到字节输出流对应的通道Channel
                fos = new FileOutputStream(targetPath);
                fisChannel = fis.getChannel();
                fosChannel = fos.getChannel();
                //3、分配缓冲區
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (true){
                    //清空緩存區
                    buffer.clear();
                    int read = fisChannel.read(buffer);
                    if(read==-1){
                        break;
                    }
                    // 已经读取了数据,把缓冲区的模式切换成可读模式
                    buffer.flip();
                    // 把数据写出
                    fosChannel.write(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                fisChannel.close();
                fosChannel.close();
                fis.close();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("复制完成!");
        }
    }

    /**
     * 分散读取（Scatter）：是指把Channel通道的数据读入到多个缓冲区中去
     *
     * 聚集写入（Gather）：是指将多个Buffer中的数据聚集到Channel
     */
    public static void test3(String sourcePath,String targetPath){
        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(targetPath);
             FileChannel fisChannel = fis.getChannel();
             FileChannel fosChannel = fos.getChannel();){
            // 3.定义多个缓冲区做数据分散
            ByteBuffer buffer1 = ByteBuffer.allocate(10);
            ByteBuffer buffer2 = ByteBuffer.allocate(1024);
            ByteBuffer[] buffers = {buffer1, buffer2};
            fisChannel.read(buffers);
            for (ByteBuffer b:buffers) {
                // 切换到读数据模式
                b.flip();
                System.out.println(new String(b.array(), 0, b.remaining(),StandardCharsets.UTF_8));
            }
            // 6.聚集写入到通道
            fosChannel.write(buffers);
            System.out.println("复制完成!");
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    /**
     * 目标通道中获取数据
     *
     * 這裡會將整個文件緩存到內存中，在內存中區獲取數據信息
     * @param sourcePath
     * @param targetPath
     */
    public static void transferFromTest(String sourcePath,String targetPath){
        StopWatch watch = new StopWatch();
        watch.start();
        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(targetPath);
             FileChannel fisChannel = fis.getChannel();
             FileChannel fosChannel = fos.getChannel();){
            // 6.將通道中的數據直接複製到fosChannel中，
            fosChannel.transferFrom(fisChannel,fisChannel.position(), fisChannel.size());
            System.out.println("复制完成!");
        } catch (IOException  e) {
            e.printStackTrace();
        }
        watch.stop();
        log.info("目標執行時間:{}-毫秒",watch.getTime());
    }

    /**
     * 直接复制数据信息到指定的地方
     * @param sourcePath
     * @param targetPath
     */
    public static void transferToTest(String sourcePath,String targetPath){
        StopWatch watch = new StopWatch();
        watch.start();
        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(targetPath);
             FileChannel fisChannel = fis.getChannel();
             FileChannel fosChannel = fos.getChannel();){
            // 6.將通道中的數據直接複製到fosChannel中，
            fisChannel.transferTo(fisChannel.position(),fisChannel.size(), fosChannel);
            System.out.println("复制完成!");
        } catch (IOException  e) {
            e.printStackTrace();
        }
        watch.stop();
        log.info("目標執行時間:{}-毫秒",watch.getTime());
    }

    public static void main(String[] args) {
       // read("D://aaa.doc");
        transferToTest("D://123.zip","D://1234.zip");
    }
}
