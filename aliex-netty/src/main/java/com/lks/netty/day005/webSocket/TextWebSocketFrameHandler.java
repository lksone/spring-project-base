package com.lks.netty.day005.webSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/8/18 10:18
 */
@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group){
        this.group = group;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
        // websocket 握手事件,当前室握手事件
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete handshake = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            // do something with handshake
            ChannelPipeline pipeline = ctx.pipeline();

            pipeline.channel().writeAndFlush(new TextWebSocketFrame(" 客户端： " + ctx.channel() + " 加入聊天室！ "));
          //  group.add(ctx.channel());
        }
        super.userEventTriggered(ctx,evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("客户端信息：{}",msg.text());
        group.writeAndFlush(msg.retain());
    }
}
