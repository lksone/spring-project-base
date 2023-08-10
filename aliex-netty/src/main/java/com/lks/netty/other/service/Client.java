package com.lks.netty.other.service;

import com.alibaba.fastjson2.JSONObject;
import com.lks.netty.other.dto.User;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author lks
 * @description 客户端信息
 * @e-mail 1056224715@qq.com
 * @date 2023/5/25 15:19
 */
public class Client {

    /**
     * 使用slf4j的抽象类实现也是可以的，如果日志框架换的话需要修改代码
     */
    private final static Logger log = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws InterruptedException {
        // 首先，netty通过ServerBootstrap启动服务端
        Bootstrap client = new Bootstrap();

        //第1步 定义线程组，处理读写和链接事件，没有了accept事件
        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group);

        //第2步 绑定客户端通道
        client.channel(NioSocketChannel.class);

        //第3步 给NIoSocketChannel初始化handler， 处理读写事件
        //通道是NioSocketChannel
        client.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                //字符串编码器，一定要加在SimpleClientHandler 的上面
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                //找到他的管道 增加他的handler
                ch.pipeline().addLast(new SimpleClientHandler());
            }
        });

        //连接服务器
        ChannelFuture future = client.connect("localhost", 8081).sync();

        //发送数据给服务器
        User user = new User();
        user.setAge(12);
        user.setId(1);
        user.setName("sssss");
        future.channel().writeAndFlush(JSONObject.toJSONString(user) + "\r\n");

        for (int i = 0; i < 5; i++) {
            String msg = "ssss" + i + "\r\n";
            future.channel().writeAndFlush(msg);
        }

        //当通道关闭了，就继续往下走
        future.channel().closeFuture().sync();

        //接收服务端返回的数据
        AttributeKey<String> key = AttributeKey.valueOf("ServerData");
        Object result = future.channel().attr(key).get();
        log.info("接收到服务器返回数据:{}",result.toString());
    }
}
