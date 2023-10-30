package com.lks.netty.day007.file.util;

import com.lks.netty.day007.file.domain.FileBurstInstruct;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/9/20 23:34
 */
public class CacheUtil {
    public static ConcurrentHashMap<String, FileBurstInstruct> burstDataMap = new ConcurrentHashMap<>();
}
