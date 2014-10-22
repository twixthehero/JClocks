package _20hourclock;

import java.awt.*;
import javax.swing.*;

public class TwentyHourClock extends JApplet
{
	private Clock _clock;
	
	public static void main(String[] args)
	{
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Analog Clock");
		window.setContentPane(new TwentyHourClock());
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public TwentyHourClock()
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
