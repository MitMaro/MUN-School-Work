package ca.mitmaro.ldb.gui.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import ca.mitmaro.ldb.gui.controller.MainPanelEvents;

public class MainMenu extends JMenuBar {
	private static final long serialVersionUID = -4928938743720332933L;
	
	public final JMenu file_menu;
	public final JMenu paper_menu;
	public final JMenu list_menu;
	
	private MainPanelEvents events;
	
	public MainMenu(MainPanelEvents events) {
		this.events = events;
		this.file_menu = new JMenu("File");
		this.paper_menu = new JMenu("Working Paper");
		this.list_menu = new JMenu("List");
		this.add(this.file_menu);
		//this.add(this.paper_menu);
		this.add(this.list_menu);
		this.createFileMenu();
		this.addListMenu();
	}
	
	protected void createFileMenu() {
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(this.events.save_application);
		this.file_menu.add(save);

		this.file_menu.add(new JSeparator());
		
		JMenuItem import_list = new JMenuItem("Load Paper Data");
		import_list.addActionListener(this.events.load_paper_data);
		this.file_menu.add(import_list);
		
		JMenuItem import_ref = new JMenuItem("Load Reference Data");
		import_ref.addActionListener(this.events.load_reference_data);
		this.file_menu.add(import_ref);
	}
	
	protected void createPaperMenu() {
	}
	
	protected void addListMenu() {
		JMenuItem master_add = new JMenuItem("Add to Master List");
		master_add.addActionListener(this.events.add_to_master);
		this.list_menu.add(master_add);

		this.list_menu.add(new JSeparator());
		
		JMenuItem clear = new JMenuItem("Clear Master List");
		clear.addActionListener(this.events.clear_list);
		this.list_menu.add(clear);
		
		// doesn't really do anything because well these lists are fictonal
		this.list_menu.add(new JMenuItem("Clear Master Reference"));
	}
}
