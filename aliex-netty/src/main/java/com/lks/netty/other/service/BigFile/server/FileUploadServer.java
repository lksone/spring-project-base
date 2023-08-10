package com.lks.netty.other.service.BigFile.server;

import com.lks.netty.other.service.BigFile.server.handler.FileUploadServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author lks
 * @description Server端口启动
 * @e-mail 1056224715@qq.com
 * @date 2023/8/9 16:39
 */
public class FileUploadServer {

    private final static Integer PORT = 8089;


    public static void main(String[] args) {
        new FileUploadServer().bind(PORT);
    }

    /**
     * 创建启动Server
     *
     * @param port
     */
    public void bind(int port) {
        //服务端的管理线程
        EventLoopGroup bootGroup = new NioEventLoopGroup();
        //服务端的工作线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //ServerBootstrap负责初始化netty服务器，并且开始监听端口的socket请求
        ServerBootstrap bootstrap = new ServerBootstrap();
        //绑定管理线程和工作线程
        bootstrap.group(bootGroup, workerGroup)
                //ServerSocketChannelFactory 有两种选择，一种是NioServerSocketChannelFactory，一种是OioServerSocketChannelFactory。
                .channel(NioServerSocketChannel.class)
                //BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，
                // 用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
                .option(ChannelOption.SO_BACKLOG, 124)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new ObjectEncoder());
                        channel.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(null)));
                        channel.pipeline().addLast(new FileUploadServerHandler("D:\\")); // 自定义Handler
                    }
                });

        try {
            ChannelFuture sync = bootstrap.bind(port).sync();
            //保证了服务一直启动，相当于一个死循环
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bootGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}

