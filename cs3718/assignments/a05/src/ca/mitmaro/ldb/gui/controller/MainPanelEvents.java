package ca.mitmaro.ldb.gui.controller;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.mitmaro.ldb.gui.model.MainModel;
import ca.mitmaro.ldb.gui.model.PaperModel;
import ca.mitmaro.ldb.gui.view.EditPaperDialog;
import ca.mitmaro.ldb.gui.view.EditPaperDialogState;
import ca.mitmaro.ldb.gui.view.MainPanel;
import ca.mitmaro.ldb.gui.view.NewPaperDialog;
import ca.mitmaro.ldb.gui.view.NewPaperDialogState;

public class MainPanelEvents {
	
	private final MainModel model;
	
	private final MainPanel panel;
	
	public MainPanelEvents(MainModel model, MainPanel panel) {
		this.model = model;
		this.panel = panel;
	}
	
	private String fileListPrompt() {
		String list_name = null;
		do {
			// ask for list name
			list_name = JOptionPane.showInputDialog(
				this.panel,
				"Enter new list name?",
				"Create New List",
				JOptionPane.QUESTION_MESSAGE
			);
			
			// good list name then skip next bit
			if (this.model.isValidListName(list_name)) {
				break;
			}
			
			// invalid list name, prompt to try again
			int opt = JOptionPane.showOptionDialog(
				this.panel,
				"Invalid list name provided. Try again?",
				"Validatino Error",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.ERROR_MESSAGE,
				null,
				new String[]{"Yes", "No"},
				"Yes"
			);
			
			// 1 == No selected, exit out doing nothing
			if (opt == 1) {
				return null;
			}
			
		} while(true);
		
		return list_name;
	}
	
	public final AbstractAction list_add = new AbstractAction() {
		
		private static final long serialVersionUID = 6875841135137252979L;
		
		private MainPanelEvents self = MainPanelEvents.this;
		
		@Override
		public void actionPerformed(ActionEvent event) {
		
			String list_name = MainPanelEvents.this.fileListPrompt();
			
			if (list_name == null) {
				return;
			}
			
			self.model.addList(list_name);
			self.model.setWorkingList(list_name);
		}
	};
	
	public final AbstractAction lists_change = new AbstractAction() {
		
		private static final long serialVersionUID = 2862334052360784812L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JComboBox cmb = MainPanelEvents.this.panel.combobox_paper_lists;
			
			// nothing selected, ignore
			if (cmb.getSelectedIndex() < 0) {
				return;
			}
			
			MainPanelEvents.this.model.setWorkingList((String)cmb.getSelectedItem());
			
		}
	};

	public final ActionListener list_delete = new AbstractAction() {

		private static final long serialVersionUID = 8917601710274020024L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// invalid list name, prompt to try again
			int opt = JOptionPane.showOptionDialog(
				(Frame)SwingUtilities.getRoot(MainPanelEvents.this.panel),
				String.format("Are you sure you wish to delete the paper \"%s\"?", MainPanelEvents.this.model.getPaperName()),
				"Confirm Delete",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				new String[]{"Yes", "No"},
				"No"
			);
			
			// 0 == Yes selected, delete list
			if (opt == 0) {
				MainPanelEvents.this.model.deleteWorkingPaper();
			}
		}
		
	};

	public final ActionListener new_paper = new AbstractAction() {
		
		private static final long serialVersionUID = 4158501468630606545L;

		@Override
		public void actionPerformed(ActionEvent e) {
			PaperModel model = new PaperModel(MainPanelEvents.this.model);
			NewPaperDialog dialog = new NewPaperDialog((Frame)SwingUtilities.getRoot(MainPanelEvents.this.panel), model);
			new NewPaperDialogState(dialog, model);
			dialog.setVisible(true);
		}
	};

	public final ActionListener edit_paper = new AbstractAction() {
		
		private static final long serialVersionUID = 4158501468630606545L;

		@Override
		public void actionPerformed(ActionEvent e) {
			PaperModel model = new PaperModel(MainPanelEvents.this.model);
			model.setPaper(MainPanelEvents.this.model.getWorkingPaper());
			EditPaperDialog dialog = new EditPaperDialog((Frame)SwingUtilities.getRoot(MainPanelEvents.this.panel), model);
			new EditPaperDialogState(dialog, model);
			dialog.setVisible(true);
		}
	};

	public final ListSelectionListener list_select = new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int row = MainPanelEvents.this.panel.table_list_papers.getSelectedRow();
			
			// nothing selected
			if (row == -1) {
				MainPanelEvents.this.model.setWorkingPaper(null);
				return;
			}
			
			MainPanelEvents.this.model.setWorkingPaperFromPid((String)MainPanelEvents.this.panel.table_list_papers.getModel().getValueAt(row, 0));
			
		}
	};

	public final ActionListener delete_paper = new AbstractAction() {
		
		private static final long serialVersionUID = 9085600629053949914L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// invalid list name, prompt to try again
			int opt = JOptionPane.showOptionDialog(
				MainPanelEvents.this.panel,
				String.format("Are you sure you wish to delete the paper \"%s\"?", MainPanelEvents.this.model.getPaperName()),
				"Confirm Delete",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				new String[]{"Yes", "No"},
				"No"
			);
			
			// 0 == Yes selected, delete list
			if (opt == 0) {
				MainPanelEvents.this.model.deleteWorkingPaper();
			}
		}
	};

	public final ActionListener remove_citation = new AbstractAction() {
		
		private static final long serialVersionUID = 3411913070839317702L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int row = MainPanelEvents.this.panel.table_paper_citations.getSelectedRow();
			String pid = (String)MainPanelEvents.this.panel.table_paper_citations.getModel().getValueAt(row, 0);
			String title = (String)MainPanelEvents.this.panel.table_paper_citations.getModel().getValueAt(row, 1);
			// invalid list name, prompt to try again
			int opt = JOptionPane.showOptionDialog(
				MainPanelEvents.this.panel,
				String.format("Are you sure you wish to remove the citation \"%s\"?", title),
				"Confirm Remove",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				new String[]{"Yes", "No"},
				"No"
			);
			
			if (opt == 0) {
				MainPanelEvents.this.model.removeCitation(pid);
			}
			
		}
	};

	public final ActionListener remove_reference = new AbstractAction() {
		
		private static final long serialVersionUID = 3411913070839317702L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int row = MainPanelEvents.this.panel.table_paper_references.getSelectedRow();
			String pid = (String)MainPanelEvents.this.panel.table_paper_references.getModel().getValueAt(row, 0);
			String title = (String)MainPanelEvents.this.panel.table_paper_references.getModel().getValueAt(row, 1);
			// invalid list name, prompt to try again
			int opt = JOptionPane.showOptionDialog(
				MainPanelEvents.this.panel,
				String.format("Are you sure you wish to remove the reference \"%s\"?", title),
				"Confirm Remove",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				new String[]{"Yes", "No"},
				"No"
			);
			
			if (opt == 0) {
				MainPanelEvents.this.model.removeReference(pid);
			}
		}
	};
	
	public final ActionListener set_cwp_citations = new AbstractAction() {
		
		private static final long serialVersionUID = 3411913070839317702L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int row = MainPanelEvents.this.panel.table_paper_citations.getSelectedRow();
			MainPanelEvents.this.model.setWorkingPaperFromPid((String)MainPanelEvents.this.panel.table_paper_citations.getModel().getValueAt(row, 0));
		}
	};
	
	public final ListSelectionListener citation_list_change = new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			MainPanelEvents.this.model.triggerEvent(MainModel.EVENT_CITATION_SELECTED);
		}
	};
	
	public final ActionListener set_cwp_references = new AbstractAction() {
		
		private static final long serialVersionUID = 6505346554107247968L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int row = MainPanelEvents.this.panel.table_paper_references.getSelectedRow();
			MainPanelEvents.this.model.setWorkingPaperFromPid((String)MainPanelEvents.this.panel.table_paper_references.getModel().getValueAt(row, 0));
		}
	};

	public final ListSelectionListener reference_list_change = new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			MainPanelEvents.this.model.triggerEvent(MainModel.EVENT_REFERENCE_SELECTED);
		}
	};

	public final ActionListener load_paper_data = new AbstractAction() {
		
		private static final long serialVersionUID = 3288964833554072980L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			FileDialog fd = new FileDialog(
				(Frame)SwingUtilities.getRoot(MainPanelEvents.this.panel),
				"Load Paper Data",
				FileDialog.LOAD
			);
			fd.setDirectory(System.getProperty("user.dir"));
			fd.setLocation(50, 50);
			fd.setVisible(true);
			
			String file_name = fd.getFile();
			
			if (file_name == null) {
				return;
			}
			
			file_name = fd.getDirectory() + file_name;
			
			String list_name = MainPanelEvents.this.fileListPrompt();
			
			if (list_name == null) {
				return;
			}
			
			MainPanelEvents.this.model.loadPaperDataFile(file_name, list_name);
		}
	};
	
	public final ActionListener load_reference_data = new AbstractAction() {
		
		private static final long serialVersionUID = 3288964833554072980L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			FileDialog fd = new FileDialog(
				(Frame)SwingUtilities.getRoot(MainPanelEvents.this.panel),
				"Load Paper Refernce Data",
				FileDialog.LOAD
			);
			fd.setDirectory(System.getProperty("user.dir"));
			fd.setLocation(50, 50);
			fd.setVisible(true);
			
			String file_name = fd.getFile();
			
			if (file_name == null) {
				return;
			}
			
			file_name = fd.getDirectory() + file_name;
			
			String list_name = MainPanelEvents.this.fileListPrompt();
			
			if (list_name == null) {
				return;
			}
			
			MainPanelEvents.this.model.loadRefenceDataFile(file_name, list_name);
		}
	};

	public final ActionListener add_to_master = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			MainPanelEvents.this.model.addToMaster();
		}
	};

	public final ActionListener save_application = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			MainPanelEvents.this.model.save();
		}
	};

	public final ActionListener clear_list = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			MainPanelEvents.this.model.clearMasterList();
		}
	};
}
