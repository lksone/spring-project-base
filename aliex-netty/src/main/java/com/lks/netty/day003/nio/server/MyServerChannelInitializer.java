/*
package com.lks.netty.day003.nio.server;

import com.lks.netty.day003.nio.proto.Person;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

*/
/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/8/15 17:48
 *//*

public class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("=========有客户端连接服务器=========");
        System.out.println("ip:"+socketChannel.localAddress().getHostString()+"         port:"+socketChannel.localAddress().getPort());

        //protobuf 处理
        socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        socketChannel.pipeline().addLast(new ProtobufDecoder(Person.getDefaultInstance()));
        socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        socketChannel.pipeline().addLast(new ProtobufEncoder());
        socketChannel.pipeline().addLast(new MyServerHandler());
    }
}
*/
