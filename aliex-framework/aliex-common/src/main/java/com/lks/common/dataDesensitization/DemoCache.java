package com.lks.common.dataDesensitization;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/11/12 21:39
 */
public class DemoCache {

    public static void main(String[] args) {
        // 创建一个缓存实例
        Cache<String, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .build();
        // 向缓存中放入数据
        cache.put("one", 1);
        cache.put("two", 2);
        cache.put("three", 3);
        // 从缓存中获取数据
        Integer value = cache.getIfPresent("two");
        System.out.println(value);
        // 缓存中不存在的数据，会返回null
        Integer nonExistingValue = cache.getIfPresent("four");
        System.out.println(nonExistingValue);

    }
}
