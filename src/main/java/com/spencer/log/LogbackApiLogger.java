package com.spencer.log;

/**
 * Created by jasshine_xxg on 2016/6/22.
 */
public class LogbackApiLogger extends AbstraceApiLogger {


    public Object newLogger(int level, String path, String charset, String pattern, String loggName, Boolean isFush) {
        return null;
    }

    public String getFilePathFromUserLogPath() {
        return null;
    }

    public Object getConsoleAppender(Object... objects) {
        return null;
    }

    public Object getRollingFileAppender(Object... objects) {
        return null;
    }
}
