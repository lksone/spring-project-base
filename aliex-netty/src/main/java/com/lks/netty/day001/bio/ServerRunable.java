package com.lks.netty.day001.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author lks
 * @description 线程打印数据
 * @e-mail 1056224715@qq.com
 * @date 2023/8/10 23:39
 */
public class ServerRunable implements Runnable {

    private Socket socket;

    public ServerRunable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            String msg = null;
            while ((msg = bf.readLine()) != null) {
                System.out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
