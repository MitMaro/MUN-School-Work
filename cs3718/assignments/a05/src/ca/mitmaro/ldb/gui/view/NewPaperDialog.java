package ca.mitmaro.ldb.gui.view;

import java.awt.Frame;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import ca.mitmaro.ldb.gui.controller.NewPaperDialogEvents;
import ca.mitmaro.ldb.gui.model.PaperModel;

public class NewPaperDialog extends PaperDialog {
	
	private static final long serialVersionUID = 2624838282274433230L;

	public final JComboBox combobox_paper_types;
	
	PaperModel paper_model;
	
	NewPaperDialogEvents events;

	public NewPaperDialog(Frame parent, PaperModel paper_model) {
		super(parent, "Create New Paper");
		this.paper_model = paper_model;
		
		this.events = new NewPaperDialogEvents(this.paper_model, this);
		this.setEventsManager(this.events);
		
		this.combobox_paper_types = new JComboBox();
		for (String s: new String[]{"Book", "Book Chapter", "Conference Paper", "Journal Paper", "PHD Thesis"}) {
			this.combobox_paper_types.addItem(s);
		}
		
		this.combobox_paper_types.addActionListener(this.events.paper_type_changed);

		this.combobox_paper_types.setSelectedIndex(0);
		
		this.main_panel.add(this.combobox_paper_types);
		
		this.main_panel.add(new JSeparator());
		
		this.addInput("paper_id", "Paper ID:", new JTextField(12));
		this.addInput("author_first", "Author First:", new JTextField(12));
		this.addInput("author_last", "Author Last:", new JTextField(12));
		this.addInput("title", "Title:", new JTextField(12));
		try {
			this.addInput("year", "Year:", new JFormattedTextField(new MaskFormatter("####")));
		} catch (ParseException e) {
			throw new RuntimeException("Invalid Mask");
		}
		this.addInput("book_title", "Book Title:", new JTextField(12));
		this.addInput("address", "Address:", new JTextField(12));
		this.addInput("chapter_title", "Chapter Title:", new JTextField(12));
		this.addInput("pages", "Pages:", new JFormattedTextField(new RegexFormatter("^[0-9]+(\\s?-\\s?[0-9]+)?$")));
		this.addInput("publisher", "Publisher:", new JTextField());
		this.addInput("editors", "Editors:", new JTextField(12));
		this.addInput("paper_title", "Paper Title:", new JTextField(12));
		this.addInput("journal_title", "Journal Title:", new JTextField(12));
		this.addInput("volume", "Volume:", new JFormattedTextField(NumberFormat.getIntegerInstance()));
		
		this.main_panel.add(this.input_panel);
		
		this.main_panel.add(new JSeparator());
		
		this.addButtons(this.events.save_paper, this.events.close_handler);
		
		this.pack();
	}
	
	public NewPaperDialogEvents createEventManager(PaperModel model) {
		return new NewPaperDialogEvents(model, this);
	}
}
