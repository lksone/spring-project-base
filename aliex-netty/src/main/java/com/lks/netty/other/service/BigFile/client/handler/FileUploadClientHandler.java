package com.lks.netty.other.service.BigFile.client.handler;

import com.lks.netty.other.service.BigFile.protocol.FilePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.RandomAccessFile;

/**
 * @author lks
 * @description handler
 * @e-mail 1056224715@qq.com
 * @date 2023/8/9 17:09
 */
@Slf4j
@ChannelHandler.Sharable
public class FileUploadClientHandler extends ChannelInboundHandlerAdapter {

    private int byteRead;
    /**
     * 使用Long 当传输的文件大于2G时，Integer类型会不够表达文件的长度
     */
    private volatile Long start = 0L;
    private volatile int lastLength = 0;
    public RandomAccessFile randomAccessFile;
    private FilePacket filePacket;

    public FileUploadClientHandler(FilePacket filePacket) {
        if (filePacket.getFile().exists()) {
            if (!filePacket.getFile().isFile()) {
                log.info("Not a file:" + filePacket.getFile());
            }
        }
        this.filePacket = filePacket;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 客户端发送FilePacket 到服务端，服务端处理完文件当前部分的数据，返回下次文件段的起始位置
         */
        if (msg instanceof Long) {
            start = (Long) msg;
            if (start != -1) {
                randomAccessFile = new RandomAccessFile(filePacket.getFile(), "r");
                //将服务端返回的数据设置此次读操作，文件的起始偏移量
                randomAccessFile.seek(start);
                log.info("文件长度：" + (randomAccessFile.length()));
                log.info("此次分片开始位置：" + start);
                log.info("块儿长度：" + (Integer.MAX_VALUE / 4));
                log.info("剩余长度：" + (randomAccessFile.length() - start));
                //还有多长？
                Long a = randomAccessFile.length() - start;
                int lastLength = (int) (Integer.MAX_VALUE / 4);
                if (a <= lastLength) {
                    log.info("最后一片长度：" + a);
                    lastLength = a.intValue();
                }
                byte[] bytes = new byte[lastLength];
                //这个判断关闭判断调整存在漏洞
                if ((byteRead = randomAccessFile.read(bytes)) != -1 && (randomAccessFile.length() - start) > 0) {
                    log.info("byte长度：" + bytes.length);
                    log.info("-----------------" + bytes.length);
                    filePacket.setEndPos(byteRead);
                    filePacket.setBytes(bytes);
                    ctx.writeAndFlush(filePacket);
                } else {
                    randomAccessFile.close();
                    ctx.close();
                    log.info("文件已读完------" + byteRead);
                }
            }
        }
    }

    /**
     * 当前channel激活的时候的时候触发  优先于channelRead方法执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        randomAccessFile = new RandomAccessFile(filePacket.getFile(), "r");
        randomAccessFile.seek(filePacket.getStartPos());
        //每次发送的文件块数的长度
        lastLength = Integer.MAX_VALUE / 4;
        byte[] bytes = new byte[lastLength];
        if ((byteRead = randomAccessFile.read(bytes)) != -1) {
            filePacket.setEndPos(byteRead);
            filePacket.setBytes(bytes);
            ctx.writeAndFlush(filePacket);
        } else {
            System.out.println("文件已读完");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
