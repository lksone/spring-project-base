package com.lks.test.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author lks
 * @description 文件工具类
 * @e-mail 1056224715@qq.com
 * @date 2023/4/7 17:37
 */
public class FileUtils {

    public static String path() {
        //Date date = new Date();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String path = "";
        // if 匹配win else linux
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            path = "D:/usr/local/GHKboard/fmbBoardMonitor/monitor.txt";
        } else {
            path = "/usr/local/GHKboard/fmbBoardMonitor/monitor.txt";
        }
        return path;
    }

    /**
     * 生成文件夹
     *
     * @param path
     */
    public static void createDir(String path) {
        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.setWritable(true, false);
            folder.mkdirs();
        }
    }


    /**
     * 创建文件
     */
    public static void createFile() {
        String path = path();
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String path = path();
        System.out.println(path);

    }
}
