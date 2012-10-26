package ca.mitmaro.ldb.gui.view;

import ca.mitmaro.ldb.gui.model.Observer;
import ca.mitmaro.ldb.gui.model.PaperModel;

public class NewPaperDialogState implements Observer {

	private NewPaperDialog dialog;
	
	private PaperModel model;
	
	public NewPaperDialogState(NewPaperDialog dialog, PaperModel model) {
		this.dialog = dialog;
		this.model = model;
		this.model.registerObserver(PaperModel.EVENT_FIELD_CHANGED, this);
		this.model.registerObserver(PaperModel.EVENT_PAPER_TYPE_CHANGED, this);
		this.handleFieldChanged();
		this.handlePaperTypeChanged();
	}
	
	public void handleFieldChanged() {
		this.dialog.button_ok.setEnabled(!this.model.isInvalidPaper());
	}
	
	public void handlePaperTypeChanged() {
		this.dialog.disableInputs();
		switch (this.model.getPaperType()) {
			case BOOK:
				for (String input_name: this.dialog.book_inputs) {
					this.dialog.enableInput(input_name);
				}
			break;
			case BOOK_CHAPTER:
				for (String input_name: this.dialog.book_chapter_inputs) {
					this.dialog.enableInput(input_name);
				}
				break;
			case CONFERENCE_PAPER:
				for (String input_name: this.dialog.conference_paper_inputs) {
					this.dialog.enableInput(input_name);
				}
				break;
			case JOURNAL_PAPER:
				for (String input_name: this.dialog.journal_paper_inputs) {
					this.dialog.enableInput(input_name);
				}
				break;
			case PHD_THESIS:
				for (String input_name: this.dialog.phd_thesis_inputs) {
					this.dialog.enableInput(input_name);
				}
				break;
		}
		this.handleFieldChanged();
	}
	
	@Override
	public void notify(String event) {
		if (event.equals(PaperModel.EVENT_FIELD_CHANGED)) {
			this.handleFieldChanged();
		} else if (event.equals(PaperModel.EVENT_PAPER_TYPE_CHANGED)) {
			this.handlePaperTypeChanged();
		}
	}
	
}
