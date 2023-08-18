package com.lks.netty.day005.redis;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author lks
 * @description 客户端
 * <P>
 *     redis 客户端写入
 * </P>
 * @e-mail 1056224715@qq.com
 * @date 2023/8/17 22:27
 */
@Slf4j
public class RedisClient {

    /**
     * 目标主机
     */
    private String host;

    /**
     * 目标主机端口
     */
    private int port;

    public RedisClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new RedisClientInitializer());
            //连接Redis 中存储数据信息
            Channel channel = bootstrap.connect(host, port).sync().channel();
            log.info(" connected to host : " + host + ", port : " + port);
            log.info(" type redis's command to communicate with redis-server or type 'quit' to shutdown ");
            //读取cmd 输入参数
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            ChannelFuture lastWriteFuture = null;
            for (; ; ) {
                String s = in.readLine();
                if (s.equalsIgnoreCase("quit")) {
                    break;
                }
                System.out.print(">");
                lastWriteFuture = channel.writeAndFlush(s);
                lastWriteFuture.addListener(new GenericFutureListener<ChannelFuture>() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            System.err.print("write failed: ");
                            future.cause().printStackTrace(System.err);
                        }
                    }
                });
            }
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
            log.info(" bye ");
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        RedisClient client = new RedisClient("127.0.0.1", 6379);
        client.start();
    }
}
