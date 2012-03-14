package ca.mitmaro.ldb;

import java.util.Comparator;

import ca.mitmaro.ldb.entity.Paper;

public class PaperComparePID implements Comparator<Paper> {
	public int compare(Paper p1, Paper p2) {
		return p1.getId().compareTo(p2.getId());
	}
}
