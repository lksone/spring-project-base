package com.lks.netty.day003.nio.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lks
 * @description netty客户端启动类
 * @e-mail 1056224715@qq.com
 * @date 2023/8/15 17:01
 */
public class NettyClient {

    private static String ip= "127.0.0.1";

    private static Integer port = 1100;

    public static void main(String[] args) {
        //用于SocketChannel的网络读写操作
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //客户端启动辅助类
            Bootstrap b = new Bootstrap();
            b.group(group).
                    //设置为NioSocketChannel
                            channel(NioSocketChannel.class).
                    option(ChannelOption.AUTO_READ, true).
                    //事件处理类
                            handler(new MyClientChannelInitializer());
            //调用它的connect操作连接服务端,调用同步阻塞方法sync等待绑定操作完成
            ChannelFuture future = b.connect(ip, port).sync();
            //异步操作的通知回调
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅的退出,释放线程池资源
            group.shutdownGracefully();
        }
    }
}
