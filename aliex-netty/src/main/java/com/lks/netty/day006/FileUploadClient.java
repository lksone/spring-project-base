package com.lks.netty.day006;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.File;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/9/17 22:53
 */
public class FileUploadClient {


    public void connect(int port, String host, final FileUploadFile fileUploadFile) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                    ch.pipeline().addLast(new FileUploadClientHandler(fileUploadFile));
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        try {
            FileUploadFile uploadFile = new FileUploadFile();
            File file = new File("D:/8a53e0db1d9369aecfc6eb3b134420d0.docx");
            // 文件名
            String fileMd5 = file.getName();
            uploadFile.setFile(file);
            uploadFile.setFileMd5(fileMd5);
            // 文件开始位置
            uploadFile.setStartPosition(0);
            new FileUploadClient().connect(port, "127.0.0.1", uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
