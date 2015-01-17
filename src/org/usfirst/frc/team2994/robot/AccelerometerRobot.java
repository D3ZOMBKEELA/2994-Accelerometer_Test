
package org.usfirst.frc.team2994.robot;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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
	
	public static final AccelerometerCalibrated accelerator = new AccelerometerCalibrated();
	
	boolean isAcceleratorDone = false;
	
    public AccelerometerRobot()
    {
    }
    
    public void robotInit()
    {
    	robotDrive.setExpiration(0.01 * 1.5);
    	accelerometer.setRange(Range.k2G);
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
    	
    	accelerator.calibrate();
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl()
    {
        robotDrive.setSafetyEnabled(true);
        
        Utils.configureRobotLogger();
        
        accelerator.initialize();
        
        while (isOperatorControl() && isEnabled())
        {
            robotDrive.arcadeDrive(joystick);
            accelX = accelerator.getX();
            accelY = accelerator.getY();
            accelZ = accelerator.getZ();
            
            System.out.println("Accelerometer X: " + accelX);
            System.out.println("Accelerometer Y: " + accelY);
            System.out.println("Accelerometer Z: " + accelZ);
            System.out.println();
            
            Timer.delay(0.005);		// wait for a motor update time
        }
        
        Utils.closeLogger();
    }

    /**
     * Runs during test mode
     */
    public void test()
    {
    	LiveWindow.run();
    }
    
    /**
     * Make it so the accelerometer number is tracked over time in entries. Similar to the 
     * frc driver station's tracking but without being in a graph
     */
}
