package ca.mitmaro.ldb;

import java.util.Comparator;

import ca.mitmaro.ldb.entity.Paper;

public class PaperCompareAuthor implements Comparator<Paper> {
	private String list_name;
	public PaperCompareAuthor(String list_name) {
		this.list_name = list_name;
	}
	public int compare(Paper p1, Paper p2) {
		String author_full_1 = p1.getAuthorLast(this.list_name) + p1.getAuthorFirst(this.list_name);
		String author_full_2 = p2.getAuthorLast(this.list_name) + p2.getAuthorFirst(this.list_name);
		return author_full_1.compareTo(author_full_2);
	}
}
