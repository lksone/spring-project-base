package com.lks.common.dataDesensitization;

/**
 * @author lks
 * @description 数据脱敏工具
 * @e-mail 1056224715@qq.com
 * @date 2023/11/12 22:00
 */
public class DesensitizedUtil {
    public static String mobilePhone(String mobile) {
        mobile.replace("1","*");
        mobile.replace("2","*");
        mobile.replace("2","*");
        mobile.replace("3","*");
        mobile.replace("4","*");
        mobile.replace("5","*");
        mobile.replace("6","*");
        return mobile;
    }
}
