package com.lks.netty.day001.nio.moreSystem;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author lks
 * @description server服務器
 * @e-mail 1056224715@qq.com
 * @date 2023/8/12 13:56
 */
@Slf4j
public class NioServer {

    /**
     * 多路服用器
     */
    private Selector selector;

    /**
     * 服務channel
     */
    private ServerSocketChannel serverSocketChannel;

    private static final int  PORT = 8768;

    public NioServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 監聽創建的接口PORT
     */
    public void listen(){
        try {
            log.info("開始進行監聽狀態--------------------------------------");
            while (selector.select()>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().stream().iterator();
                while (iterator.hasNext()){
                    //獲取selectionKey的數據類型，accepte
                    SelectionKey selectionKey = iterator.next();
                    //
                    if(selectionKey.isAcceptable()){
                        log.info("進入了acceptable狀態");
                        SocketChannel sChannel = serverSocketChannel.accept();
                        sChannel.configureBlocking(false);
                        System.out.println(sChannel.getRemoteAddress() + "上线");
                        sChannel.register(selector, SelectionKey.OP_READ);
                    }else if(selectionKey.isReadable()){
                        readClientData(selectionKey);
                    }else if(selectionKey.isConnectable()){
                        System.out.println("创建连接种-----------------------------");
                    }else if(selectionKey.isValid()){
                        System.out.println("校验-----------------------------");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 從鏈接中讀取數據信息
     * @param selectionKey
     */
    private void readClientData(SelectionKey selectionKey) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel)selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            while (read >0){
                buffer.flip();
                String msg = new String(buffer.array(), 0, buffer.remaining());
                System.out.println("接收到了客户端消息:" + msg);
                // 向其它的客户端转发消息(去掉自己)
                this.sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                System.out.println(channel.getRemoteAddress() + "离线了..");
                selectionKey.cancel();
                channel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 向其他客户端发送信息
     *
     * @param msg
     * @param sChannel
     * @throws IOException
     */
    private void sendInfoToOtherClients(String msg, SocketChannel sChannel) throws IOException {
        System.out.println("服务器转发消息中...");
        // 遍历所有注册到selector上的SocketChannel并排除sChannel
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != sChannel) {
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        NioServer server = new NioServer();
        server.listen();
    }
}
