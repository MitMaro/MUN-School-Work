package ca.mitmaro.ldb.gui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import ca.mitmaro.ldb.gui.model.PaperModel;
import ca.mitmaro.ldb.gui.model.PaperModel.Type;

public class PaperTypeListener implements ActionListener {

	PaperModel paper_model;
	
	public PaperTypeListener(PaperModel paper_model) {
		this.paper_model = paper_model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cmb = (JComboBox)e.getSource();
		
		if (cmb.getSelectedItem().equals("Book")) {
			this.paper_model.setPaperType(Type.BOOK);
		} else if (cmb.getSelectedItem().equals("Book Chapter")) {
			this.paper_model.setPaperType(Type.BOOK_CHAPTER);
		} else if (cmb.getSelectedItem().equals("Journal Paper")) {
			this.paper_model.setPaperType(Type.JOURNAL_PAPER);
		} else if (cmb.getSelectedItem().equals("Conference Paper")) {
			this.paper_model.setPaperType(Type.CONFERENCE_PAPER);
		} else if (cmb.getSelectedItem().equals("PHD Thesis")) {
			this.paper_model.setPaperType(Type.PHD_THESIS);
		} else {
			throw new RuntimeException(String.format("Invalid Book Type: %s", cmb.getSelectedItem()));
		}
	}
}
