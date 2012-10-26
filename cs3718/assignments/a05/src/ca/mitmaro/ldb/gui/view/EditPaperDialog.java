package ca.mitmaro.ldb.gui.view;

import java.awt.Frame;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import ca.mitmaro.ldb.gui.controller.EditPaperDialogEvents;
import ca.mitmaro.ldb.gui.model.PaperModel;

public class EditPaperDialog extends PaperDialog {
	
	private static final long serialVersionUID = 2624838282274433230L;

	public final JComboBox combobox_paper_types;
	
	PaperModel paper_model;
	
	EditPaperDialogEvents events;

	public EditPaperDialog(Frame parent, PaperModel paper_model) {
		super(parent, "Create New Paper");
		this.paper_model = paper_model;
		
		this.events = new EditPaperDialogEvents(this.paper_model, this);
		this.setEventsManager(this.events);
		
		this.combobox_paper_types = new JComboBox();
		for (String s: new String[]{"Book", "Book Chapter", "Conference Paper", "Journal Paper", "PHD Thesis"}) {
			this.combobox_paper_types.addItem(s);
		}
		
		this.combobox_paper_types.setEnabled(false);
		
		switch (this.paper_model.getPaperType()) {
			case BOOK:
				this.combobox_paper_types.setSelectedItem("Book");
				break;
			case BOOK_CHAPTER:
				this.combobox_paper_types.setSelectedItem("Book Chapter");
				break;
			case CONFERENCE_PAPER:
				this.combobox_paper_types.setSelectedItem("Conference Paper");
				break;
			case JOURNAL_PAPER:
				this.combobox_paper_types.setSelectedItem("Journal Paper");
				break;
			case PHD_THESIS:
				this.combobox_paper_types.setSelectedItem("PHD Thesis");
				break;
		}
		this.main_panel.add(this.combobox_paper_types);
		
		this.main_panel.add(new JSeparator());
		
		JTextField in_id = new JTextField(this.paper_model.getPaperField("paper_id"), 12);
		in_id.setEnabled(false);
		this.addInput("paper_id", "Paper ID:", in_id);
		this.addInput("author_first", "Author First:", new JTextField(this.paper_model.getPaperField("author_first"), 12));
		this.addInput("author_last", "Author Last:", new JTextField(this.paper_model.getPaperField("author_last"), 12));
		this.addInput("title", "Title:", new JTextField(this.paper_model.getPaperField("title"), 12));
		try {
			JFormattedTextField in_year = new JFormattedTextField(new MaskFormatter("####"));
			in_year.setText(this.paper_model.getPaperField("year"));
			this.addInput("year", "Year:", in_year);
		} catch (ParseException e) {
			throw new RuntimeException("Invalid Mask");
		}
		this.addInput("book_title", "Book Title:", new JTextField(this.paper_model.getPaperField("book_title"), 12));
		this.addInput("address", "Address:", new JTextField(this.paper_model.getPaperField("address"), 12));
		this.addInput("chapter_title", "Chapter Title:", new JTextField(this.paper_model.getPaperField("chapter_title"), 12));
		JFormattedTextField in_pages = new JFormattedTextField(new RegexFormatter("^[0-9]+(\\s?-\\s?[0-9]+)?$"));
		in_pages.setText(this.paper_model.getPaperField("pages"));
		this.addInput("pages", "Pages:", in_pages);
		this.addInput("publisher", "Publisher:", new JTextField(this.paper_model.getPaperField("publisher"), 12));
		this.addInput("editors", "Editors:", new JTextField(this.paper_model.getPaperField("editors"), 12));
		this.addInput("paper_title", "Paper Title:", new JTextField(this.paper_model.getPaperField("paper_title"), 12));
		this.addInput("journal_title", "Journal Title:", new JTextField(this.paper_model.getPaperField("journal_title"), 12));
		JFormattedTextField in_vol = new JFormattedTextField(NumberFormat.getIntegerInstance());
		in_vol.setText(this.paper_model.getPaperField("volume"));
		this.addInput("volume", "Volume:", in_vol);
		
		this.main_panel.add(this.input_panel);
		
		this.main_panel.add(new JSeparator());
		
		this.addButtons(this.events.save_paper, this.events.close_handler);
		
		this.pack();
	}
	
	public EditPaperDialogEvents createEventManager(PaperModel model) {
		return new EditPaperDialogEvents(model, this);
	}
	
	protected void enableInput(String name) {
		
		if (name.equals("paper_id")) {
			return;
		}
		
		super.enableInput(name);
	}
}
