package com.lks.netty.day007.file.domain;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/9/18 23:48
 */
public class Constants {


    public static class TransferType{

        public static final Integer INSTRUCT = 1;
        public static final Integer DATA = 2;
    }

    public static class FileStatus {
        public static final Integer COMPLETE = 3;
        public static final Integer END = 2;
        public static final Integer CENTER = 1;
        public static final Integer BEGIN = 0;
    }
}
