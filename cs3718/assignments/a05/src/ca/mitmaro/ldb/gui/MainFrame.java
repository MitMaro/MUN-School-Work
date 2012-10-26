package ca.mitmaro.ldb.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import ca.mitmaro.ldb.Application;
import ca.mitmaro.ldb.gui.model.MainModel;
import ca.mitmaro.ldb.gui.view.MainMenu;
import ca.mitmaro.ldb.gui.view.MainPanel;
import ca.mitmaro.ldb.gui.view.MainState;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -3742970353695171197L;

	public MainFrame(Application application) {
		this.setTitle("Literature Database");
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(875, 575));
		MainModel model = new MainModel(application);
		MainPanel panel = new MainPanel(model);
		new MainState(panel, model);
		this.add(panel);
		this.setJMenuBar(new MainMenu(panel.getEventHandler()));
	}
	
}
