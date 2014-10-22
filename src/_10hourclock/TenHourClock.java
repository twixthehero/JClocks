package _10hourclock;

import java.awt.*;
import javax.swing.*;

public class TenHourClock extends JApplet
{
	private Clock _clock;
	
	public static void main(String[] args)
	{
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("10 Hour Clock");
		window.setContentPane(new TenHourClock());
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public TenHourClock()
	{
		_clock = new Clock();
		
		setLayout(new BorderLayout());
		add(_clock, BorderLayout.CENTER);
		
		start();
	}
		
	public void start()
	{
		_clock.start();
	}
	
	public void stop()
	{
		_clock.stop();
	}
}