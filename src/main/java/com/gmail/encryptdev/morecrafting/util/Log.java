package com.gmail.encryptdev.morecrafting.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by EncryptDev
 */
public class Log {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(Log.class.getCanonicalName());
    }

    /**
     * Log a info message
     * @param message - the logged message
     */
    public static void info(String message) {
        LOGGER.log(Level.INFO, "[MoreCrafting-LOG] " + message);
    }

    /**
     * Log a warning message
     * @param message - the logged message
     */
    public static void warning(String message) {
        LOGGER.log(Level.WARNING, "[MoreCrafting-LOG] " + message);
    }

    /**
     *
     * Log a throwing error
     *
     * @param message - the message for the error
     * @param throwable - the error
     */
    public static void throwError(String message, Throwable throwable) {
        LOGGER.log(Level.SEVERE, message, throwable);
    }

}
