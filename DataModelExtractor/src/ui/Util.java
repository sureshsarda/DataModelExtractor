package ui;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Util
{
	public static void setCurrentSystemLookAndFeel()
	{
		try
		{
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void setLocationToCenter(JFrame frame) {
	
		Point loc = frame.getLocation();
		Point newLoc = new Point();
		
		newLoc.x = loc.x - frame.getWidth() / 2;
		newLoc.y = loc.y - frame.getHeight() / 2;
		
		frame.setLocation(newLoc);
	}
	
}
