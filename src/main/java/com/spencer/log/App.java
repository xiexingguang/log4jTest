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


            if (FileAppender.class.isAssignableFrom(appender.getClass())) { //��ʾFileAppender�Ƿ��� appender.getClass �ĸ���
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


    //public  Logger ͨ������ ��ȡlog4j�û����ڵ�Ŀ¼�ļ�

    public static String getLog4jAppenderFileName() throws  Exception {


        Class<?> log4jClass = Class.forName("org.apache.log4j.Logger");

        System.out.println("log4j type ===>" + log4jClass.getName());


         //��ȡlogManagerClass
        Class<?> logManagerClass = Class.forName("org.apache.log4j.LogManager");

        System.out.println("logManageClass type === >" + logManagerClass.getName());

       //��ȡLoggerRepository

        Method getLoggerRepository = logManagerClass.getDeclaredMethod("getLoggerRepository");

        Object reposityObjet = getLoggerRepository.invoke(logManagerClass);


        System.out.println("repsity type : " + reposityObjet.getClass().getName());

        Class<?> reposityClass = Class.forName("org.apache.log4j.spi.LoggerRepository");

       //��ȡgetRootLogger����
        Method getRootLogger = reposityClass.getDeclaredMethod("getRootLogger");


        //�����ȡlog4j����
        Object log4jObjcet = getRootLogger.invoke(reposityObjet);


        org.apache.log4j.Logger logger = (org.apache.log4j.Logger) log4jObjcet;

        logger.info("this is reflect test");


        //��ȡappender��Ȼ���ȡfileAppender��ʽ


        //��ȡlog4jclass �ĸ����ļ�
        Class<?> category = log4jClass.getSuperclass();
        System.out.println("category type is  ===== > " + category.getName());

        //�����⣿Ϊë�����ȡ�����ͣ���ʵ�����Ͳ�һ�£�
        Method getAllAppenders = category.getDeclaredMethod("getAllAppenders");
        Object allAppendes = getAllAppenders.invoke(log4jObjcet);
        System.out.println(allAppendes.getClass().getName());

        Enumeration<Appender> eeAppeners = (Enumeration<Appender>) allAppendes;

        System.out.println("===============>" + eeAppeners);




        Field aai = category.getDeclaredField("aai");  //Ϊʲô�������calss �ֽ��벻�ܻ�ȡ����log4jClass
        aai.setAccessible(true);

        Object AppenderAttachableImpl = aai.get(log4jObjcet); //�õ�aai��ʵ��ʵ����
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

        //�����ȡ����
        return null;
    }


    //ͨ�������ȡ��log4jʵ��
    public static Object createLog4jInstance(String name) throws  Exception{

        Class<?> log4jClass = Class.forName("org.apache.log4j.Logger");

        Constructor log4jConstruct = log4jClass.getDeclaredConstructor(new Class<?>[]{String.class});

        log4jConstruct.setAccessible(true);

        Object log4jObjcet = log4jConstruct.newInstance(name); //��ȡ���µ�log4jObjcetʵ����


        //����set level

        Class<?> levelClass = Class.forName("org.apache.log4j.Level");

        Constructor levcelClassConstructor = levelClass.getDeclaredConstructor(new Class<?>[]{int.class, String.class, int.class});
        levcelClassConstructor.setAccessible(true);
        Object levenInstance_Info = levcelClassConstructor.newInstance(20000, "INFO", 6);
        Object levenInstance_debug1 = levcelClassConstructor.newInstance(10000, "DEBUG", 7);
        Method setLevelMethod = log4jClass.getSuperclass().getDeclaredMethod("setLevel", levelClass);

        setLevelMethod.invoke(log4jObjcet, levenInstance_Info); //������log4j����levelΪinof����

     //��������Additivity ��ʾ��log�Ƿ���־����������appender�ϡ� ture��ʾ��

        Method setAdditivityMethod = log4jClass.getSuperclass().getDeclaredMethod("setAdditivity", boolean.class);

        setAdditivityMethod.invoke(log4jObjcet, true); //����addditve


        //����rootlogger����

        Class<?> rootLoggerClass = Class.forName("org.apache.log4j.spi.RootLogger");

        //����level����

        Object levenInstance_debug = levcelClassConstructor.newInstance(10000, "DEBUG", 7);

        Constructor rootLoggerConstruct = rootLoggerClass.getDeclaredConstructor(new Class<?>[]{levelClass});

        Object RootloggerObjcet = rootLoggerConstruct.newInstance(levenInstance_debug);


        //appender �ֿ�

        Class<?> hierarchyClass = Class.forName("org.apache.log4j.Hierarchy");

        Constructor hierarchyConstruct = hierarchyClass.getDeclaredConstructor(log4jClass);

        Object hierarchyObjcet = hierarchyConstruct.newInstance(RootloggerObjcet);


        //�����Ŵ�

        Class<?> reposityClass = Class.forName("org.apache.log4j.spi.LoggerRepository");

        Method setHirerarchyMethod = log4jClass.getSuperclass().getDeclaredMethod("setHierarchy", new Class<?>[]{reposityClass}); //����Ϊ���໹���У�
        setHirerarchyMethod.setAccessible(true);
        setHirerarchyMethod.invoke(log4jObjcet, hierarchyObjcet);

      //���appender

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
        setImmediateFlushMethod.invoke(consoleObject, true); //Ĭ��Ϊtrue

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
        setImmediateFlushMethod.invoke(dailyRollingObjcet, true); //Ĭ��Ϊtrue


        return dailyRollingObjcet;

    }



}

