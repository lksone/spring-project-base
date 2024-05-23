package com.lks.netty.day007.file.client;

import com.lks.netty.day007.file.domain.Constants;
import com.lks.netty.day007.file.domain.FileBurstData;
import com.lks.netty.day007.file.domain.FileBurstInstruct;
import com.lks.netty.day007.file.domain.FileTransferProtocol;
import com.lks.netty.day007.file.util.FileUtil;
import com.lks.netty.day007.file.util.MsgUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lks
 * @description客户端
 * @e-mail 1056224715@qq.com
 * @date 2023/9/18 23:49
 */
@Slf4j
public class FileClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //数据格式验证
        if (!(msg instanceof FileTransferProtocol)) return;

        FileTransferProtocol fileTransferProtocol = (FileTransferProtocol) msg;
        //0传输文件'请求'、1文件传输'指令'、2文件传输'数据'
        switch (fileTransferProtocol.getTransferType()) {
            case 1:
                FileBurstInstruct fileBurstInstruct = (FileBurstInstruct) fileTransferProtocol.getTransferObj();
                //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
                if (Constants.FileStatus.COMPLETE.equals(fileBurstInstruct.getStatus())) {
                    ctx.flush();
                    ctx.close();
                    System.exit(-1);
                    return;
                }
                FileBurstData fileBurstData = FileUtil.readFile(fileBurstInstruct.getClientFileUrl(), fileBurstInstruct.getReadPosition());
                ctx.writeAndFlush(MsgUtil.buildTransferData(fileBurstData));
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " bugstack虫洞栈客户端传输文件信息。 FILE：" + fileBurstData.getFileName() + " SIZE(byte)：" + (fileBurstData.getEndPos() - fileBurstData.getBeginPos()));
                break;
            default:
                break;
        }
        //模拟传输过程中断，场景测试可以注释掉
        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " bugstack虫洞栈客户端传输文件信息[主动断开链接，模拟断点续传]");
        ctx.flush();
        ctx.close();
    }

    /**
     * 異常如何處理，直接拋出異常並且打印日誌
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
