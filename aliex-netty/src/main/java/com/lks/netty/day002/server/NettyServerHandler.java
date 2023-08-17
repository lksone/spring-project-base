package com.lks.netty.day002.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lks
 * @description 操作类
 * @e-mail 1056224715@qq.com
 * @date 2023/8/14 23:44
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    public NettyServerHandler() {
        super();
    }

    /**
     *
     * 调用ChannelHandlerContext.fireChannelRegistered（）以转发到ChannelPipeline
     * 中的下一个ChannelInboundHandler。子类可以覆盖此方法以更改行为。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("----------------------ChannelInitializer-----------------------------------");
        super.channelRegistered(ctx);
    }

    /**
     * 调用ChannelHandlerContext.fireChannelUnregistered（）以转发到ChannelPipeline中的下一个ChannelInboundHandler。
     * 子类可以覆盖此方法以更改行为。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("----------------------channelUnregistered-----------------------------------");
        super.channelUnregistered(ctx);
    }

    /**
     * 当客户端主动连接服务端,通道活跃后触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //在接收到客户端连接的时候通知客户端连接成功
        String msg = "与服务端建立连接成功" + new Date();
        ByteBuf buf = Unpooled.buffer(msg.getBytes().length);
        buf.writeBytes(msg.getBytes("utf-8"));
        ctx.writeAndFlush(buf);
    }

    /**
     * 当客户端主动断开连接,通道不活跃触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("当通道不活跃的时候触发---------IP:{}斷開連接",ctx.channel().localAddress().toString());
    }

    /**
     * 通道有消息触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("----------------------channelRead-----------------------------------");
        //接收msg消息
        ByteBuf buf = (ByteBuf) msg;
        byte[] msgByte = new byte[buf.readableBytes()];
        buf.readBytes(msgByte);
        System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())  + "接收到消息：");
        System.out.println(new String(msgByte, Charset.forName("utf-8")));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("----------------------channelReadComplete-----------------------------------");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("----------------------userEventTriggered-----------------------------------");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.info("----------------------channelWritabilityChanged-----------------------------------");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("----------------------exceptionCaught-----------------------------------");
        //在发生异常时主动关掉连接
        ctx.close();
        log.error("发现异常：\r\n" + cause.getMessage());
    }
}
