package _24hourclock;

import java.awt.*;
import javax.swing.*;

public class Twenty_FourHourClock extends JApplet
{
	private Clock _clock;
	
	public static void main(String[] args)
	{
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Analog Clock");
		window.setContentPane(new Twenty_FourHourClock());
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public Twenty_FourHourClock()
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
