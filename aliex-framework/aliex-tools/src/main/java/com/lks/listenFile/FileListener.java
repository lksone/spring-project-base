package com.lks.listenFile;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * @author lks
 * @description 文件监听器
 * @e-mail 1056224715@qq.com
 * @date 2024/1/31 16:23
 */
public class FileListener extends FileAlterationListenerAdaptor {

    /**
     * Directory changed Event.
     *
     * @param directory The directory changed (ignored)
     */
    @Override
    public void onDirectoryChange(File directory) {
        System.out.println("The directory changed ,file={}" + directory.getAbsolutePath());
    }

    /**
     * Directory created Event.
     *
     * @param directory The directory created (ignored)
     */
    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("The directory created ,file={}" + directory.getAbsolutePath());
    }

    /**
     * Directory deleted Event.
     *
     * @param directory The directory deleted (ignored)
     */
    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("The directory deleted +file={}" + directory.getAbsolutePath());
    }

    /**
     * File changed Event.
     *
     * @param file The file changed (ignored)
     */
    @Override
    public void onFileChange(File file) {
        System.out.println("The file changed +file={}" + file.getAbsolutePath());
    }

    /**
     * File created Event.
     *
     * @param file The file created (ignored)
     */
    @Override
    public void onFileCreate(File file) {
        System.out.println("The file created +file={}" + file.getAbsolutePath());
    }

    /**
     * File deleted Event.
     *
     * @param file The file deleted (ignored)
     */
    @Override
    public void onFileDelete(File file) {
        System.out.println("The file deleted +file={}" + file.getAbsolutePath());
    }

    /**
     * File System observer started checking event.
     *
     * @param observer The file System observer (ignored)
     */
    @Override
    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
    }

    /**
     * File System observer finished checking event.
     *
     * @param observer The file System observer (ignored)
     */
    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }
}
