package com.lks.netty.day001.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author lks
 * @description 客户端
 * @e-mail 1056224715@qq.com
 * @date 2023/8/10 21:08
 */
@Slf4j
public class Client {

    public static void main(String[] args)  {
        log.info("客戶達啟動------------------------");
        //1、創建一個Socet的監聽時間
        try {
            Socket localhost = new Socket("127.0.0.1", 8888);
            OutputStream outputStream = localhost.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("请说:");
                String msg = sc.nextLine();
                printWriter.println(msg);
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
