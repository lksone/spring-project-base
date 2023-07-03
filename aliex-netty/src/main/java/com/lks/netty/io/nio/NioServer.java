package com.lks.netty.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lks
 * @description 文件数据信息Nio测试数据信息
 * @e-mail 1056224715@qq.com
 * @date 2023/6/2 10:55
 */
public class NioServer {

    static List<SocketChannel> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //1、开启一个channel,绑定端口号信息
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(9000));
        //设置阻塞信息
        server.configureBlocking(false);

        //2、接收一个客户端数据信息,但是服务器不会一直轮询获取其中客户端获取到数据信息，这种会一直占用资源
        while (true) {
            SocketChannel client = server.accept();
            if (client != null) {
                client.configureBlocking(true);
                //设置该channel非阻塞 使得下面的read方法非阻塞
                clients.add(client);
            }
            //2.1 获取socketChannel通道
            Iterator<SocketChannel> iterator = clients.iterator();
            //轮询所有的客户端
            while (iterator.hasNext()) {
                //读取通道中的数据信息
                SocketChannel channel = iterator.next();
                ByteBuffer buffer = ByteBuffer.allocate(128);
                int len = channel.read(buffer);
                if (len > 0) {
                    System.out.println("receive message :" + len + new String(buffer.array()));
                } else if (len == -1) {
                    iterator.remove();
                    System.out.println("client broken the connection");
                }
            }
        }
    }
}
