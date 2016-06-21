package com.spencer.log;

/**
 * Created by jasshine_xxg on 2016/6/21.
 */
public interface ApiLogger {


    Object newLogger(int level, String path, String charset, String pattern, String loggName,Boolean isFush);

    String getFilePathFromUserLogPath();

    Object getConsoleAppender(Object ...objects);

    Object getRollingFileAppender(Object... objects);

    Object getFileAppender(Object... objects);

    Object getStringQueueAppender(Object... objects);

}
