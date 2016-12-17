/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.sglogger.SGLogger;
import java.io.FileNotFoundException;

/**
 *
 * @author smallgod
 */
public final class LoggerUtil {

    private SGLogger logger;

    public <T> LoggerUtil(final Class<T> loggerClass) {
        
        try {
            
            logger = new SGLogger();
            logger.addLogger(loggerClass);
            
        } catch (Exception ex) {
            System.err.println("Error while initialising logger: " + ex.getMessage());
        }

    }

    public static void configureLog4J(String log4jFile) throws FileNotFoundException, Exception {
        SGLogger.configureLog4J(log4jFile);
    }

    public void debug(String debugLogText) {

        logger.debug(debugLogText);
    }

    public void info(String infoLogText) {

        logger.info(infoLogText);
    }

    public void error(String errorLogText) {

        logger.error(errorLogText);
    }

    public void warn(String warnLogText) {

        logger.warn(warnLogText);
    }

    public void fatal(String fatalLogText) {

        logger.fatal(fatalLogText);
    }

}
