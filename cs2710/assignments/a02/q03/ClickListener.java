/*
  CS 2710 (Fall 2008), Assignment #2, Question #3
          File Name: ClickListener.java
       Student Name: Tim Oram
              MUN #: 200529220
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

// an action listener that prints the name of the button clicked
public class ClickListener implements ActionListener {

	// the method called when one of the buttons is clicked
	public void actionPerformed(ActionEvent event) {
		
		// get the button object
		JButton btn = (JButton) event.getSource();
		
		// print out a message with the buttons text
		System.out.println("Button " + btn.getText() +  " was clicked!");
	}
}
