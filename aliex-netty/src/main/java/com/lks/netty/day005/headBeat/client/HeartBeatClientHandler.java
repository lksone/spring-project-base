package com.lks.netty.day005.headBeat.client;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lks
 * @description 指定的channel 中的codec
 * @e-mail 1056224715@qq.com
 * @date 2023/8/17 16:22
 */
@Slf4j
public class HeartBeatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("客戶端接收到的消息-------msg:{} :" ,msg);
        //判断如果client，心跳链接超时3次那么就会断开客户端
        if(msg!= null && msg.contains("you are out")) {
            log.error(" 服務器關閉連接，clientIP:{} , 客户端端将自动断开连接！！！",ctx.channel().localAddress().toString());
            ctx.channel().closeFuture();
        }
    }
}
