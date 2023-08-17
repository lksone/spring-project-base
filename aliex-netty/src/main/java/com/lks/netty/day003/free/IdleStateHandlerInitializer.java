package com.lks.netty.day003.free;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 * @author lks
 * @description 空闲连接和超时
 * <p>
 *     检测空闲连接和超时对于及时释放资源至关重要，Netty提供了几个处理器进行处理，
 *     如IdleStateHandler、ReadTimeoutHandler、WriteTimeoutHandler等。
 * 　　 首先看看IdleStateHandler的原因，如果60S内没有接受或发送数据，
 *     如何接收通知，可以使用向远程对等体发送心跳消息来获取通知;
 *     如果没有响应，则连接被关闭。
 * </p>
 * @e-mail 1056224715@qq.com
 * @date 2023/8/16 10:36
 */
@Slf4j
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
        pipeline.addLast(new HeartbeatHandler());
    }

    /**
     * 心跳檢查
     * <P>
     *     IdleStateHandler 将通过 IdleStateEvent 调用 userEventTriggered ，如果连接没有接收或发送数据超过60秒钟
     *     心跳发送到远端
     *     发送的心跳并添加一个侦听器，如果发送操作失败将关闭连接
     *     事件不是一个 IdleStateEvent 的话，就将它传递给下一个处理程序
     * </P>
     */
    public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter  {

        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
