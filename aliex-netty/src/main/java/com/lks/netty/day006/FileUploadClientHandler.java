package com.lks.netty.day006;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/9/17 22:54
 */
public class FileUploadClientHandler extends ChannelInboundHandlerAdapter {

    private int byteRead;
    private volatile int start = 0;
    private volatile int lastLength = 0;
    public RandomAccessFile randomAccessFile;
    private FileUploadFile fileUploadFile;

    /**
     * 將寫入的對象傳入
     * @param ef
     */
    public FileUploadClientHandler(FileUploadFile ef) {
        if (ef.getFile().exists()) {
            if (!ef.getFile().isFile()) {
                System.out.println("Not a file :" + ef.getFile());
                return;
            }
        }
        this.fileUploadFile = ef;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            randomAccessFile = new RandomAccessFile(fileUploadFile.getFile(), "r");
            randomAccessFile.seek(fileUploadFile.getStartPosition());
            lastLength = (int) randomAccessFile.length() / 10;
            byte[] bytes = new byte[lastLength];
            //讀取緩存中的數據
            if ((byteRead = randomAccessFile.read(bytes)) != -1) {
                fileUploadFile.setEndPosition(byteRead);
                fileUploadFile.setBuffer(bytes);
                ctx.writeAndFlush(fileUploadFile);
            } else {
                System.out.println("文件已经读完");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Integer) {
            start = (Integer) msg;
            if (start != -1) {
                randomAccessFile = new RandomAccessFile(fileUploadFile.getFile(), "r");
                randomAccessFile.seek(start);
                System.out.println("块儿长度：" + (randomAccessFile.length() / 10));
                System.out.println("长度：" + (randomAccessFile.length() - start));
                int a = (int) (randomAccessFile.length() - start);
                int b = (int) (randomAccessFile.length() / 10);
                if (a < b) {
                    lastLength = a;
                }
                byte[] bytes = new byte[lastLength];
                System.out.println("-----------------------------" + bytes.length);
                if ((byteRead = randomAccessFile.read(bytes)) != -1 && (randomAccessFile.length() - start) > 0) {
                    System.out.println("byte 长度：" + bytes.length);
                    fileUploadFile.setEndPosition(byteRead);
                    fileUploadFile.setBuffer(bytes);
                    try {
                        ctx.writeAndFlush(fileUploadFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    randomAccessFile.close();
                    ctx.close();
                    System.out.println("文件已经读完--------" + byteRead);
                }
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
