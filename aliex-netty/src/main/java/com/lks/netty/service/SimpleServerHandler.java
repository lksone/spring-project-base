package com.lks.netty.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.Charset;

/**
 * @author lks
 * @description 处理客户端返回的数据
 * @e-mail 1056224715@qq.com
 * @date 2023/5/26 17:08
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger log = LogManager.getLogger(SimpleServerHandler.class);

    /**
     * 读取客户端通道的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //可以在这里面写一套类似SpringMVC的框架
        //让SimpleServerHandler不跟任何业务有关，可以封装一套框架
        if (msg instanceof ByteBuf) {
            System.out.println(((ByteBuf) msg).toString(Charset.defaultCharset()));
            //业务逻辑代码处理框架。。。
            //返回给客户端的数据，告诉我已经读到你的数据了
            String result = "hello client ";
            ByteBuf buf = Unpooled.buffer();
            buf.writeBytes(result.getBytes());
            ctx.channel().writeAndFlush(buf);

            ByteBuf buf2 = Unpooled.buffer();
            buf2.writeBytes("\r\n".getBytes());
            ctx.channel().writeAndFlush(buf2);
            log.info("----------------------------------");
        }

    }
}
