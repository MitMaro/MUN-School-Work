package ca.mitmaro.ldb.gui.view;

import ca.mitmaro.ldb.gui.model.MainModel;
import ca.mitmaro.ldb.gui.model.Observer;

public class MainState implements Observer {
	
	private MainPanel main;
	
	private MainModel model;
	
	public MainState(MainPanel main, MainModel model) {
		this.main = main;
		this.model = model;
		this.model.registerObserver(MainModel.EVENT_LIST_CHANGED, this);
		this.model.registerObserver(MainModel.EVENT_LIST_ADDED, this);
		this.model.registerObserver(MainModel.EVENT_LIST_REMOVED, this);
		this.model.registerObserver(MainModel.EVENT_PAPER_CHANGED, this);
		this.model.registerObserver(MainModel.EVENT_PAPER_UPDATED, this);
		this.model.registerObserver(MainModel.EVENT_PAPER_REMOVED, this);
		this.model.registerObserver(MainModel.EVENT_CITATION_SELECTED, this);
		this.model.registerObserver(MainModel.EVENT_CITATION_REMOVED, this);
		this.model.registerObserver(MainModel.EVENT_REFERENCE_SELECTED, this);
		this.model.registerObserver(MainModel.EVENT_REFERENCE_REMOVED, this);
	}
	
	private void updateListsCombo() {
		this.main.combobox_paper_lists.removeAllItems();
		for (String s: this.model.getLists()) {
			this.main.combobox_paper_lists.addItem(s);
		}
	}
	
	private void updateListTableData() {
		this.main.papers_data_model.setList(this.model.getCurrentList(), this.model.getListPapers());
		this.main.table_list_papers.packAll();
	}
	
	private void updateWorkingPaper() {
		this.main.textarea_paper.setText(this.model.getPrintablePaper());
		this.main.button_add_paper_citation.setEnabled(this.model.getWorkingPaper() != null);
		this.main.button_add_paper_reference.setEnabled(this.model.getWorkingPaper() != null);
		this.main.button_edit_paper.setEnabled(this.model.getWorkingPaper() != null);
		this.main.citations_data_model.setList(this.model.getCurrentList(), this.model.getCitations());
		this.main.references_data_model.setList(this.model.getCurrentList(), this.model.getReferences());
		this.main.table_paper_citations.packAll();
		this.main.table_paper_references.packAll();
	}
	
	private void handleListAdded() {
		this.updateListsCombo();
	}
	
	private void handleListChanged() {
		this.main.combobox_paper_lists.setSelectedItem(this.model.getList());
		this.main.button_delete_list.setEnabled(this.model.isListDeletable());
		this.updateListTableData();
	}

	private void handleListDeleted() {
		this.updateListsCombo();
	}
	
	private void handlePaperChanged() {
		this.updateWorkingPaper();
		this.main.button_delete_paper.setEnabled(this.model.canRemovePaper());
	}
	
	private void handlePaperRemoved() {
		this.updateListTableData();
	}
	
	private void handlePaperUpdated() {
		this.updateListTableData();
		// the edited paper is the working paper and may have changed
		this.updateWorkingPaper();
	}

	private void handleCitationSelected() {
		int row = this.main.table_paper_citations.getSelectedRow();
		this.main.button_load_paper_citation.setEnabled(row != -1);
		this.main.button_remove_paper_citation.setEnabled(row != -1);
	}
	
	private void handleReferenceSelected() {
		int row = this.main.table_paper_references.getSelectedRow();
		this.main.button_load_paper_reference.setEnabled(row != -1);
		this.main.button_remove_paper_reference.setEnabled(row != -1);
	}
	
	private void handleReferenceRemoved() {
		this.updateWorkingPaper();
	}
	
	private void handleCitationRemoved() {
		this.updateWorkingPaper();
	}
	
	@Override
	public void notify(String event) {
		if (event.equals(MainModel.EVENT_LIST_ADDED)) {
			this.handleListAdded();
		} else if (event.equals(MainModel.EVENT_LIST_CHANGED)) {
			this.handleListChanged();
		} else if (event.equals(MainModel.EVENT_LIST_REMOVED)) {
			this.handleListDeleted();
		} else if (event.equals(MainModel.EVENT_PAPER_CHANGED)) {
			this.handlePaperChanged();
		} else if (event.equals(MainModel.EVENT_PAPER_REMOVED)) {
			this.handlePaperRemoved();
		} else if (event.equals(MainModel.EVENT_PAPER_UPDATED)) {
			this.handlePaperUpdated();
		} else if (event.equals(MainModel.EVENT_CITATION_SELECTED)) {
			this.handleCitationSelected();
		} else if (event.equals(MainModel.EVENT_REFERENCE_SELECTED)) {
			this.handleReferenceSelected();
		} else if (event.equals(MainModel.EVENT_REFERENCE_REMOVED)) {
			this.handleReferenceRemoved();
		} else if (event.equals(MainModel.EVENT_CITATION_REMOVED)) {
			this.handleCitationRemoved();
		}
	}	
}
