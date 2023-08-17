package com.lks.netty.day005.echo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/8/17 17:42
 */
public class EchoServerInitializer extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 基于分隔符 \r\n 的数据帧解码。
        pipeline.addLast(new LineBasedFrameDecoder(1024));
        pipeline.addLast(new EchoServerHandler());
    }
}
