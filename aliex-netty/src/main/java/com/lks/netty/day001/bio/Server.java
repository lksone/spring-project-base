package com.lks.netty.day001.bio;

import com.lks.netty.utils.ThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

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

        Executor threadPoolExecutor = new ThreadPool().initPool();
        ServerSocket localhost = new ServerSocket(8888);
        //监听一sokcet请求连接
        while (true) {
            Socket accept = localhost.accept();
            ServerRunable runable = new ServerRunable(accept);
            threadPoolExecutor.execute(runable);
        }
    }
}
