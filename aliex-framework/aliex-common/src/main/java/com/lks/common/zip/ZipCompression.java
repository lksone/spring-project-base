package com.lks.common.zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author lks
 * @description 多文件压缩
 * @e-mail 1056224715@qq.com
 * @date 2023/11/5 22:36
 */
public class ZipCompression {

    private final static Logger logger = LoggerFactory.getLogger(ZipCompression.class);

    private ZipCompression() {
    }


    /**
     * 将多个文件压缩到指定输出流中
     *
     *
     * @param files             需要压缩的文件列表
     * @param outputStream      压缩到指定的输出流
     */
    public static void compressZip(List<File> files, OutputStream outputStream){
        ZipOutputStream zipOutStream = null;
        FileInputStream filenputStream = null;
        try {
            //-- 包装成ZIP格式输出流
            zipOutStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
            // -- 设置压缩方法
            zipOutStream.setMethod(ZipOutputStream.DEFLATED);
            //-- 将多文件循环写入压缩包
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                 filenputStream = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                filenputStream.read(data);
                //-- 添加ZipEntry，并ZipEntry中写入文件流，这里，加上i是防止要下载的文件有重名的导致下载失败
                zipOutStream.putNextEntry(new ZipEntry(i + file.getName()));
                zipOutStream.write(data);
                zipOutStream.flush();

                filenputStream.close();
                zipOutStream.closeEntry();
            }
        } catch (IOException e) {
            logger.error("error,msg:{}", e.getMessage());
        } finally {
            close(filenputStream);
            close(zipOutStream);
            close(outputStream);
        }
    }

    /**
     * 功能:压缩多个文件成一个zip文件
     *
     * @param srcfile：源文件列表
     * @param zipfile：压缩后的文件
     */
    public static void zipFiles(File[] srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));){
            //ZipOutputStream类：完成文件或文件夹的压缩
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = null;
                try {
                    in = new FileInputStream(srcfile[i]);
                    out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    out.closeEntry();
                    close(in);
                }
            }
            out.close();
            System.out.println("压缩完成.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 解壓文件
     * @param zipFile
     * @param descDir
     */
    public static void unZipFiles(File zipFile, String descDir) {
        InputStream in = null;
        OutputStream out = null;
        try {
            ZipFile zf = new ZipFile(zipFile);
            for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                 in = zf.getInputStream(entry);
                 out = new FileOutputStream(descDir + zipEntryName);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
                logger.info("解压缩完成.");
            }
        } catch (Exception e) {
            logger.error("io 异常",e);
        }finally {
            close(in);
            close(out);
        }
    }



    /**
     * 关闭
     *
     * @param closeable
     */
    public static void close(Closeable closeable){
        if(closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                logger.error("close io 异常",e);
            }
        }
    }
}
