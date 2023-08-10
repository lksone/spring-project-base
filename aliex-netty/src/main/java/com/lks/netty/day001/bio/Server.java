package com.lks.netty.day001.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lks
 * @description 服务器
 * @e-mail 1056224715@qq.com
 * @date 2023/8/10 21:08
 */
@Slf4j
public class Server {


    public static void main(String[] args) throws IOException {
        log.info("服務器啟動--------------------------");
        //定义一个ServerSocket
        ServerSocket localhost = new ServerSocket( 9999);
        Socket accept = localhost.accept();
    }
}
