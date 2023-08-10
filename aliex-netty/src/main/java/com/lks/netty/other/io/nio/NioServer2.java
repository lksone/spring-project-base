package com.lks.netty.other.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lks
 * @description 不适用死循环
 * @e-mail 1056224715@qq.com
 * @date 2023/6/2 11:35
 */
public class NioServer2 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(9000));
        server.configureBlocking(false);
        //创建一个多路复用器
        Selector selector = Selector.open();
        //将这个复用器注册 绑定一个accept事件
        SelectionKey key = server.register(selector, SelectionKey.OP_ACCEPT);


        while(true) {
            //阻塞 等待事件触发
            selector.select();
            //事件触发 拿到所有的事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while(iterator.hasNext()) {
                SelectionKey key1 = iterator.next();
                if(key1.isAcceptable()) {
                    //如果是accept事件 则创建一个channel
                    ServerSocketChannel channel = (ServerSocketChannel) key1.channel();
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    //将这个channel注册到多路复用器 绑定一个读事件
                    accept.register(selector, SelectionKey.OP_READ);
                    System.out.println("connect success");

                } else if(key1.isReadable()) {
                    //触发了读事件
                    //开始读取客户端发来的数据
                    SocketChannel socketChannel = (SocketChannel) key1.channel();
                    //申请一个buffer 可以是堆内的 也可以是直接内存中的(allocateDirect())
                    ByteBuffer buffer = ByteBuffer.allocate(128);
                    //将操作系统中的buffer数据读到java内存里的buffer里
                    int len = socketChannel.read(buffer);
                    if(len > 0) {

                        byte[] bytes = Arrays.copyOfRange(buffer.array(), 0, len);
                        System.out.println("receive " + new String(bytes));
                    } else if(len == -1) {
                        System.out.println("connection closed");
                        socketChannel.close();
                    }
                }
                //将本次事件删除避免下次遍历继续处理
                iterator.remove();
            }
        }
    }
}
