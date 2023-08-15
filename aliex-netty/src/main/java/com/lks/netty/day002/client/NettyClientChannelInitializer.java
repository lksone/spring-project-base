package com.lks.netty.day002.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;


/**
 * @author lks
 * @description
 *
 * MyChannelInitializer的主要目的是为程序员提供了一个简单的工具，用于在某个Channel注册到EventLoop后，
 * 对这个Channel执行一些初始化操作。ChannelInitializer虽然会在一开始会被注册到Channel相关的pipeline里，
 * 但是在初始化完成之后，ChannelInitializer会将自己
 * 从pipeline中移除，不会影响后续的操作。
 *
 *
 * @e-mail 1056224715@qq.com
 * @date 2023/8/14 15:00
 */
@Slf4j
public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 这个方法在Channel被注册到EventLoop的时候会被调用
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info("---------連接到了服務器-----------");
        ch.pipeline().addLast(new NettyClientHandler());
    }
}
