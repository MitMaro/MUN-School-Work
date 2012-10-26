/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #5                       //
//  Program File Name: IMDB.java                               //
//         Login Name: oram                                    //
//       Student Name: Oram, Timothy A.                        //
//       Student Name: Alfosool Saheb, Ali Mohammad            //
//       Student Name: Chen, Shike                             //
//       Student Name: Zakzouk, Omar S.                        //
/////////////////////////////////////////////////////////////////

package ca.mun.imdb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class DatabaseHistory implements Serializable, Iterable<DatabaseHistory.HistoryEntry> {

	private static final long serialVersionUID = 1L;

	public static class HistoryEntry implements Serializable {

		private static final long serialVersionUID = 1L;

		public enum Action {
			CREATE, MERGE
		};

		private User user;
		private Action action;
		private Date date;
		private String list;

		private HistoryEntry(User user, String list, Action action) {
			this.user = user;
			this.action = action;
			this.list = list;
			this.date = new Date();
		}

		public User getUser() {
			return this.user;
		}

		public Action getAction() {
			return this.action;
		}

		public Date getDate() {
			return this.date;
		}
		
		@Override
		public String toString() {
			if (this.action == Action.CREATE) {
				return String.format("%s created new database, %s, on %s", this.user.getUserName(), this.list, this.date);
			} else {
				return String.format("%s merged in list, %s, on %s", this.user.getUserName(), this.list, this.date);
			}
		}
		
	}

	private ArrayList<HistoryEntry> history_entries = new ArrayList<HistoryEntry>();

	public void addHistory(User user, String listname, HistoryEntry.Action action) {
		this.history_entries.add(new HistoryEntry(user, listname, action));
	}

	@Override
	public Iterator<HistoryEntry> iterator() {
		return this.history_entries.iterator();
	}
}
