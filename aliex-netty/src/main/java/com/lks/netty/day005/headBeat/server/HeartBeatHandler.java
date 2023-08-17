package com.lks.netty.day005.headBeat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lks
 * @description 心跳检测 ，指定类型String类型
 * @e-mail 1056224715@qq.com
 * @date 2023/8/17 15:52
 */
@Slf4j
public class HeartBeatHandler extends SimpleChannelInboundHandler<String> {

    int readIdleTimes = 0;

    /**
     * 为类型I的每个消息调用。就是指定的他的泛型
     * Params: ctx-这个SimpleChannelInboundHandler属于msg的ChannelHandlerContext-要处理的消息
     * exception:异常-如果发生错误，将引发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("======> 服務器接收到的消息：msg:{}: ",msg);
        ctx.channel().writeAndFlush("copy that");
    }

    /**
     * 如果应该处理给定的消息，则返回true。
     * 如果为false，它将被传递给ChannelPipeline中的下一个ChannelInboundHandler。
     *
     * @param msg
     * @return
     * @throws Exception
     */
    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        return super.acceptInboundMessage(msg);
    }

    /**
     * 当当前通道从对等方读取消息时调用
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    /**
     * ChannelHandlerContext的频道已在其EventLoop中注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    /**
     * ChannelHandlerContext的频道未从其EventLoop中注册
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * ChannelHandlerContext的频道现在处于活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("================IP:{}，处于活跃状态",ctx.channel().localAddress());
    }

    /**
     * 已注册的ChannelHandlerContext的频道现在处于非活动状态，并已达到其生命周期。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * 当当前读取操作读取的最后一条消息已被channelRead(ChannelHandlerContext，对象) 消耗时调用。
     * 如果ChannelOption.AUTO_READ处于关闭状态，则在调用ChannelHandlerContext.read() 之前，
     * 将不再尝试从当前通道读取入站数据。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 如果触发了用户事件，则调用Gets。
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent)evt;
        String eventType = null;
        switch (event.state()){
            case READER_IDLE:
                eventType = "读空闲";
                readIdleTimes ++; // 读空闲的计数加1
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                // 不处理
                break;
            case ALL_IDLE:
                eventType ="读写空闲";
                // 不处理
                break;
        }
        log.error(ctx.channel().remoteAddress() + "超时事件：" +eventType);
        if(readIdleTimes > 3){
            log.error("[server]读空闲超过3次，关闭连接");
            ctx.channel().writeAndFlush(String.format("IP:%s 的用戶,you are out",ctx.channel().localAddress().toString()));
            ctx.channel().close();
        }
    }

    /**
     * 一旦通道的可写状态改变，就被调用。
     * 您可以使用Channel.isWritable() 检查状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    /**
     * 异常如何处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String message = cause.getMessage();
        ctx.writeAndFlush(message);
    }
}
