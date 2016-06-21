package com.spencer.log;

/**
 * Created by jasshine_xxg on 2016/6/21.
 */
public abstract class AbstraceApiLogger  implements  ApiLogger{

    protected boolean additivity = true; /**日志是否输出到父appender上*/

    protected int level;

    /**
     * 要输出日志的路径，*
     */
    protected String logpath = "/log/dubbo_framework";

    /**
     * 日志输出的格式
     */
    protected String pattern = "%m%n";

    /**
     *
     */
    protected String enCoding = "UTF-8";

    protected boolean isImmeditlyFlush=true;


    /**
     * 暂时不支持该类型
     * @param objects
     * @return
     */
    public Object getFileAppender(Object... objects) {
        throw new UnsupportedOperationException("can not suport fileAppender for log");
    }

    public Object getStringQueueAppender(Object... objects) {
        throw new UnsupportedOperationException("can not suport StringQueueAppender for log");
    }


    public void setLogpath(String logpath) {
        if (logpath == null || logpath.length() ==  0) {
            return;
        }
        this.logpath = logpath;
    }

    public void setAdditivity(boolean additivity) {
        this.additivity = additivity;
    }
}
