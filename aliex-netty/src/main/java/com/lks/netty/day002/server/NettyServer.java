package com.lks.netty.day002.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lks
 * @description netty 創建一個Server服務器端口
 * @e-mail 1056224715@qq.com
 * @date 2023/8/14 14:47
 */
public class NettyServer {


    /**
     * 设置端口号
     */
    private static int port = 1100;

    public static void main(String[] args) {
        //用于处理服务端接受客户端连接
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        //用于SocketChannel的网络读写操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //用于启动NIO服务端的辅助启动类,目的是降低服务端的开发复杂度
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup, workerGroup)
                    //对应JDK NIO类库中的ServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    //配置NioServerSocketChannel的TCP参数 指定处理客户端连接队列大小
                    //ChannelOption.SO_KEEPALIVE 一直保持连接活动状态
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //绑定I/O的事件处理类
                    .childHandler(new NettyServerChannelInitializer());
            //调用它的bind操作监听端口号,sync 等待异步操作执行完毕
            ChannelFuture f = b.bind(port).sync();
            //异步操作的通知回调
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅的退出,释放线程池资源
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
