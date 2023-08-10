package com.lks.netty.other.service.BigFile.server.handler;

import com.lks.netty.other.service.BigFile.protocol.FilePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author lks
 * @description Handler
 * @e-mail 1056224715@qq.com
 * @date 2023/8/9 16:49
 */
@Slf4j
@ChannelHandler.Sharable
public class FileUploadServerHandler extends ChannelInboundHandlerAdapter {

    private int byteRead;
    private volatile Long start = 0L;
    private String file_dir;

    public FileUploadServerHandler(String file_dir) {
        this.file_dir = file_dir;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //如果传递的参数为FilePacket
        if (msg instanceof FilePacket) {
            FilePacket filePacket = (FilePacket) msg;
            byte[] bytes = filePacket.getBytes();
            byteRead = filePacket.getEndPos();
            String md5 = filePacket.getFile_md5();
            String path = file_dir + File.separator + md5;
            log.info("server的文件路径：{}"+path);
            File file = new File(path);
            //文件讀取制定范围的数据信息
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(start);
            randomAccessFile.write(bytes);
            start = start + byteRead;
            if (byteRead > 0) {
                ctx.writeAndFlush(start);
            } else {
                randomAccessFile.close();
            }
        }
    }

    /**
     * ChannelHandler回调方法出现异常时被回调
     *
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
