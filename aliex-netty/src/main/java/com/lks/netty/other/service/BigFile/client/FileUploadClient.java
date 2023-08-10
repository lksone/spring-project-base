package com.lks.netty.other.service.BigFile.client;

import com.lks.netty.other.service.BigFile.client.handler.FileUploadClientHandler;
import com.lks.netty.other.service.BigFile.protocol.FilePacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author lks
 * @description 文件上传Client
 * @e-mail 1056224715@qq.com
 * @date 2023/8/9 17:10
 */
@Slf4j
public class FileUploadClient {

    private final static Integer PORT = 8089;

    public static void main(String[] args) {
        FilePacket filePacket = new FilePacket();
        File file = new File("D:\\123.zip");
        String fileMd5 = file.getName();
        filePacket.setFile(file);
        filePacket.setFile_md5(fileMd5);
        //要传输的文件的初始信息
        filePacket.setStartPos(0);
        new FileUploadClient().connect("127.0.0.1", PORT, filePacket);
    }

    /**
     * 客户端连接数据
     *
     * @param host
     * @param port
     * @param filePacket
     */
    public void connect(String host, int port, final FilePacket filePacket) {
        //只需要一个线程组，和服务端有所不同
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new ObjectEncoder());
                        channel.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                        //自定义的handler
                        channel.pipeline().addLast(new FileUploadClientHandler(filePacket));
                    }
                });
        ChannelFuture future = null;
        try {
            //使得链接保持
            future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
            log.info("通道關閉，文件傳輸完成");
        }
    }
}
