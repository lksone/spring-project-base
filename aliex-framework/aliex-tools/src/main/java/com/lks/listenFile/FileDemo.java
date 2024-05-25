package com.lks.listenFile;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.util.concurrent.TimeUnit;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2024/1/31 16:31
 */
public class FileDemo {

    public static void main(String[] args) throws Exception {
        //Monitor Directory
        String absolateDir = "D:\\logs";
        //Interval time 5 seconds
        long intervalTime = TimeUnit.SECONDS.toMillis(5);

        //Sample 2:  Create a file Observer to work with file type, eg： Scan the "log" folder under C, and the new file ending with the suffix ".success"
        FileAlterationObserver observer2 = new FileAlterationObserver(absolateDir, FileFilterUtils.and(FileFilterUtils.fileFileFilter(),FileFilterUtils.suffixFileFilter(".success")));

        FileAlterationObserver observer = new FileAlterationObserver(absolateDir);

        //Set file change listener
        observer2.addListener(new FileListener());
        //Create file change monitor,每一个这个就是一个线程
        FileAlterationMonitor monitor = new FileAlterationMonitor(intervalTime, observer2);
        //start monitor
        monitor.start();
    }
}
