package ca.mitmaro.ldb.gui.view;

import java.awt.Dialog;
import java.awt.Frame;
	import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.DefaultFormatter;

import layout.SpringUtilities;

import ca.mitmaro.ldb.gui.controller.PaperDialogEvents;

public abstract class PaperDialog extends JDialog {
	
	private static final long serialVersionUID = 5583635761815292573L;
	
	protected static class RegexFormatter extends DefaultFormatter {
		
		private static final long serialVersionUID = -254094014881222112L;
		
		private Pattern pattern;

		public RegexFormatter(String pattern) {
			super();
			this.pattern = Pattern.compile(pattern);
		}

		public Object stringToValue(String text) throws ParseException {
			if (this.pattern != null) {
				if (pattern.matcher(text).matches()) {
					return super.stringToValue(text);
				}
				throw new ParseException("Pattern did not match", 0);
			}
			return text;
		}
	}
	
	public final String[] book_inputs = {
		"paper_id", "author_first", "author_last", "book_title", "address", "publisher",
		"book_title", "address", "publisher", "year"
	};
	public final String[] book_chapter_inputs = {
		"paper_id", "book_title", "address", "publisher", "chapter_title", "pages", "editors", "book_title",
		"address", "publisher", "chapter_title", "pages", "editors","author_first", "author_last", "year"
	};
	public final String[] conference_paper_inputs = {
		"paper_id", "book_title", "address", "publisher", "chapter_title", "pages", "editors", "book_title",
		"address", "publisher", "chapter_title", "pages", "editors", "author_first", "author_last", "year"
	};
	public final String[] journal_paper_inputs = {
		"paper_id","author_first", "author_last", "pages", "paper_title", "volume", "journal_title", "year"
	};
	public final String[] phd_thesis_inputs = {"paper_id", "author_first", "author_last", "title", "year", "address"};
	
	protected JPanel main_panel;
	protected JPanel input_panel;
	protected SpringLayout layout;
	
	private PaperDialogEvents events;
	
	private class ComponentPair {
		public final JComponent c1;
		public final JComponent c2;
		public ComponentPair(JComponent c1, JComponent c2) {
			this.c1 = c1;
			this.c2 = c2;
		}
	}

	protected LinkedHashMap<String, ComponentPair> inputs = new LinkedHashMap<String, ComponentPair>();
	
	protected JButton button_ok = new JButton("Save");
	protected JButton button_cancel = new JButton("Cancel");
	
	public PaperDialog(Frame parent, String title) {
		super(
			parent,
			title,
			Dialog.ModalityType.APPLICATION_MODAL
		);
		
		this.main_panel =  new JPanel();
		this.main_panel.setLayout(new BoxLayout(this.main_panel, BoxLayout.Y_AXIS));
		
		this.input_panel = new JPanel(new SpringLayout());
		this.input_panel.setOpaque(true);
		
		// border setup
		this.main_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.setContentPane(this.main_panel);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
	}
	
	protected void setEventsManager(PaperDialogEvents events) {
		this.events = events;
	}
	
	protected void disableInputs() {
		for (ComponentPair pair: this.inputs.values()) {
			pair.c1.setEnabled(false);
			pair.c2.setEnabled(false);
		}
	}
	
	protected void enableInput(String name) {
		if (this.inputs.containsKey(name)) {
			this.inputs.get(name).c1.setEnabled(true);
			this.inputs.get(name).c2.setEnabled(true);
		} else {
			throw new RuntimeException(String.format("And input with name, %s, does not exist.", name));
		}
	}
	
	protected void addInput(String name, String label_text, JTextField input) {
		JLabel label = new JLabel(label_text, JLabel.TRAILING);
		label.setAlignmentX(RIGHT_ALIGNMENT);
		input.setAlignmentX(RIGHT_ALIGNMENT);
		input.addKeyListener(this.events.new InputKeyListener(input, name) {});
		this.input_panel.add(label);
		this.input_panel.add(input);
		this.inputs.put(name, new ComponentPair(label, input));
	}
	
	protected void addButtons(ActionListener save_listener, ActionListener close_listener) {
		this.button_ok.setEnabled(false);
		this.button_ok.addActionListener(save_listener);
		this.button_ok.addActionListener(close_listener);
		this.button_cancel.setAlignmentX(CENTER_ALIGNMENT);
		this.button_cancel.addActionListener(close_listener);
		this.button_cancel.setAlignmentX(CENTER_ALIGNMENT);
		this.addComponents(this.button_ok, this.button_cancel);
	}
	
	protected JPanel addComponents(JComponent c1, JComponent c2 ){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(3, 5, 5, 3));
		panel.add(c1);
		panel.add(c2);
		this.main_panel.add(panel);
		return panel;
	}
	
	protected JPanel addComponent(JComponent c1) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(3, 5, 5, 3));
		panel.add(c1);
		this.main_panel.add(panel);
		return panel;
	}
	
	public void pack() {
		SpringUtilities.makeCompactGrid(this.input_panel, this.inputs.size(), 2, 0, 0, 3, 5);
		super.pack();
	}
	
}
