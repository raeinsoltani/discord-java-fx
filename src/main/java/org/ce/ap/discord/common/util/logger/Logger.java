package org.ce.ap.discord.common.util.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Parham Ahmady
 * @since 14/3/2022
 */
public class Logger {

    private static final String INFO_SIGNATURE = "[INFO]";
    private static final String WARN_SIGNATURE = "[WARN]";
    private static final String ERROR_SIGNATURE = "[ERROR]";

    private final String className;

    /**
     * Private constructor to prevent instantiation from outside the class
     *
     * @param className the name of the class that is using this logger
     */
    private Logger(String className) {
        this.className = className;
    }

    /**
     * This method is used to log a warning message
     *
     * @param message the message to be logged
     * @param args    the arguments to be replaced in the message
     */
    public void warn(String message, String... args) {
        System.out.println(buildLog(message, args, WARN_SIGNATURE));
    }

    /**
     * This method is used to log an info message
     *
     * @param message the message to be logged
     * @param args    the arguments to be replaced in the message
     */
    public void info(String message, String... args) {
        System.out.println(buildLog(message, args, INFO_SIGNATURE));
    }

    /**
     * This method is used to log an error message
     *
     * @param message the message to be logged
     * @param args    the arguments to be replaced in the message
     */
    public void error(String message, String... args) {
        System.out.println(buildLog(message, args, ERROR_SIGNATURE));
    }

    private String buildMessage(String message, String... args) {
        if (args == null || args.length == 0)
            return message;
        for (String arg : args) {
            int regexIndex = message.indexOf("{}");
            if (regexIndex < 0)
                return message;
            message = message.substring(0, regexIndex) + arg + message.substring(regexIndex + 2);
        }
        return message;
    }

    @SuppressWarnings("StringBufferReplaceableByString") //just to make it look better in intellij IDEA
    private String buildLog(String message, String[] args, String signature) {
        // stringBuilder is used to build the log message, there are many ways to do this
        StringBuilder builder = new StringBuilder(getCurrentDate())
                .append(" ")
                .append(signature)
                .append(" ")
                .append(className)
                .append(" \n\t")
                .append(buildMessage(message, args));
        return builder.toString();
    }

    private String getCurrentDate() {
        //this is an easy way to get the current date and time, there are many ways to do this
        return new SimpleDateFormat("yyy-MM-dd' 'hh:mm:ss:SS").format(new Date());
    }

    /**
     * This method is used to create a new logger instance (Called Factory Method)
     *
     * @param className the name of the class that is using this logger
     * @return the logger instance
     */
    public static Logger getLogger(String className) {
        if (className != null && !className.isEmpty())
            return new Logger(className);
        return null;
    }
}

