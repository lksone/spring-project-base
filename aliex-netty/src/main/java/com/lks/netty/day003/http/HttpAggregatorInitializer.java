package com.lks.netty.day003.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author lks
 * @description
 * <p>
 *     HTTP消息聚合
 *     当管道中的处理器被初始化后，就可以处理不同的消息，但由于HTTP请求和响应由多部分组成，需要将这些部分汇聚成完整的结果，Netty提供了一个合成器，
 *     其可以消息的不同部分组合成FullHttpRequest和FullHttpResponse。
 *     由于消息在完整之前需要被缓存，因此其会带来小小的开销，但是不用担心消息的碎片化处理
 * </p>
 * @e-mail 1056224715@qq.com
 * @date 2023/8/15 23:41
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {

    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient) {
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());
        }
        pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
    }
}
