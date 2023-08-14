package com.lks.netty.day001.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * @author lks
 * @description client客户端数据信息
 * @e-mail 1056224715@qq.com
 * @date 2023/8/10 23:49
 */
public class NioClient {

    public static void main(String[] args) {
        try {
            SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8765));
            sChannel.configureBlocking(Boolean.FALSE);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String next = scanner.next();
                buffer.put((new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis())
                        + "\n" + next).getBytes());
                buffer.flip();
                sChannel.write(buffer);
                buffer.clear();
            }
            // 关闭通道
            sChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }
}
