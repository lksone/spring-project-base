package com.lks.netty;

import cn.hutool.core.util.CharsetUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * @author lks
 * @description 服务器信息
 * @e-mail 1056224715@qq.com
 * @date 2023/12/4 17:46
 */
public class FileServer {

    /**
     * 进行port
     *
     * @param port
     */
    public void run(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(
                                            //编码  编码器从下至上。 顺序是  FileServerHandler >>>>StringEncoder
                                            //解码 是解码器从上到下。 每一个的返回值是下一个的入参。
                                            //     LineBasedFrameDecoder  >> StringDecoder >>  FileServerHandler
                                            //到 FileServerHandler 拿到的就是String了。
                                            //将文件内容编码为字符串
                                            new StringEncoder(CharsetUtil.charset(CharsetUtil.UTF_8)),
                                            //按照回车换行符对数据报报进行解码
                                            new LineBasedFrameDecoder(1024),
                                            //将数据报解码为字符串
                                            new StringDecoder(CharsetUtil.charset(CharsetUtil.UTF_8)),
                                            //业务处理类
                                            new FileServerHandler());


                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("Start file server at port : " + port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new FileServer().run(8080);
    }
}
