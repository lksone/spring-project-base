package com.lks.netty.day005.headBeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author lks
 * @description 客户端
 * @e-mail 1056224715@qq.com
 * @date 2023/8/17 15:43
 */
@Slf4j
public class HeadBeatClient {

    int port;
    Channel channel;
    Random random ;

    public HeadBeatClient(int port){
        this.port = port;
        random = new Random();
    }

    public void start() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new HeartBeatClientInitializer());

            connect(bootstrap,port);
            String  text = "I am alive";
            //發送消息
            while (channel.isActive()){
                sendMsg(text);
            }
        }catch(Exception e){
            // do something
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    /**
     * 發送消息
     * @param text
     * @throws Exception
     */
    public void sendMsg(String text) throws Exception{
        channel.writeAndFlush(text);
        int num = random.nextInt(10);
        log.info(" 客戶端連接多少秒：{} ",num);
        Thread.sleep(num * 1000);
    }

    /**
     * 连接
     * @param bootstrap
     * @param port
     * @throws Exception
     */
    public void connect(Bootstrap bootstrap,int port) throws Exception{
        channel = bootstrap.connect("localhost",port).sync().channel();
    }


    public static void main(String[] args) {
        HeadBeatClient headBeatClient = new HeadBeatClient(8090);
        headBeatClient.start();
    }
}
