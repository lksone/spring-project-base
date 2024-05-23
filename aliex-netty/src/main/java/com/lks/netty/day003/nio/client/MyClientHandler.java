package com.lks.netty.day003.nio.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lks
 * @description 客户端事件操作类
 * @e-mail 1056224715@qq.com
 * @date 2023/8/15 17:33
 */
@Slf4j
public class MyClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * 当客户端主动链接服务端的链接后，通道就是活跃的了此时触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      /*  Person.Builder builder = Person.newBuilder();
        builder.setName("clientlks");
        builder.setAge(20);*/
       // ctx.writeAndFlush(builder.build());
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。
     * 也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("===================" + ctx.channel().localAddress().toString() + " 断开连接===================");
    }

    /**
     * 当管道中传输数据信息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())  + "接收到消息：");
     //   log.info( JsonFormat.printToString((Person) msg));
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("---------------------读取数据完成----------------------------------------------");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("----用户时间触发-------------------------------------------------------------------");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("客戶端發現異常信息：message={}--{}",cause.getMessage(),cause);
    }
}
