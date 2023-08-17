package com.lks.netty.day003.file;

import io.netty.channel.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author lks
 * @description
 * @e-mail 1056224715@qq.com
 * @date 2023/8/16 15:42
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {

    /**
     * 指定讀取的文件數據
     */
    private final File file;

    /**
     * ssl 上下文
     */
    private final SslContext sslCtx;

    public ChunkedWriteHandlerInitializer(File file, SslContext sslCtx) {
        this.file = file;
        this.sslCtx = sslCtx;
    }


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //// 将SslHandler添加到ChannelPipeline中
        pipeline.addLast(new SslHandler(sslCtx.newEngine(ch.alloc())));
        // 添加ChunkedWriteHandler以处理作为ChunkedInput传入的数据
        pipeline.addLast(new ChunkedWriteHandler());
        // 一旦连接建立，WriteStreamHandler就开始写文件数据
        pipeline.addLast(new WriteStreamHandler());
    }


    public final class WriteStreamHandler extends ChannelInboundHandlerAdapter {  //4

        // 当连接建立时，channelActive()方法将使用ChunkedInput写文件数据
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            /**
             *  使用 ChunkedStream 来进行写入
             */
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
        }
    }
}
