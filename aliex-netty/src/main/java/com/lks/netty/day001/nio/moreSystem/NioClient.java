package com.lks.netty.day001.nio.moreSystem;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author lks
 * @description 客戶端向server中發送消息
 * @e-mail 1056224715@qq.com
 * @date 2023/8/12 13:57
 */
@Slf4j
public class NioClient {

    private Selector selector;
    private SocketChannel socketChannel;
    private String username;
    private static final String IP = "127.0.0.1";
    private static final Integer PORT = 8768;


    public NioClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(IP, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);

            username = socketChannel.getLocalAddress().toString().substring(1);
            log.info("{},当前客户端准备完成...", username);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 读取从服务器端回复的消息
     *
     * @throws IOException
     */
    private void readInfo() throws IOException {
        //读取数据信息
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    sc.read(buffer);
                    System.out.println(new String(buffer.array()).trim());
                }
                iterator.remove();
            }
        }
    }

    /**
     * 向服务器发送消息
     *
     * @param s
     */
    private void sendToServer(String s) {
        s = username + "说:" + s;
        try {
            ByteBuffer wrap = ByteBuffer.wrap(s.getBytes());
            socketChannel.write(wrap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NioClient client = new NioClient();
        //1、讀取服務器發送過來的消息
        new Thread(() -> {
            try {
                client.readInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        //我自己發送的消息
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            client.sendToServer(s);
        }
    }
}
