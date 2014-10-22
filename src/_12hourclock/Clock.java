package _12hourclock;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

public class Clock extends JComponent
{
	private static final double TWO_PI = 2.0 * Math.PI;
	private static final int UPDATE_INTERVAL = 100;
	
	private Calendar _now = Calendar.getInstance();
	
	private int _diameter;
	private int _centerX;
	private int _centerY;
	private BufferedImage _clockImage;
	
	private javax.swing.Timer _timer;
	
	public Clock()
	{
		setPreferredSize(new Dimension(300, 300));
		
		_timer = new javax.swing.Timer(UPDATE_INTERVAL, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateTime();
				repaint();
			}
		});
	}
	
	public void start()
	{
		_timer.start();
	}
	
	public void stop()
	{
		_timer.stop();
	}
	
	public void updateTime()
	{
		_now.setTimeInMillis(System.currentTimeMillis());
	}
	
	public void paintComponent(Graphics g)
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
		int hours = _now.get(Calendar.HOUR);
		int minutes = _now.get(Calendar.MINUTE);
		int seconds = _now.get(Calendar.SECOND);
		int millis = _now.get(Calendar.MILLISECOND);
		
		g2.setColor(Color.BLUE);
		
		int handMin = _diameter / 8;
		int handMax = _diameter / 2;
		double fseconds = (seconds + (double)millis / 1000) / 60.0;
		drawRadius(g2, fseconds, 0, handMax);
		
		g2.setColor(Color.RED);
		
		handMin = 0;
		handMax = _diameter / 3;
		double fminutes = (minutes + fseconds) / 60.0;
		drawRadius(g2, fminutes, 0, handMax);
		
		g2.setColor(Color.YELLOW);
		
		handMin = 0;
		handMax = _diameter / 4;
		drawRadius(g2, (hours + fminutes) / 12.0, 0, handMax);
		
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
		
		for (int sec = 0; sec < 60; sec++)
		{
			int tickStart;
			if (sec % 5 ==0)
			{
				tickStart = radius - 10;
				g2.setColor(Color.YELLOW);
			}
			else
			{
				tickStart = radius - 5;
				g2.setColor(Color.RED);
			}
			
			drawRadius(g2, sec / 60.0, tickStart, radius);
		}
	}
	
	private void drawRadius(Graphics2D g2, double percent, int minRadius, int maxRadius)
	{
		double radians = (0.5 - percent) * TWO_PI;
		double sine = Math.sin(radians);
		double cosine = Math.cos(radians);
		
		int dxmin = _centerX + (int)(minRadius * sine);
		int dymin = _centerY + (int)(minRadius * cosine);
		
		int dxmax = _centerX + (int)(maxRadius * sine);
		int dymax = _centerY + (int)(maxRadius * cosine);
		
		g2.drawLine(dxmin, dymin, dxmax, dymax);
	}
}
