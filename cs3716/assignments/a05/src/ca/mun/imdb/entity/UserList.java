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

import java.util.Iterator;
import java.util.LinkedList;
import java.io.Serializable;

import ca.mun.imdb.entity.User.UserType;

public class UserList implements Serializable, Iterable<User> {
	private static final long serialVersionUID = 7791615428298699365L;
	private LinkedList<User> users;

	public UserList() {
		this.users = new LinkedList<User>();
	}

	private boolean isExist(String user) {
		boolean result = false;
		for (int i = 0; i < this.users.size(); i++) {
			if (user.compareTo(this.users.get(i).getUserName()) == 0) {
				result = true;
			}
		}
		return result;
	}

	public User validate(String username, String password) {
		User result = null;
		for (int i = 0; i < this.users.size(); i++) {
			User temp = this.users.get(i);
			if (temp.validate(username, password) == true) {
				result = temp;
			}
		}
		return result;
	}

	public boolean addUser(User temp) {
		boolean added = false;
		if (this.isExist(temp.getUserName()) == false) {
			this.users.add(temp);
			added = true;
		}
		return added;
	}

	public boolean addUser(String Username, String Password, UserType userType) {
		boolean added = false;
		if (this.isExist(Username) == false) {
			User temp = new User(Username, Password, userType);
			this.addUser(temp);
			added = true;
		}
		return added;
	}

	public boolean delUser(String temp) {
		boolean found = false;
		for (int i = 0; i < this.users.size(); i++) {
			if (temp.compareTo(this.users.get(i).getUserName()) == 0) {
				this.users.remove(i);
				found = true;
			}
		}
		return found;
	}

	public User getItem(int pos) {
		return this.users.get(pos);
	}

	@Override
	public Iterator<User> iterator() {
		return this.users.iterator();
	}

	public void removeUser(User user) {
		this.users.remove(user);
	}

	public int getSize() {
		return this.users.size();
	}
}
