package com.lks.netty.day003.oio;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;

/**
 * @author lks
 * @description OIO是什么方式？
 * @e-mail 1056224715@qq.com
 * @date 2023/8/15 15:13
 */
public class NettyOioServer {

    public static void main(String[] args) {
        EventLoopGroup group = new OioEventLoopGroup();
    }
}
