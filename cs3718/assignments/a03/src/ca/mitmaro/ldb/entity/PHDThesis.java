/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import ca.mitmaro.lang.StringUtils;

public class PHDThesis extends Paper {
	
	private static class Data {
		private String address;
		
		public Data(String address) {
			this.address = address;
		}
		
		public Data(Data data) {
			this.address = data.address;
		}

		protected String getAddress() {
			return this.address;
		}
		
		protected void setAddress(String address) {
			this.address = address;
		}
		
	}
	
	private List<Data> data_sets; 
	
	public PHDThesis(String id, String type) {
		super(id, type);
		this.data_sets = new List<Data>();
	}
	
	public void addToList(String new_list, String old_list) {
		super.addToList(new_list, old_list);
		this.data_sets.put(new_list, new Data(this.data_sets.get(old_list)));
	}
	
	public void setDataSet (
		String list_name, String title, String author_first,
		String author_last, int year, String address
	) {
		this.data_sets.put(list_name, new Data(address));
		super.setDataSet(list_name, title, author_first, author_last, year);
	}
	
	public String getAddress(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getAddress();
		}
		return null;
	}
	
	@Override
	public void updateField(String list_name, String field_name, String value) {
		super.updateField(list_name, field_name, value);
		
		if (field_name.equals("address")) {
			this.data_sets.get(list_name).setAddress(value);
		}
		
	}
	
	@Override
	public String getPrintable(String list_name, String padding) {
		return String.format(
			"%s%s: %s, %s (%d) %s\n%s\t%s.\n",
			padding,
			this.getId(),
			this.getAuthorLast(list_name),
			this.getAuthorFirst(list_name),
			this.getYear(list_name),
			StringUtils.truncate(this.getTitle(list_name), 35),
			padding,
			this.getAddress(list_name)
		);
	}
	
	
}
