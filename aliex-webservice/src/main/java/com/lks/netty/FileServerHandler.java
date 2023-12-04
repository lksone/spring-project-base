package com.lks.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/12/4 17:47
 */
public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String CR=System.getProperty("line.separator");
    @Override
    protected void messageReceived(ChannelHandlerContext context, String s) throws Exception {
        //通过路径来构造文件
        File file=new File(s);
        //如果文件存在
        if (file.exists()){
            //如果不是文件，是文件夹
            if(!file.isFile()){
                context.writeAndFlush("Not a file : "+file+CR);
                return;
            }
            context.write(file+" "+file.length()+CR);
            //构造只读文件
            RandomAccessFile randomAccessFile=new RandomAccessFile(s,"r");
            //构造netty 的FileRegion对象
            FileRegion region=new DefaultFileRegion(
                    //FileChannel 文件通道，用于对文件进行读写操作
                    randomAccessFile.getChannel(),
                    //0（Position）文件操作的指针位置，读取或写入的起始点。
                    0,
                    //操作的总字节数
                    randomAccessFile.length());

            //实现对文件的发送。由于Netty 底层对文件写入进行了封装，我们不用关心发送的细节。
            context.write(region);
            //写入分割符告诉CMD 控制台，文件传输结束
            context.writeAndFlush(CR);
            randomAccessFile.close();
        }else {
            //文件不存在
            context.writeAndFlush("File not found : "+file+CR);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause){
        cause.printStackTrace();
        context.close();
    }
}
