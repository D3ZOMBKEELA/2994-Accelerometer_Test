package org.usfirst.frc.team2994.robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Timer;

public class AccelerometerCalibrated extends BuiltInAccelerometer
{
	public static final String CALIBRATION_FILE_LOCATION = "/home/lvuser/accelerometer_calibration.txt";
	public static PrintStream stream = null;
	public static BufferedReader iStream = null;
	
	double xOffset = 0.0;
	double yOffset = 0.0;
	double zOffset = 0.0;
	
	public AccelerometerCalibrated()
	{
		super(Range.k4G);	// Because I feel like it
	}
	
	@Override
	public double getX()
	{
		return super.getX() - xOffset;
	}
	
	@Override
	public double getY()
	{
		return super.getY() - yOffset;
	}
	
	@Override
	public double getZ()
	{
		return super.getZ() - zOffset;
	}
	
	public void initialize()
	{
		// Read calibration data and adjust variables based on config
		String line = readLineFromFile();
		String[] offsets = line.split(",");
		
		xOffset = Double.valueOf(offsets[0]);
		yOffset = Double.valueOf(offsets[1]);
		zOffset = Double.valueOf(offsets[2]);
		
		System.out.println(xOffset + " " + yOffset + " " + zOffset);
		
//		xOffset = Double.valueOf(line.);
//		yOffset = Double.valueOf(line.substring(line.indexOf(",") + 1));
//		zOffset = Double.valueOf(line.substring(line.lastIndexOf(",") + 1));
	}
	
	public void calibrate()
	{
		// Call this when robot isn't moving at all to calibrate values
		int numEntries = 2000; // About 2 seconds of data
		double xZeros[] = new double[numEntries];
		double yZeros[] = new double[numEntries];
		double zZeros[] = new double[numEntries];
		
		for(int i = 0; i < numEntries; i++)
		{
			xZeros[i] = this.getX();
			yZeros[i] = this.getY();
			zZeros[i] = this.getZ();
			Timer.delay(0.01);
		}
		
		double averageX = xZeros[0];
		double averageY = yZeros[0];
		double averageZ = zZeros[0];
		
		for(int i = 1; i < numEntries; i++)
		{
			averageX += xZeros[i];
			averageY += yZeros[i];
			averageZ += zZeros[i];
		}
		
		averageX /= numEntries;
		averageY /= numEntries;
		averageZ /= numEntries;
		
		writeLineToFile(averageX + ", " + averageY + ", " + averageZ);
	}
	
	/**
	 * Writes a String to the file specified
	 * @param line The String to write to the file
	 * @param file The file to write to
	 * @return Whether the operation was successful or not
	 */
	public static boolean writeLineToFile(String line, File file) {
		
		if(stream == null)
		{
			try
			{
				stream = new PrintStream(new FileOutputStream(file, false));
				
				Runtime.getRuntime().addShutdownHook(new Thread()
				{
					@Override
					public void run()
					{
						stream.flush();
						stream.close();
					}
				});
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				return false;	
			}
		}
		
		stream.println(line);
		
		return true;
	}
	
	/**
	 * Passes line on to {link #writeLineToFile(String, File) writeLineToFile(java.lang.String line, java.io.File file)} using AUTONOMOUS_OUTPUT_FILE_LOC for the location parameter
	 * @param line The String to write to the file
	 * @return Whether the operation was successful or not
	 */
	public static boolean writeLineToFile(String line) {
		return writeLineToFile(line, new File(CALIBRATION_FILE_LOCATION));
	}
	
	public static String readLineFromFile(File file)
	{
		if(iStream == null)
		{
			try
			{
				iStream = new BufferedReader(new FileReader(file));
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		
		try
		{
			return iStream.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static String readLineFromFile()
	{
		return readLineFromFile(new File(CALIBRATION_FILE_LOCATION));
	}
}