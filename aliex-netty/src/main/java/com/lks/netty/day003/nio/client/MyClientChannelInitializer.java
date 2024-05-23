package com.lks.netty.day003.nio.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;


/**
 * @author lks
 * @description
 * <P>
 *     MyChannelInitializer的主要目的是为程序员提供了一个简单的工具，用于在某个Channel注册到EventLoop后，对这个Channel执行一些初始
 *    化操作。ChannelInitializer虽然会在一开始会被注册到Channel相关的pipeline里，但是在初始化完成之后，ChannelInitializer会将自己
 *    从pipeline中移除，不会影响后续的操作。
 * </P>
 *
 * @e-mail 1056224715@qq.com
 * @date 2023/8/15 17:02
 */
@Slf4j
public class MyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info("-----------服務器連接成功----------------");

        //protobuf 处理
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        //ch.pipeline().addLast(new ProtobufDecoder(Person.getDefaultInstance()));
        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        ch.pipeline().addLast(new ProtobufEncoder());
        ch.pipeline().addLast(new MyClientHandler());
    }
}
