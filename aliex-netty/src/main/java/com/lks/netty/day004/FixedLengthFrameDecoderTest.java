package com.lks.netty.day004;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/8/16 17:33
 */
@Slf4j
public class FixedLengthFrameDecoderTest {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        //向channel中写入2个字节
        log.info("第一次："+channel.writeInbound(input.readBytes(4)));
        log.info("第二次："+channel.writeInbound(input.readBytes(5)));
        log.info("是否完成"+channel.finish());

        ByteBuf read = (ByteBuf) channel.readInbound();
        log.info(""+buf.readSlice(2).equals(read));
        read.release();
    }
}
