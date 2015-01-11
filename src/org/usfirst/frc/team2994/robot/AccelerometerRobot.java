
package org.usfirst.frc.team2994.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * @author Tylar
 */
public class AccelerometerRobot extends SampleRobot
{
    RobotDrive myRobot;
    Joystick stick;

    public AccelerometerRobot()
    {
        myRobot = new RobotDrive(0, 1);
        myRobot.setExpiration(0.1);
        stick = new Joystick(0);
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous()
    {
        myRobot.setSafetyEnabled(false);
        myRobot.drive(-0.5, 0.0);	// drive forwards half speed
        Timer.delay(2.0);		//    for 2 seconds
        myRobot.drive(0.0, 0.0);	// stop robot
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl()
    {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled())
        {
            myRobot.arcadeDrive(stick); // drive with arcade style (use right stick)
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    public void test()
    {
    }
}