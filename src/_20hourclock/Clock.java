package _20hourclock;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.image.*;

public class Clock extends JComponent
{
	private static final double TWO_PI = 2.0 * Math.PI;
	private static final int UPDATE_INTERVAL = 50;
	
	private Calendar _now = Calendar.getInstance();
	
	private int _diameter;
	private int _centerX;
	private int _centerY;
	private BufferedImage _clockImage;
	
	private javax.swing.Timer _timer;
	
	public Clock() //Clock constructor
	{
		setPreferredSize(new Dimension(150, 150));
		
		_timer = new javax.swing.Timer(UPDATE_INTERVAL, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateTime();
				repaint();
			}
		});
	}
	
	public void start() //start
	{
		_timer.start();
	}
	
	public void stop() //stop
	{
		_timer.stop();
	}
	
	public void updateTime() //updateTime
	{
		_now.setTimeInMillis(System.currentTimeMillis());
	}
	
	public void paintComponent(Graphics g) //paintComponent
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int w = getWidth();
		int h = getHeight();
		
		_diameter = ((w < h) ? w : h);
		_centerX = _diameter / 2;
		_centerY = _diameter / 2;
		
		if (_clockImage == null || _clockImage.getWidth() != w || _clockImage.getHeight() != h)
		{
			_clockImage = (BufferedImage)(this.createImage(h, w));
			
			Graphics2D g2a = _clockImage.createGraphics();
			g2a.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			drawClockFace(g2a);
		}
		
		g2.drawImage(_clockImage, null, 0, 0);
		
		drawClockHands(g2);
	}
	
	private void drawClockHands(Graphics2D g2)
	{
		int hours = _now.get(Calendar.HOUR_OF_DAY);
		int minutes = hours * 60 + _now.get(Calendar.MINUTE);
		int seconds = _now.get(Calendar.SECOND);
		int millis = _now.get(Calendar.MILLISECOND);
		
		hours = minutes / 72;
		minutes -= hours * 72;
		
		g2.setColor(Color.BLUE);
		
		int handMax = _diameter / 2;
		double fseconds = (seconds + (double)millis / 1000) / 60.0;
		drawRadius(g2, fseconds, 0, handMax);
		
		g2.setColor(Color.RED);
		
		handMax = _diameter / 3;
		double fminutes = (minutes + fseconds) / 72.0;
		drawRadius(g2, fminutes, 0, handMax);
		
		g2.setColor(Color.YELLOW);
		
		handMax = _diameter / 4;
		drawRadius(g2, (hours + fminutes) / 20.0, 0, handMax);
		
		g2.setColor(Color.BLACK);
		g2.drawString(hours + ":" + minutes + ":" + seconds + ":" + millis, 140, 200);
	}
	
	private void drawClockFace(Graphics2D g2)
	{
		g2.setColor(Color.BLACK);
		g2.fillOval(0, 0, _diameter, _diameter);
		g2.setColor(Color.RED);
		g2.drawOval(0, 0, _diameter, _diameter);
		
		int radius = _diameter / 2;
		
		for (double sec = 0; sec < 60; sec++)
		{
			int tickStart = radius - 5;
			
			drawRadius(g2, sec / 60, tickStart, radius);
		}
		
		for (double sec = 0; sec < 72; sec += 3.6)
		{
			int tickStart = radius - 10;
			
			g2.setColor(Color.YELLOW);
			drawRadius(g2, sec / 72, tickStart, radius); 
		}
		
	}
	
	private void drawRadius(Graphics2D g2, double percent, double minRadius, int maxRadius)
	{
		double radians = (0.5 - percent) * TWO_PI;		
		double sine = Math.sin(radians);
		double cosine = Math.cos(radians);
		
		int dxmin = (int)(_centerX + minRadius * sine);
		int dymin = (int)(_centerY + minRadius * cosine);
		
		int dxmax = (int)(_centerX + maxRadius * sine);
		int dymax = (int)(_centerY + maxRadius * cosine);
		
		g2.drawLine(dxmin, dymin, dxmax, dymax);
	}
}