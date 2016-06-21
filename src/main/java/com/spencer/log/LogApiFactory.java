package com.spencer.log;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jasshine_xxg on 2016/6/22.
 */
public class LogApiFactory {

    public static ConcurrentHashMap<String, ApiLogger> logClassMap = new ConcurrentHashMap<String, ApiLogger>(1);



    //暂时只支持logback,log4j2种实现方式
    static {
        try {
            Class logbackClass = Class.forName("");
            logClassMap.putIfAbsent("logback", new LogbackApiLogger());
            //适配logback实现
        } catch (ClassNotFoundException e) {
             //木有找到


        }

    }


    /**
     * 需要设计缓存，缓存名字相同的，
     *
     * 缓存key + logpath相同的。即可
     * @param name
     * @return
     */
    public static  Object getLogger(String name) {
        ApiLogger logger = logClassMap.get("");
        return logger.newLogger(

        )
    }


    public Object





}
