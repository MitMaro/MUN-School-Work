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

public class User implements Serializable {
	private static final long serialVersionUID = 8254267643754528824L;

	public enum UserType {
		ADMIN, VERIFIER, BROWSER, EDITOR
	};

	private String username;
	private String password;
	private UserType user_type;
	private PersonalList personal_list;

	public User(String username, String password, UserType user_type) {
		this.username = username;
		this.password = password;
		this.user_type = user_type;
		this.personal_list = new PersonalList();
	}

	public boolean validate(String user, String pass) {
		boolean result = false;
		if (user.compareTo(this.username) == 0
				&& pass.compareTo(this.password) == 0) {
			result = true;
		}
		return result;
	}

	public void setUserType(UserType newType) {
		this.user_type = newType;
	}

	public UserType getUserType() {
		return this.user_type;
	}

	public void setPassword(String oldPassword, String newPassword) {
		if (this.password.compareTo(oldPassword) == 0) {
			this.password = newPassword;
		}
	}

	public void setUserName(String passWord, String newUserName) {
		if (this.password.compareTo(passWord) == 0) {
			this.username = newUserName;
		}
	}

	public String getUserName() {
		return this.username;
	}

	public PersonalList getPersonalList() {
		return this.personal_list;
	}
	
	@Override
	public boolean equals(Object e) {
		if (e instanceof User) {
			User user = (User)e;
			return user.username.equals(this.username);
		}
		return false;
	}
	
}
