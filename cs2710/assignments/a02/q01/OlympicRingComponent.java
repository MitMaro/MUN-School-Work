/*
  CS 2710 (Fall 2008), Assignment #2, Question #1
          File Name: OlympicRingComponent.java
       Student Name: Tim Oram
              MUN #: #########
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;


// Describes a Olympic Ring Component
class OlympicRingComponent extends JComponent{
	
	// a circle for each of the rings
	private Ellipse2D.Double ellipse1;
	private Ellipse2D.Double ellipse2;
	private Ellipse2D.Double ellipse3;
	private Ellipse2D.Double ellipse4;
	private Ellipse2D.Double ellipse5;
	
	// the width of the rings (how thick the rings are)
	private int ringWidth;
	
	// constructor of the component
	public OlympicRingComponent(double x, double y, double width, double height, int ringWidth){
		
		// set the positions and size of each ring, a nice bit of math here
		
		// top rings
		// blue ring
		this.ellipse1 = new Ellipse2D.Double(x, y, width, height);
		// black ring
		this.ellipse3 = new Ellipse2D.Double(x + width + ringWidth, y, width, height);
		// red ring
		this.ellipse5 = new Ellipse2D.Double(x + (width * 2) + (ringWidth * 2), y, width, height);

		
		// bottom rings
		// yellow ring
		this.ellipse2 = new Ellipse2D.Double(x + Math.ceil(width / 2) + Math.ceil(ringWidth / 2), y + (height / 2), width, height);
		// green ring
		this.ellipse4 = new Ellipse2D.Double(x + Math.ceil(width * 1.5) + Math.floor(ringWidth * 1.5), y + (height / 2), width, height);
		
		// the width of the ring (how thick the rings are)
		this.ringWidth = ringWidth;

	}
	
	// overridden method to draw the component
	public void paintComponent(Graphics g){
		
		// recover Graphics2D
		Graphics2D g2 = (Graphics2D)g;

		// set the width of the brush
		g2.setStroke(new BasicStroke(this.ringWidth));
		
		// set the color of each ring and then draw it
		// first the top three
		g2.setColor(Color.BLUE);
		g2.draw(this.ellipse1);
		g2.setColor(Color.BLACK);
		g2.draw(this.ellipse3);
		g2.setColor(Color.RED);
		g2.draw(this.ellipse5);
		
		// now the bottom two 
		g2.setColor(Color.YELLOW);
		g2.draw(this.ellipse2);
		g2.setColor(Color.GREEN);
		g2.draw(this.ellipse4);
		
	}
}