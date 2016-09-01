package tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by clarechen on 16/6/6.
 */
public class LoggerUtils {
    private static Logger logger = LoggerFactory.getLogger("runtimelogger");
    public static void info(String msg){
        logger.info(msg);
    }
    public static void info(String msg,Object... args){
        logger.info(msg,args);
    }
    public static void warn(String msg){
        logger.warn(msg);
    }
    public static void error(String msg,Throwable e){
        logger.error(msg,e);
    }
}
