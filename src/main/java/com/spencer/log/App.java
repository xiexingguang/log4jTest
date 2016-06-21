package com.spencer.log;

import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Hierarchy;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Vector;


/**
 * Hello world!
 *
 */
public class App 
{


    public static void main(String[] args) throws Exception {
        //  Logger logger = LogManager.getLogger(App.class);
        //   org.slf4j.Logger LOG = LogManager.getLogger(App.class);
        //   logger.debug("hello world");
        //System.out.println(LoggerFactory.getLogger(App.class));
        // Logger logger = org.slf4j.LoggerFactory.getLogger(App.class);
     /*   Class<?> staticLoggerBinderClass = Class.forName("org.slf4j.impl.StaticLoggerBinder");
        //Class<?> staticLogger = Class.forName("com.specner");
        com.alibaba.dubbo.common.logger.Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("so many");
        Logger logger1 = org.slf4j.LoggerFactory.getLogger(App.class);
        logger1.info("{} hello ,world","xxg");*/


        org.apache.log4j.Logger logger = LogManager.getRootLogger();
        logger.info("new to print log");

        Enumeration<Appender> appenderEnumeration = logger.getAllAppenders();

        while (appenderEnumeration.hasMoreElements()) {
            Appender appender = appenderEnumeration.nextElement();
            System.out.println("append-Name :" + appender.getName());
            System.out.println("append-class :" + appender.getClass());
            if (appender.getClass().isAssignableFrom(FileAppender.class)) {
                FileAppender fileAppender = (FileAppender) appender;
                System.out.println("file-path :" + fileAppender.getFile());
            }


            if (FileAppender.class.isAssignableFrom(appender.getClass())) { //表示FileAppender是否是 appender.getClass 的父类
                FileAppender fileAppender = (FileAppender) appender;
                System.out.println("file-path :" + fileAppender.getFile());
            }
        }

       // getLog4jAppenderFileName();
        System.out.println("===========================start ===============================");

      Object customLogger =  createLog4jInstance("App.name");
        System.out.println(customLogger.getClass().getName());

        org.apache.log4j.Logger logger1 = (org.apache.log4j.Logger) customLogger;
        logger1.info("this is my test for info");
        logger1.debug("this is my test for debug");
        logger1.warn("this is my test warn");
        logger1.error("this is my test error");

    }


    //public  Logger 通过反射 获取log4j用户所在的目录文件

    public static String getLog4jAppenderFileName() throws  Exception {


        Class<?> log4jClass = Class.forName("org.apache.log4j.Logger");

        System.out.println("log4j type ===>" + log4jClass.getName());


         //获取logManagerClass
        Class<?> logManagerClass = Class.forName("org.apache.log4j.LogManager");

        System.out.println("logManageClass type === >" + logManagerClass.getName());

       //获取LoggerRepository

        Method getLoggerRepository = logManagerClass.getDeclaredMethod("getLoggerRepository");

        Object reposityObjet = getLoggerRepository.invoke(logManagerClass);


        System.out.println("repsity type : " + reposityObjet.getClass().getName());

        Class<?> reposityClass = Class.forName("org.apache.log4j.spi.LoggerRepository");

       //获取getRootLogger方法
        Method getRootLogger = reposityClass.getDeclaredMethod("getRootLogger");


        //反射获取log4j对象
        Object log4jObjcet = getRootLogger.invoke(reposityObjet);


        org.apache.log4j.Logger logger = (org.apache.log4j.Logger) log4jObjcet;

        logger.info("this is reflect test");


        //获取appender，然后获取fileAppender方式


        //获取log4jclass 的父类文件
        Class<?> category = log4jClass.getSuperclass();
        System.out.println("category type is  ===== > " + category.getName());

        //有问题？为毛反射获取的类型，跟实际类型不一致？
        Method getAllAppenders = category.getDeclaredMethod("getAllAppenders");
        Object allAppendes = getAllAppenders.invoke(log4jObjcet);
        System.out.println(allAppendes.getClass().getName());

        Enumeration<Appender> eeAppeners = (Enumeration<Appender>) allAppendes;

        System.out.println("===============>" + eeAppeners);




        Field aai = category.getDeclaredField("aai");  //为什么用子类的calss 字节码不能获取？即log4jClass
        aai.setAccessible(true);

        Object AppenderAttachableImpl = aai.get(log4jObjcet); //得到aai的实际实现类
        System.out.println(AppenderAttachableImpl);

        Class<?> appenderAttachClass = Class.forName("org.apache.log4j.helpers.AppenderAttachableImpl");

        Method getAllAppenderss = appenderAttachClass.getDeclaredMethod("getAllAppenders");
        getAllAppenders.setAccessible(true);
        Object appenders = getAllAppenderss.invoke(AppenderAttachableImpl);
        System.out.println("appenders type is :" + appenders.getClass().getName());
        System.out.println(AppenderAttachableImpl.getClass().getName());

        Enumeration<Appender> eAppeners = (Enumeration<Appender>) appenders;

        System.out.println(eAppeners);

        while (eAppeners.hasMoreElements()) {
            Appender appender = eAppeners.nextElement();
            if (FileAppender.class.isAssignableFrom(appender.getClass())) {
                FileAppender fileAppender = (FileAppender) appender;
                System.out.println("file - name  :" + ((FileAppender) appender).getFile());
            }

        }

        //Vector vAppenders = (Vector) appenders;
       // System.out.println(vAppenders.size());

        //反射获取不到
        return null;
    }


    //通过反射获取到log4j实例
    public static Object createLog4jInstance(String name) throws  Exception{

        Class<?> log4jClass = Class.forName("org.apache.log4j.Logger");

        Constructor log4jConstruct = log4jClass.getDeclaredConstructor(new Class<?>[]{String.class});

        log4jConstruct.setAccessible(true);

        Object log4jObjcet = log4jConstruct.newInstance(name); //获取到新的log4jObjcet实例了


        //反射set level

        Class<?> levelClass = Class.forName("org.apache.log4j.Level");

        Constructor levcelClassConstructor = levelClass.getDeclaredConstructor(new Class<?>[]{int.class, String.class, int.class});
        levcelClassConstructor.setAccessible(true);
        Object levenInstance_Info = levcelClassConstructor.newInstance(20000, "INFO", 6);
        Object levenInstance_debug1 = levcelClassConstructor.newInstance(10000, "DEBUG", 7);
        Method setLevelMethod = log4jClass.getSuperclass().getDeclaredMethod("setLevel", levelClass);

        setLevelMethod.invoke(log4jObjcet, levenInstance_Info); //反射向log4j设置level为inof级别

     //反射设置Additivity 表示子log是否将日志输出到父类的appender上。 ture表示是

        Method setAdditivityMethod = log4jClass.getSuperclass().getDeclaredMethod("setAdditivity", boolean.class);

        setAdditivityMethod.invoke(log4jObjcet, true); //设置addditve


        //构造rootlogger对象

        Class<?> rootLoggerClass = Class.forName("org.apache.log4j.spi.RootLogger");

        //构造level对象

        Object levenInstance_debug = levcelClassConstructor.newInstance(10000, "DEBUG", 7);

        Constructor rootLoggerConstruct = rootLoggerClass.getDeclaredConstructor(new Class<?>[]{levelClass});

        Object RootloggerObjcet = rootLoggerConstruct.newInstance(levenInstance_debug);


        //appender 仓库

        Class<?> hierarchyClass = Class.forName("org.apache.log4j.Hierarchy");

        Constructor hierarchyConstruct = hierarchyClass.getDeclaredConstructor(log4jClass);

        Object hierarchyObjcet = hierarchyConstruct.newInstance(RootloggerObjcet);


        //设置遗传

        Class<?> reposityClass = Class.forName("org.apache.log4j.spi.LoggerRepository");

        Method setHirerarchyMethod = log4jClass.getSuperclass().getDeclaredMethod("setHierarchy", new Class<?>[]{reposityClass}); //参数为子类还不行？
        setHirerarchyMethod.setAccessible(true);
        setHirerarchyMethod.invoke(log4jObjcet, hierarchyObjcet);

      //添加appender

        Class<?> appenerClass = Class.forName("org.apache.log4j.Appender");

        Method addAppender = log4jClass.getSuperclass().getDeclaredMethod("addAppender", appenerClass);

        Object consoleAppender = getConsoleAppender();
        Object dailyFileAppender = getDailyRollingFileAppender();
        addAppender.invoke(RootloggerObjcet, consoleAppender);
        addAppender.invoke(RootloggerObjcet, dailyFileAppender);

        Method updateParntsMethod = hierarchyClass.getDeclaredMethod("updateParents", new Class<?>[]{log4jClass});

        updateParntsMethod.setAccessible(true);
        updateParntsMethod.invoke(hierarchyObjcet, log4jObjcet);

        return log4jObjcet;

    }


    private static Object getConsoleAppender() throws Exception{

        Object object;

        Class<?> patterLayOutClass = Class.forName("org.apache.log4j.PatternLayout");

        Object layoutObjcet = patterLayOutClass.newInstance();

        Method setConversionPatternMethod = patterLayOutClass.getDeclaredMethod("setConversionPattern", new Class[]{String.class});

        setConversionPatternMethod.invoke(layoutObjcet, "%m%n");


        Class<?> consoleAppenderClass = Class.forName("org.apache.log4j.ConsoleAppender");

        Class<?> layoutClass = Class.forName("org.apache.log4j.Layout");
        Constructor consoleAppenderCons = consoleAppenderClass.getDeclaredConstructor(new Class<?>[]{layoutClass});
        Object consoleObject = consoleAppenderCons.newInstance(layoutObjcet);


        Method setEncodingMethod = consoleAppenderClass.getSuperclass().getDeclaredMethod("setEncoding", new Class[]{String.class});
        setEncodingMethod.setAccessible(true);
        setEncodingMethod.invoke(consoleObject, "UTF-8");

        Method setImmediateFlushMethod = consoleAppenderClass.getSuperclass().getDeclaredMethod("setImmediateFlush", boolean.class);
        setImmediateFlushMethod.invoke(consoleObject, true); //默认为true

        return consoleObject;
    }

    private static Object getDailyRollingFileAppender() throws  Exception{
        Object o = null;

        Class<?> patterLayOutClass = Class.forName("org.apache.log4j.PatternLayout");

        Object layoutObjcet = patterLayOutClass.newInstance();

        Method setConversionPatternMethod = patterLayOutClass.getDeclaredMethod("setConversionPattern", new Class[]{String.class});

        setConversionPatternMethod.invoke(layoutObjcet, "%m%n");


        Class<?> dailyRollingClass = Class.forName("org.apache.log4j.DailyRollingFileAppender");
        Class<?> layoutClass = Class.forName("org.apache.log4j.Layout");
        Constructor dailyRollingConstruct = dailyRollingClass.getDeclaredConstructor(new Class<?>[]{layoutClass, String.class, String.class});

        Object dailyRollingObjcet = dailyRollingConstruct.newInstance(layoutObjcet, "d://testlog4j.log", "'_'yyyyMMdd'.log'");


        Method setEncodingMethod = dailyRollingClass.getSuperclass().getSuperclass().getDeclaredMethod("setEncoding", new Class[]{String.class});
        setEncodingMethod.setAccessible(true);
        setEncodingMethod.invoke(dailyRollingObjcet, "UTF-8");

        Method setImmediateFlushMethod = dailyRollingClass.getSuperclass().getSuperclass().getDeclaredMethod("setImmediateFlush", boolean.class);
        setImmediateFlushMethod.invoke(dailyRollingObjcet, true); //默认为true


        return dailyRollingObjcet;

    }



}

