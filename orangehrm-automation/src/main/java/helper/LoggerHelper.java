package helper;

import com.aventstack.chaintest.plugins.ChainTestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerHelper {

    private static final Logger log = LogManager.getLogger(LoggerHelper.class);

    public static void info(String message) {
        log.info(message);
        ChainTestListener.log(message);
    }

    public static void debug(String message) {
        log.debug(message);
        ChainTestListener.log(message);  // optional for debug
    }

    public static void error(String message) {
        log.error(message);
        ChainTestListener.log("ERROR: " + message);
    }
}
