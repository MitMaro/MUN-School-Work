package ca.mitmaro.ldb.gui.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ca.mitmaro.ldb.gui.model.PaperModel;
import ca.mitmaro.ldb.gui.view.EditPaperDialog;

public class EditPaperDialogEvents extends PaperDialogEvents {
	
	private PaperModel model;
	
	public EditPaperDialogEvents(PaperModel model, EditPaperDialog dialog) {
		super(model, dialog);
		this.model = model;
	}

	public final AbstractAction save_paper = new AbstractAction("Save") {
		
		private static final long serialVersionUID = -4521700331569884217L;

		@Override
		public void actionPerformed(ActionEvent e) {
			EditPaperDialogEvents.this.model.createPaper();
		}
	};
}
