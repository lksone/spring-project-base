package com.lks.netty.day001.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author lks
 * @description 客户端，监听数据信息，可以通过Netty的方式去获取文件信息
 * @e-mail 1056224715@qq.com
 * @date 2023/8/10 23:48
 */
public class NioServer {

    public static void main(String[] args) {
        try {
            //1、客户单链接服务器时候会获取channel
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            //2、设置非阻塞模式
            socketChannel.configureBlocking(false);
           // 3）绑定连接
            socketChannel.bind(new InetSocketAddress(8765));
            //获取选择器
            Selector selector = Selector.open();
            //5、将通道注册到选择器上
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //6、6）轮询式的获取选择器上已经准备就绪的事件

            //// 轮询式的获取选择器上已经准备就绪的事件
            while (selector.select()>0){
                // 7)获取当前选择器中所有注册的选择键(已就绪的监听事件)
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    // 8)获取准备就绪的是事件
                    SelectionKey sk = it.next();
                    // 9)判断具体是什么事件准备就绪
                    if (sk.isAcceptable()) {
                        // 10)若接收就绪,获取客户端连接
                        java.nio.channels.SocketChannel sChannel = socketChannel.accept();
                        //11)切换非阻塞模式
                        sChannel.configureBlocking(false);
                        // 12)将该通道注册到选择器上
                        sChannel.register(selector, SelectionKey.OP_READ);
                    } else if (sk.isReadable()) {
                        // 13)获取当前选择器上读就绪状态的通道
                        SocketChannel sChannel = (SocketChannel) sk.channel();
                        // 14)读取数据
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int len = 0;
                        while ((len = sChannel.read(buf)) > 0) {
                            buf.flip();
                            System.out.println(new String(buf.array(), 0, len));
                            buf.clear();
                        }
                    }
                    // 15)取消选择键SelectionKey
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
