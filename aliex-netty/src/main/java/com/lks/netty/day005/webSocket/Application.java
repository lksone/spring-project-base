package com.lks.netty.day005.webSocket;

import io.netty.channel.ChannelFuture;

/**
 * @author lks
 * @description websocket server
 * <p>
 *
 * </p>
 * @e-mail 1056224715@qq.com
 * @date 2023/8/18 10:18
 */
public class Application {

    public static void main(String[] args) {
        WebsocketServer websocketServer = new WebsocketServer(8090);
        ChannelFuture start = websocketServer.start();
        //添加守护进程
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                websocketServer.destroy();
            }
        });
        start.channel().closeFuture().syncUninterruptibly();
    }
}
