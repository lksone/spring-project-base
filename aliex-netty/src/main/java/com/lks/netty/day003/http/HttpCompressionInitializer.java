package com.lks.netty.day003.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author lks
 * @description HTTP压缩
 * <p>
 *     使用HTTP时，建议采用压缩来尽可能减少传输数据的大小。Netty为压缩和解压缩提供了ChannelHandler的实现，其支持gzip和deflate编码。
 *
 * 　　如下代码展示了如何自动压缩HTTP消息。
 * </p>
 * @e-mail 1056224715@qq.com
 * @date 2023/8/16 9:55
 */
public class HttpCompressionInitializer extends ChannelInitializer<Channel> {


    private final boolean isClient;

    public HttpCompressionInitializer(boolean isClient) {
        this.isClient = isClient;
    }


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient) {
            pipeline.addLast("codec", new HttpClientCodec());
            pipeline.addLast("decompressor", new HttpContentDecompressor());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());
            pipeline.addLast("compressor", new HttpContentCompressor());
        }
    }
}
