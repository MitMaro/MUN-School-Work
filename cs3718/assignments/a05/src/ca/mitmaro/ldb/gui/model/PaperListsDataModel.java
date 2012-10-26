package ca.mitmaro.ldb.gui.model;

import javax.swing.table.AbstractTableModel;

import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.exception.InvalidListException;

public class PaperListsDataModel extends AbstractTableModel {

	private static final long serialVersionUID = 1555419607767947102L;

	private Paper[] papers;
	
	private String list_name;
	
	private String[] columns = {"Paper ID", "Title", "Author", "Year"};
	
	public PaperListsDataModel(String list_name, Paper[] papers) {
		this.setList(list_name, papers);
	}
	
	public void setList(String list_name, Paper[] papers) {
		this.list_name = list_name;
		this.papers = papers;
		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return this.columns.length; // pid, title, author first, author last, year, type
	}
	
	@Override
	public String getColumnName(int index) {
		return this.columns[index];
	}
	
	@Override
	public int getRowCount() {
		return this.papers.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		try {
			String list_name = this.list_name;
			
			// work around issues with the system I use for storing papers
			if (!this.papers[row].inList(this.list_name)) {
				list_name = this.papers[row].getList();
			}
			
			if (col == 0) { // pid
				return this.papers[row].getId();
			} else if (col == 1) { // title
				return this.papers[row].getTitle(list_name);
			} else if (col == 2) { //author
				return String.format(
					"%s, %s",
					this.papers[row].getAuthorLast(list_name),
					this.papers[row].getAuthorFirst(list_name)
				);
			} else if (col == 3) { // year
				return this.papers[row].getYear(list_name);
			}
		} catch (InvalidListException e) {
			return null;
		}
		return null;
	}
}
