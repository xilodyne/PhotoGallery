package xilodyne.photo_gallery.globals;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/* call this class first to init all variables */

public class Logging {
	
	
	//LogManager.getRootLogger().setLevel(Level.DEBUG);
	
 //   public static final Logger logger = LogManager.getLogger(Gallery.class.getName());
//    private static  Logger logger = LogManager.getLogger("undefined");
    private static  Logger logger = org.apache.logging.log4j.LogManager.getLogger("undefined");
 
    private static LoggerContext ctx;
    private static LoggerConfig loggerConfig;
//    private static Level level;
    
    public Logging () {
//		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Logging.setClassName(this.getClass().getName());

		ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
//		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
		loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
		
		//set to all until Globals debug check is done
//		Logging.level = loggerConfig.getLevel();
		Logging.info(this.getClass().getName(), "log4j level: " + loggerConfig.getLevel());
	//	loggerConfig.setLevel(Level.ALL);
		loggerConfig.setLevel(Level.INFO);
		Logging.info(this.getClass().getName(), "log4j level: " + loggerConfig.getLevel());
//		loggerConfig.setLevel(level);
		
		ctx.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.

		

    }
    
    private static void setClassName(String className) {
    	logger = LogManager.getLogger(className);
    	}
    
    private static void setClassName(Object oClass) {
    	logger = LogManager.getLogger(oClass.getClass());
    	}
    
    public static void setLoggingLevel() {
    	//overrides the log4j-logging.xml (set in web.xml) located in conf directory
    	if (Globals.USER_DEBUG ) {
    		loggerConfig.setLevel(Level.ALL);
    		Logging.logger.info("log4j level: " + loggerConfig.getLevel());  
    		    		

    	} else {
    		//always show info unless debug
    		loggerConfig.setLevel(Level.INFO);
    		Logging.logger.info( "log4j level: " + loggerConfig.getLevel());
    	}
    	
    	ctx.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.

    	
    }
    
    public static void info(Object oClass, String message){
    	setClassName(oClass);
    	Logging.logger.info(message);
    }    
    
     public static void info(String sClass, String message){
    	setClassName(sClass);
    	Logging.logger.info(message);
    }    
    
    public static void debug(Object oClass, String message){
    	setClassName(oClass);
    	Logging.logger.debug(message);
    }
    
    public static void debug(String sClass, String message){
    	setClassName(sClass);
    	Logging.logger.debug(message);
    }
    
    
 //   public static void error(Message arg0){
 //    	Logging.logger.error(arg0);
 //   }
    
    
    public static void error(Object arg0){
     	Logging.logger.error(arg0);
    }


}
