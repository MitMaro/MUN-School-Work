/*
  CS 2710 (Fall 2008), Assignment #2, Question #3
          File Name: ButtonViewerExt.java
       Student Name: Tim Oram
              MUN #: 200529220
 */

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// class shows how to use one ActionListener with multiple buttons
public class ButtonViewerExt {

	// constants containing the frames height and width
	private static final int FRAME_WIDTH = 200;
	private static final int FRAME_HEIGHT = 60;
	
	// the main function, show a frame with two buttons (A and B)
	public static void main(String[] args) {
		// create the main window
		JFrame frame = new JFrame();
		
		// create a panel for those buttons
		JPanel panel = new JPanel();
		
		// the two buttons
		JButton buttonA;
		JButton buttonB;
		
		// setup the frame
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create the two buttons
		buttonA = new JButton("A");
		buttonB = new JButton("B");

		// create the ActionListener
		ActionListener listener = new ClickListener();
		
		// set the action listeners for the buttons
		buttonA.addActionListener(listener);
		buttonB.addActionListener(listener);
		
		// add the buttons to the panel
		panel.add(buttonA);
		panel.add(buttonB);
		
		// add the panel to the frame
		frame.add(panel);
		
		// show the main window (frame)
		frame.setVisible(true);
	}

}
