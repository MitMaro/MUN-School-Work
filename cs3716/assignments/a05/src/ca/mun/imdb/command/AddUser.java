/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #5                       //
//  Program File Name: IMDB.java                               //
//         Login Name: oram                                    //
//       Student Name: Oram, Timothy A.                        //
//       Student Name: Alfosool Saheb, Ali Mohammad            //
//       Student Name: Chen, Shike                             //
//       Student Name: Zakzouk, Omar S.                        //
/////////////////////////////////////////////////////////////////

package ca.mun.imdb.command;

import ca.mun.imdb.Application;
import ca.mun.imdb.IMDBException;
import ca.mun.imdb.InvalidArgumentException;
import ca.mun.imdb.entity.User;
import ca.mun.imdb.entity.User.UserType;

public class AddUser extends Command {
	
	private Application app;
	
	public AddUser(Application application) {
		this.app = application;
	}
	
	@Override
	public void call(String[] args) throws IMDBException {
		
		if (args.length != 3) {
			throw new InvalidArgumentException("Invalid number of arguments provided");
		}
		
		User.UserType user_type;
		
		if (args[0].equals("admin")) {
			user_type = UserType.ADMIN;
		} else if (args[0].equals("editor")) {
			user_type = UserType.EDITOR;
		} else if (args[0].equals("verifier")) {
			user_type = UserType.VERIFIER;
		} else if (args[0].equals("browser")) {
			user_type = UserType.BROWSER;
		} else {
			throw new InvalidArgumentException("Invalid user type provided");
		}
			
		
		String username = args[1];
		String password = args[2];
		
		this.app.addUser(user_type, username, password);
	}
}