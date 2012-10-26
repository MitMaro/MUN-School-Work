package ca.mitmaro.ldb.entity;

import java.io.Serializable;

public class PaperList  implements Serializable {
	
	private static final long serialVersionUID = -7924301865486050429L;

	public enum SortOrder {NATURAL, AUTHOR, PID, YEAR, TITLE};
	
	private String list_name;
	
	private SortOrder order;
	
	private boolean asc;
	
	public PaperList(String name) {
		this.list_name = name;
		this.order = SortOrder.NATURAL;
		this.asc = true;
	}
	
	public SortOrder getSortOrder() {
		return this.order;
	}
	
	public void setSortOrder(SortOrder order, boolean asc) {
		this.order = order;
		this.asc = asc;
	}
	
	public String getListName() {
		return this.list_name;
	}
	
	public boolean isSortAscending() {
		return this.asc;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof PaperList && ((PaperList) obj).list_name.equals(this.list_name);
	}
	
	@Override
	public int hashCode() {
		return this.list_name.hashCode();
	}
	
}
