package com.lks.netty.day003.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;


/**
 * @author lks
 * @description WebSocket解决
 * <p>
 *     如果底层使用HTTP协议，是一系列请求-响应交互，那么如何实时发布信息。AJAX提供了一些改进，
 *     但是数据流的驱动仍然来自客户端的请求。
 *     而WebSocket规范及其实现代表了一种更有效的解决方案，其为双向流量提供了一个单一的TCP连接，
 *     其在客户端和服务器之间提供真正的双向数据交换，并且可以处理任何类型的数据。
 * </p>
 * @e-mail 1056224715@qq.com
 * @date 2023/8/16 10:07
 */
@Slf4j
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(65536),
                new WebSocketServerProtocolHandler("/websocket"),
                new TextFrameHandler(),
                new BinaryFrameHandler(),
                new ContinuationFrameHandler());
    }

    /**
     *
     */
    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            // Handle text frame
        }
    }

    /**
     *
     */
    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
                                 BinaryWebSocketFrame msg) throws Exception {
            // Handle binary frame
        }
    }


    /**
     *
     */
    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
            // Handle continuation frame
        }
    }
}


