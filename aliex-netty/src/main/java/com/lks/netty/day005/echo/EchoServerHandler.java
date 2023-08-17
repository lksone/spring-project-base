package com.lks.netty.day005.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lks
 * @description Hantdle 讀取指定的ByteBuf的類型
 * @e-mail 1056224715@qq.com
 * @date 2023/8/17 17:42
 */
@Slf4j
public class EchoServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info(" EchoServerHandler active... ");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("獲取echo:{}",msg);
        msg.retain();
        ctx.writeAndFlush(msg);
    }
}
