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

public class ListHistory implements Serializable, Iterable<ListHistory.HistoryEntry> {
	private static final long serialVersionUID = 6810995157742920949L;

	public static class HistoryEntry implements Serializable {

		private static final long serialVersionUID = -8135794863228914979L;

		public enum Action {
			CREATE, EDIT, VERIFY
		};

		private User user;
		private Action action;
		private Date date;

		private HistoryEntry(User user, Action action) {
			this.user = user;
			this.action = action;
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
				return String.format("%s created list on %s", this.user.getUserName(), this.date);
			} else if (this.action == Action.EDIT) {
				return String.format("%s edited list on %s", this.user.getUserName(), this.date);
			} else {
				return String.format("%s verified list on %s", this.user.getUserName(), this.date);
			}
		}
		
	}

	private ArrayList<HistoryEntry> history_entries = new ArrayList<HistoryEntry>();

	public void addHistory(User user, HistoryEntry.Action action) {
		this.history_entries.add(new HistoryEntry(user, action));
	}

	@Override
	public Iterator<HistoryEntry> iterator() {
		return this.history_entries.iterator();
	}
}
