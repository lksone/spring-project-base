package com.lks.netty.day003.file;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author lks
 * @description 读取大文件数据类型
 * <P>
 *     文件數據類型和http webSocket和ssl等其他類型不同
 *     这种方式需要jDK和操作系统支持ZERO_COPY,有一些window并不支持
 * </P>
 * @e-mail 1056224715@qq.com
 * @date 2023/8/16 16:35
 */
@Slf4j
public class BigFileChanelHandler extends ChannelInitializer<Channel> {

    private final File file;

    public BigFileChanelHandler(File file) {
        this.file = file;
    }


    @Override
    protected void initChannel(Channel ch) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        //这是一个fileRegion
        FileRegion fileRegion = new DefaultFileRegion(fileInputStream.getChannel(), 0, file.length());
        ch.pipeline().writeAndFlush(fileRegion).addListener(new FileListener());
    }


    public static class FileListener implements ChannelFutureListener {

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if(!future.isSuccess()){
                future.cause().printStackTrace();
                log.error("读取文件失败------------");
            }
        }
    }
}
