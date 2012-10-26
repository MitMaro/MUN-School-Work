package ca.mitmaro.ldb.gui.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;

import ca.mitmaro.ldb.gui.model.PaperModel;
import ca.mitmaro.ldb.gui.model.PaperModel.Type;
import ca.mitmaro.ldb.gui.view.NewPaperDialog;

public class NewPaperDialogEvents extends PaperDialogEvents {
	
	private NewPaperDialog dialog;
	private PaperModel model;
	
	public NewPaperDialogEvents(PaperModel model, NewPaperDialog dialog) {
		super(model, dialog);
		this.model = model;
		this.dialog = dialog;
	}

	public final AbstractAction save_paper = new AbstractAction("Save") {
		
		private static final long serialVersionUID = -4521700331569884217L;

		@Override
		public void actionPerformed(ActionEvent e) {
			NewPaperDialogEvents.this.model.createPaper();
		}
	};
	
	public final AbstractAction paper_type_changed = new AbstractAction() {
		
		private static final long serialVersionUID = -4083401168537403571L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cmb = NewPaperDialogEvents.this.dialog.combobox_paper_types;
			PaperModel model = NewPaperDialogEvents.this.model;
			
			if (cmb.getSelectedItem().equals("Book")) {
				model.setPaperType(Type.BOOK);
			} else if (cmb.getSelectedItem().equals("Book Chapter")) {
				model.setPaperType(Type.BOOK_CHAPTER);
			} else if (cmb.getSelectedItem().equals("Journal Paper")) {
				model.setPaperType(Type.JOURNAL_PAPER);
			} else if (cmb.getSelectedItem().equals("Conference Paper")) {
				model.setPaperType(Type.CONFERENCE_PAPER);
			} else if (cmb.getSelectedItem().equals("PHD Thesis")) {
				model.setPaperType(Type.PHD_THESIS);
			} else {
				throw new RuntimeException(String.format("Invalid Book Type: %s", cmb.getSelectedItem()));
			}
		}
	};
}
