package org.usfirst.frc.team2994.robot;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Utils {
	
	/*
	 * Name of the Logger
	 */
	public static final String ROBOT_LOGGER_NAME = "RobotLogger";
	
	/*
	 * The logger to be used
	 */
	public static final Logger ROBOT_LOGGER = Logger.getLogger(ROBOT_LOGGER_NAME);
	
	/*
	 * The path for the log
	 */
	public static final String ROBOT_LOG_FILENAME = "/home/lvuser/robot_accelerometer.log";
	
	public static FileHandler fh;
	
	/*
	 * Configures the logger
	 */
	public static void configureRobotLogger() {
		try {
			fh = new FileHandler(ROBOT_LOG_FILENAME, false);
			fh.setFormatter(new SimpleFormatter());
			ROBOT_LOGGER.addHandler(fh);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeLogger() {
			fh.close();
	}
}