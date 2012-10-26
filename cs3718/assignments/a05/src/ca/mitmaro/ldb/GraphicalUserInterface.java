package ca.mitmaro.ldb;

import javax.swing.JFrame;

import ca.mitmaro.ldb.gui.MainFrame;

public class GraphicalUserInterface {
	
	private Application application;
	
	private MainFrame main_frame;

	public GraphicalUserInterface(Application application) {
		this.application = application;
		
		this.main_frame = new MainFrame(this.application);
		
	}
	
	public void run(boolean default_exit) {
		
		if (default_exit) {
			this.main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		this.main_frame.setVisible(true);
	}
}
