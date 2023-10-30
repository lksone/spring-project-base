package com.lks.netty.day006;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/9/17 22:53
 */
public class FileUploadServerHandler extends ChannelInboundHandlerAdapter {

    private int byteRead;
    private volatile int start = 0;
    private String file_dir = "D:/tempData";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FileUploadFile) {
            FileUploadFile ef = (FileUploadFile) msg;
            byte[] bytes = ef.getBuffer();
            byteRead = ef.getEndPosition();
            //文件名
            String md5 = ef.getFileMd5();
            //路径
            String path = file_dir + File.separator + md5;
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(start);
            randomAccessFile.write(bytes);
            start = start + byteRead;
            if (byteRead > 0) {
                ctx.writeAndFlush(start);
            } else {
                randomAccessFile.close();
                ctx.close();
            }
        }
    }


    /**
     * 异常如何处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
