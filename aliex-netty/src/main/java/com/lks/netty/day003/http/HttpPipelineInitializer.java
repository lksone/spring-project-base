package com.lks.netty.day003.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lks
 * @description
 * <p>
 *     下面代码的HttpPipelineInitializer可以为应用提供HTTP支持，
 *     仅仅只需要往ChannelPipeline中添加正确的ChannelHandler即可。　
 * </p>
 * @e-mail 1056224715@qq.com
 * @date 2023/8/15 23:33
 */
@Slf4j
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
    /**
     * 是否是客戶端
     */
    private final boolean client;
    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //客户单，请求
        if (client) {
            //返回解密器
            pipeline.addLast("decoder", new HttpResponseDecoder());
            //请求加密器
            pipeline.addLast("encoder", new HttpRequestEncoder());
        } else {
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("encoder", new HttpResponseEncoder());
        }
    }
}
