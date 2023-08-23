package com.lks.netty.day005.webSocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;


/**
 * @author lks
 * @description webSocket数据信息
 * @e-mail 1056224715@qq.com
 * @date 2023/8/18 10:18
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    private ChannelGroup channelGroup;

    public WebSocketServerInitializer(ChannelGroup channelGroup){
        this.channelGroup = channelGroup;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));

        //webSocket
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler(channelGroup));
    }
}
