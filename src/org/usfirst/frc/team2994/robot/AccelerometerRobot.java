
package org.usfirst.frc.team2994.robot;

import java.util.logging.Level;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * @author Tylar
 * @version 1.0
 */
public class AccelerometerRobot extends SampleRobot
{
	double accelX;
	double accelY;
	double accelZ;
	
	public static final RobotDrive robotDrive = new RobotDrive(0, 1);
	
	public static final Joystick joystick = new Joystick(0);	// 2994-L: Joystick input recieved from input 1
	
	public static final Accelerometer accelerometer = new BuiltInAccelerometer();
	
    public AccelerometerRobot()
    {
    }
    
    public void robotInit()
    {
    	robotDrive.setExpiration(0.01 * 1.5);
    	accelerometer.setRange(Accelerometer.Range.k4G);
    }
    
    private void drive(double speed, double time)
    {
    	robotDrive.drive(speed, 0.0);
    	Timer.delay(time);
    	robotDrive.drive(0.0, 0.0);
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous()
    {
    	robotDrive.setSafetyEnabled(false);
    	
    	Utils.configureRobotLogger();
    	
    	drive(-0.3, 1.0);	// 2994-L: Might need to expand drive function and place accelerometer read inside
    	accelX = accelerometer.getX();
    	accelY = accelerometer.getY();
    	accelZ = accelerometer.getZ();
    	
    	drive(0.0, 0.0);
    	
    	Utils.ROBOT_LOGGER.log(Level.INFO, "Accelerometer X: " + accelX);
    	Utils.ROBOT_LOGGER.log(Level.INFO, "Accelerometer Y: " + accelY);
    	Utils.ROBOT_LOGGER.log(Level.INFO, "Accelerometer Z: " + accelZ);
    	Utils.ROBOT_LOGGER.log(Level.INFO, "\n");
    	
    	Utils.closeLogger();
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl()
    {
        robotDrive.setSafetyEnabled(false);
        
        Utils.configureRobotLogger();
        
        while (isOperatorControl() && isEnabled())
        {
            robotDrive.arcadeDrive(joystick);
            accelX = accelerometer.getX();
            accelY = accelerometer.getY();
            accelZ = accelerometer.getZ();
            
            Utils.ROBOT_LOGGER.log(Level.INFO, "Accelerometer X: " + accelX);
        	Utils.ROBOT_LOGGER.log(Level.INFO, "Accelerometer Y: " + accelY);
        	Utils.ROBOT_LOGGER.log(Level.INFO, "Accelerometer Z: " + accelZ);
        	Utils.ROBOT_LOGGER.log(Level.INFO, "\n");
            
            Timer.delay(0.005);		// wait for a motor update time
        }
        
        Utils.closeLogger();
    }

    /**
     * Runs during test mode
     */
    public void test()
    {
    }
    
}
