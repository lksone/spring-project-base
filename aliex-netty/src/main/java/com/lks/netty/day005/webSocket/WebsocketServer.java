package com.lks.netty.day005.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * @author lks
 * @description webSocketServer的数据信息
 * @e-mail 1056224715@qq.com
 * @date 2023/8/18 10:17
 */
public class WebsocketServer {

    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group = new NioEventLoopGroup();
    int port;
    public WebsocketServer(int port){
        this.port = port;
    }

    public ChannelFuture start(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketServerInitializer(channelGroup));
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(port));
        future.syncUninterruptibly();
        System.out.println(" server start up on port: "+port);
        return future;
    }
    public void destroy(){
        channelGroup.close();
        group.shutdownGracefully();
    }
}
