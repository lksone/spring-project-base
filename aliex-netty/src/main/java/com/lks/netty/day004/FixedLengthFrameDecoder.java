package com.lks.netty.day004;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author lks
 * @description
 *
 * <p>
 *     有足够的数据可以读取时将产生固定大小的包，如果没有足够的数据可以读取，则会等待下一个数据块并再次检查是否可以产生一个完整包
 * </p>
 * @e-mail 1056224715@qq.com
 * @date 2023/8/16 17:30
 */
@Slf4j
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {

    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException("frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //返回等于 (此.writerIndex-此.readerIndex) 的可读字节数。
        int readableBytes = in.readableBytes();
        log.info("讀取到的數據長度：length:{}",readableBytes);
        if(readableBytes>=frameLength){
            ByteBuf buf = in.readBytes(frameLength);
            out.add(buf);
        }
    }
}
