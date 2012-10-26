package ca.mitmaro.ldb.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;

import ca.mitmaro.ldb.gui.controller.MainPanelEvents;
import ca.mitmaro.ldb.gui.model.MainModel;
import ca.mitmaro.ldb.gui.model.PaperListsDataModel;

public class MainPanel extends JPanel {
	
	private static final long serialVersionUID = -6309809698336061949L;
	
	// components
	public final JXTable table_list_papers;
	public final JXTable table_paper_citations;
	public final JXTable table_paper_references;
	
	public final JXList list_paper_lists;
	
	public final JButton button_add_list;
	public final JButton button_delete_list;
	public final JButton button_new_paper;
	public final JButton button_delete_paper;
	public final JButton button_edit_paper;
	public final JButton button_load_paper_reference;
	public final JButton button_add_paper_reference;
	public final JButton button_remove_paper_reference;
	public final JButton button_load_paper_citation;
	public final JButton button_add_paper_citation;
	public final JButton button_remove_paper_citation;
	
	public final JComboBox combobox_paper_lists;
	
	public final JTextArea textarea_paper;

	public final PaperListsDataModel papers_data_model;
	public final PaperListsDataModel citations_data_model;
	public final PaperListsDataModel references_data_model;
	
	private MainModel model;
	
	private final int left_start_index = 4;
	
	private MainPanelEvents events;
	
	public MainPanel(MainModel model) {
		this.model = model;
		this.setLayout(new GridBagLayout());
		
		this.papers_data_model = new PaperListsDataModel(this.model.getCurrentList(), this.model.getListPapers());
		this.citations_data_model = new PaperListsDataModel(this.model.getCurrentList(), this.model.getCitations());
		this.references_data_model = new PaperListsDataModel(this.model.getCurrentList(), this.model.getReferences());
		
		this.table_list_papers = new JXTable(this.papers_data_model);
		this.combobox_paper_lists = new JComboBox();
		this.button_add_list = new JButton("New List");
		this.button_delete_list = new JButton("Delete List");
		this.button_new_paper = new JButton("New Paper");
		this.button_delete_paper = new JButton("Remove Paper");
		this.textarea_paper = new JTextArea("Please Select a Paper");
		this.button_edit_paper = new JButton("Edit Paper");
		this.table_paper_citations = new JXTable(this.citations_data_model);
		this.button_load_paper_citation = new JButton("Load");
		this.button_add_paper_citation = new JButton("Add");
		this.button_remove_paper_citation = new JButton("Remove");
		this.table_paper_references = new JXTable(this.references_data_model);
		this.button_load_paper_reference = new JButton("Load");
		this.button_add_paper_reference = new JButton("Add");
		this.button_remove_paper_reference = new JButton("Remove");
		this.list_paper_lists = null;
		
		this.setEventHandlers();
		
		this.placeListDropdown();
		this.placeListControls();
		this.placeTable();
		this.placeListPaperControls();
		this.placeSeperator(3, 0, 11, JSeparator.VERTICAL);
		this.placePaperText();
		this.placePaperControls();
		this.placeSeperator(this.left_start_index, 3, 3, JSeparator.HORIZONTAL);
		this.placePaperCitations();
		this.placeCitationControls();
		this.placeSeperator(this.left_start_index, 7, 3, JSeparator.HORIZONTAL);
		this.placePaperReferences();
		this.placeReferenceControls();
		
		// border setup
		this.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10),
			BorderFactory.createEtchedBorder()
		));
	}
	
	private GridBagConstraints getGridBagConstraints() {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 3, 1, 3);
		return c;
	}
	
	private void setEventHandlers() {
		this.events = new MainPanelEvents(model, this);
		this.combobox_paper_lists.addActionListener(this.events.lists_change);
		this.button_add_list.addActionListener(this.events.list_add);
		this.button_delete_list.addActionListener(this.events.list_delete);
		this.button_new_paper.addActionListener(this.events.new_paper);
		this.table_list_papers.getSelectionModel().addListSelectionListener(this.events.list_select);
		this.button_delete_paper.addActionListener(this.events.delete_paper);
		this.button_load_paper_citation.addActionListener(this.events.set_cwp_citations);
		this.button_load_paper_reference.addActionListener(this.events.set_cwp_references);
		this.table_paper_citations.getSelectionModel().addListSelectionListener(this.events.citation_list_change);
		this.table_paper_references.getSelectionModel().addListSelectionListener(this.events.reference_list_change);
		this.button_remove_paper_citation.addActionListener(this.events.remove_citation);
		this.button_remove_paper_reference.addActionListener(this.events.remove_reference);
		this.button_edit_paper.addActionListener(this.events.edit_paper);
	}
	
	public MainPanelEvents getEventHandler() {
		return this.events;
	}
	
	private void placeSeperator(int gridx, int gridy, int size, int type) {
		GridBagConstraints c = this.getGridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;
		if (type == JSeparator.VERTICAL) {
			c.gridheight = size;
		} else if (type == JSeparator.HORIZONTAL) {
			c.gridwidth = size;
		}
		this.add(new JSeparator(type), c);
	}
	
	private void placeListDropdown() {
		this.combobox_paper_lists.removeAllItems();
		for (String s: this.model.getLists()) {
			this.combobox_paper_lists.addItem(s);
		}
		GridBagConstraints c = this.getGridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.combobox_paper_lists, c);
	}
	
	private void placeListControls() {
		this.button_add_list.setToolTipText("Create a new list");
		
		this.button_delete_list.setToolTipText("Delete selected list");
		this.button_delete_list.setEnabled(false);
		
		GridBagConstraints c = this.getGridBagConstraints();
		c.gridy = 0;
		c.gridx = 1;
		c.fill = GridBagConstraints.BOTH;
		
		this.add(this.button_add_list, c);
		c.gridx = 2;
		this.add(this.button_delete_list, c);
	}
	
	private void placeTable() {
		
		// table options
		this.table_list_papers.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, new Color(255, 255, 225), null));
		this.table_list_papers.setColumnSelectionAllowed(false);
		this.table_list_papers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// layout options
		GridBagConstraints c = this.getGridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.gridheight = 9;
		c.weighty = 1.0;
		c.weightx = 0.6;
		c.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(this.table_list_papers), c);
		
		// pack columns
		this.table_list_papers.packAll();
	}
	
	private void placeListPaperControls() {
		this.button_new_paper.setToolTipText("Create a new paper");

		this.button_delete_paper.setToolTipText("Remove selected paper from this list");
		this.button_delete_paper.setEnabled(false);

		GridBagConstraints c = this.getGridBagConstraints();
		c.gridy = 10;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		this.add(this.button_new_paper, c);
		c.gridx = 2;
		this.add(this.button_delete_paper, c);
	}
	
	private void placePaperText() {
		// layout options
		GridBagConstraints c = this.getGridBagConstraints();
		c.gridx = this.left_start_index + 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		this.add(new JLabel("Current Working Paper"), c);
		
		this.textarea_paper.setEditable(false);
		this.textarea_paper.setLineWrap(true);
		this.textarea_paper.setOpaque(false);
		this.textarea_paper.setWrapStyleWord(true);
		this.textarea_paper.setMinimumSize(new Dimension(400, 75));
		this.textarea_paper.setBorder(BorderFactory.createEtchedBorder());
		c.gridy = 1;
		this.add(this.textarea_paper, c);
	}
	
	private void placePaperControls() {
		GridBagConstraints c = this.getGridBagConstraints();
		c.gridx = this.left_start_index + 1;
		c.gridwidth = 2;
		c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		this.button_edit_paper.setEnabled(false);
		this.add(this.button_edit_paper, c);
	}

	private void placePaperCitations() {

		
		// layout options
		GridBagConstraints c = this.getGridBagConstraints();
		c.gridx = this.left_start_index;
		c.gridy = 4;
		c.gridwidth = 3;
		c.weightx = 0.4;
		c.fill = GridBagConstraints.BOTH;
		this.add(new JLabel("Paper Citations"), c);
		
		this.table_paper_citations.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, new Color(255, 255, 225), null));
		this.table_paper_citations.setColumnSelectionAllowed(false);
		this.table_paper_citations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		c.weighty = 0.5;
		c.gridy = 5;
		this.add(new JScrollPane(this.table_paper_citations), c);
		
		// pack columns
		this.table_paper_citations.packAll();
	}
	
	private void placeCitationControls() {
		GridBagConstraints c = this.getGridBagConstraints();
		c.gridx = this.left_start_index;
		c.gridy = 6;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.EAST;
		this.button_load_paper_citation.setToolTipText("Set selected citation as current working paper");
		this.button_load_paper_citation.setEnabled(false);
		this.add(this.button_load_paper_citation, c);

		c.weightx = 0;
		c.gridx++;
		this.button_add_paper_citation.setToolTipText("Add citation to current paper");
		this.button_add_paper_citation.setEnabled(false);
		this.add(this.button_add_paper_citation, c);
		
		c.gridx++;
		this.button_remove_paper_citation.setToolTipText("Remove selected citation from current paper");
		this.button_remove_paper_citation.setEnabled(false);
		this.add(this.button_remove_paper_citation, c);
	}

	private void placePaperReferences() {
		GridBagConstraints c = this.getGridBagConstraints();
		c.gridx = this.left_start_index;
		c.gridy = 8;
		c.gridwidth = 3;
		c.weightx = 0.4;
		c.fill = GridBagConstraints.BOTH;
		this.add(new JLabel("Paper References"), c);
		
		this.table_paper_references.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, new Color(255, 255, 225), null));
		this.table_paper_references.setColumnSelectionAllowed(false);
		this.table_paper_references.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		c.weighty = 0.5;
		c.gridy = 9;
		this.add(new JScrollPane(this.table_paper_references), c);
		
		// pack columns
		this.table_paper_references.packAll();
	}
	
	private void placeReferenceControls() {
		GridBagConstraints c = this.getGridBagConstraints();
		c.gridx = this.left_start_index;
		c.gridy = 10;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.EAST;
		this.button_load_paper_reference.setToolTipText("Set selected reference as current working paper");
		this.button_load_paper_reference.setEnabled(false);
		this.add(this.button_load_paper_reference, c);

		c.weightx = 0;
		c.gridx++;
		this.button_add_paper_reference.setToolTipText("Add citation to current paper");
		this.button_add_paper_reference.setEnabled(false);
		this.add(this.button_add_paper_reference, c);
		
		c.gridx++;
		this.button_remove_paper_reference.setToolTipText("Remove selected reference from current paper");
		this.button_remove_paper_reference.setEnabled(false);
		this.add(this.button_remove_paper_reference, c);
	}
	
}