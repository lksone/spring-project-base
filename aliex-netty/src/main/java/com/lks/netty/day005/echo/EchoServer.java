package com.lks.netty.day005.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @author lks
 * @description echo的sever 主要測試用於telnet localhost 端口
 * @e-mail 1056224715@qq.com
 * @date 2023/8/17 17:41
 */
public class EchoServer {

    int port;
    public EchoServer(int port){
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boosGroup,workGroup)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioServerSocketChannel.class)
                .childHandler(new EchoServerHandler());
        ChannelFuture bind = bootstrap.bind(new InetSocketAddress(port));
        bind.channel().closeFuture().sync();

    }

    public static void main(String[] args) {
        try {
            EchoServer server = new EchoServer(9991);
            server.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
