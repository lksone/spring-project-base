package com.lks.common.dataDesensitization;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lks
 * @description guava cache的使用
 * @e-mail 1056224715@qq.com
 * @date 2023/11/20 11:07
 */
public class GuavaCache {

    private static Logger logger = LoggerFactory.getLogger(GuavaCache.class);
    /**
     * 一对一
     */
    public static Cache<String, String> cacheStr = CacheBuilder.newBuilder()
            // 并发等级，也可以定义为同时操作缓存的线程数
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            //设置初始化的个数
            .initialCapacity(100)
            //设置初始容量100
            .maximumSize(1000)
            //访问后的过期时间
            .expireAfterAccess(10, TimeUnit.SECONDS)
            //写入后的过期时间
            .expireAfterWrite(10,TimeUnit.SECONDS)
            .build();


    /**
     * 一对多
     */
    public static Cache<String, Map<String,Object>> map = CacheBuilder.newBuilder()
            // 并发等级，也可以定义为同时操作缓存的线程数
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            //设置初始化的个数
            .initialCapacity(100)
            //设置初始容量100
            .maximumSize(1000)
            //访问后的过期时间
            .expireAfterAccess(10, TimeUnit.SECONDS)
            //写入后的过期时间
            .expireAfterWrite(10,TimeUnit.SECONDS)
            .build();


    /**
     * 回源查询
     *
     * @param name
     */
    public Object build(String name){
        LoadingCache<String, Object> build = CacheBuilder.newBuilder()
                // 并发等级，也可以定义为同时操作缓存的线程数
                .concurrencyLevel(Runtime.getRuntime().availableProcessors()).build(new CacheLoader<String, Object>() {
                    /**
                     * 加載，如果沒有就回源，查詢數據庫啥的
                     *
                     * @param key
                     * @return
                     * @throws Exception
                     */
                    @Override
                    public Object load(String key) throws Exception {
                        //todo 這裡就是查詢源數據信息的地方,数据信息
                        logger.info("reload的数据信息：key={}",key);
                        return null;
                    }

                    /**
                     * 監聽數據信息
                     *
                     * @param key
                     * @param oldValue
                     * @return
                     * @throws Exception
                     */
                    @Override
                    public ListenableFuture<Object> reload(String key, Object oldValue) throws Exception {
                        logger.info("reload的数据信息：{}，{}",key, JSON.toJSONString(oldValue));
                        return super.reload(key, oldValue);
                    }
                });
        return build.getIfPresent(name);
    }

    /**
     * 删除某个数据信息
     *
     * @param key
     */
    public void delete(String key){
        cacheStr.invalidate(key);
    }

    /**
     * 清空库
     */
    public void deleteAll(){
        cacheStr.invalidateAll();
    }

    /**
     * 批量删除数据信息
     * @param keyList
     */
    public void deleteList(List<String> keyList){
        cacheStr.invalidateAll(keyList);
    }
}
