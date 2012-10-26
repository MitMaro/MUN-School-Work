/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb;

import java.util.Comparator;

import ca.mitmaro.ldb.entity.Paper;

/**
 * Paper PID comparator
 *
 * @author Tim Oram (MitMaro)
 */
public class PaperComparePID implements Comparator<Paper> {
	@Override
	public int compare(Paper p1, Paper p2) {
		return p1.getId().compareTo(p2.getId());
	}
}
