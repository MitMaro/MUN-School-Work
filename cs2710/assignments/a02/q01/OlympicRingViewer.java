/*
  CS 2710 (Fall 2008), Assignment #2, Question #1
          File Name: OlympicRingViewer.java
       Student Name: Tim Oram
              MUN #: #########
 */

import javax.swing.JFrame;

class OlympicRingViewer{
	
	// creates a JFrame and adds an Olympic ring and shows the frame
	public static void main(String[] argv){
		
		// create a JFrame
		JFrame frame = new JFrame();
		
		// set the size
		frame.setSize(300, 200);
		// and the title
		frame.setTitle("Rectangle Component Viewer");
		// set what to do on close operation
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// create the Ring
		OlympicRingComponent oRing = new OlympicRingComponent(10,10,75,75,6);
		
		// add it to the frame
		frame.add(oRing);
	
		// show the frame
		frame.setVisible(true);
	}
}