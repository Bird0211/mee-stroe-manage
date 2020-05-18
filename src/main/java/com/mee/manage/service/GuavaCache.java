package com.mee.manage.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.mee.manage.po.City;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Component
public class GuavaCache {

    private static final Logger logger = LoggerFactory.getLogger(GuavaCache.class);

    public static String STEET_KEY = "steet";

    public static String CITY_KEY = "city";

    private Cache<String, Object> cache;

    @Autowired
    ICityService cityService;

    //用于初始化cache的参数及其缺省值
    private int maximumSize = 1000;                 //最大缓存条数，子类在构造方法中调用setMaximumSize(int size)来更改
    private int expireAfterWriteDuration = 1;      //数据存在时长，子类在构造方法中调用setExpireAfterWriteDuration(int duration)来更改
    // private TimeUnit timeUnit = TimeUnit.DAYS;   //时间单位（分钟）

    private Date resetTime;     //Cache初始化或被重置的时间
    private long highestSize = 0; //历史最高记录数
    private Date highestTime;   //创造历史记录的时间


    /**
     * 通过调用getCache().get(key)来获取数据
     * @return cache
     */
    public Cache<String, Object> getCache() {
        if(cache == null){  //使用双重校验锁保证只有一个cache实例
            synchronized (this) {
                if(cache == null){
                    cache = CacheBuilder.newBuilder().build();
                    logger.debug("本地缓存{}初始化成功", this.getClass().getSimpleName());
                }
            }
        }

        return cache;
    }


    /**
     * 从缓存中获取数据（第一次自动调用fetchData从外部获取数据），并处理异常
     * @param key
     * @return Value
     * @throws ExecutionException
     */
    public Object getValue(String key) throws ExecutionException {
        Object result = getCache().get(key,  new Callable<List<City>>() {
            public List<City> call() throws Exception {
                
                return cityService.list();
            }
        });
        return result;
    }

    public void setValue(String key, Object value) {
        getCache().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public void putValue(String key, String value) throws ExecutionException {
        Object result = getValue(key);
        if(key.equals(STEET_KEY)) {
            if(result == null) {
                result = Lists.newArrayList();
            }
            if(result instanceof List) {
                ((List<String>)result).add(value);
            }
        } else if (key.equals(CITY_KEY)) {
            if(result == null) {
                result = new HashSet<>();
            }
            if (result instanceof Set) {
                ((Set<String>)result).add(value);
            }
        }
        setValue(key, result);
    }

    public long getHighestSize() {
        return highestSize;
    }

    public Date getHighestTime() {
        return highestTime;
    }

    public Date getResetTime() {
        return resetTime;
    }

    public void setResetTime(Date resetTime) {
        this.resetTime = resetTime;
    }

    public int getMaximumSize() {
        return maximumSize;
    }

    public int getExpireAfterWriteDuration() {
        return expireAfterWriteDuration;
    }

    /**
     * 设置最大缓存条数
     * @param maximumSize
     */
    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    /**
     * 设置数据存在时长（分钟）
     * @param expireAfterWriteDuration
     */
    public void setExpireAfterWriteDuration(int expireAfterWriteDuration) {
        this.expireAfterWriteDuration = expireAfterWriteDuration;
    }

}
