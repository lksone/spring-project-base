package com.lks.netty.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.Charset;

/**
 * @author lks
 * @description 处理服务端返回的数据
 * @e-mail 1056224715@qq.com
 * @date 2023/5/29 10:31
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    private final static Logger log = LogManager.getLogger(SimpleClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            String value = ((ByteBuf) msg).toString(Charset.defaultCharset());
            log.info("服务器返回的数据：value{}", value);
        }

        AttributeKey<String> key = AttributeKey.valueOf("ServerData");
        ctx.channel().attr(key).set("客户端处理完毕");

        //把客户端的通道关闭
        ctx.channel().close();
    }
}
