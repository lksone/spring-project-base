package com.lks.netty.day002.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/8/14 14:47
 */
public class NettyClient {
    private static String IP = "127.0.0.1";

    private static int PORT = 1100;


    /**
     * 客戶端連接服務器
     */
    public static void connect(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            //客户端启动辅助类
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    //设置为NioSocketChannel
                    .channel(NioSocketChannel.class)
                    //设置指定时间
                    .option(ChannelOption.AUTO_READ,true)
                    //事件处理类
                    .handler(new NettyClientChannelInitializer());
            //调用它的connect操作连接服务端,调用同步阻塞方法sync等待绑定操作完成
            ChannelFuture sync = bootstrap.connect(new InetSocketAddress(IP, PORT)).sync();
            //异步操作的通知回调
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅的退出,释放线程池资源
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        connect();
    }
}
