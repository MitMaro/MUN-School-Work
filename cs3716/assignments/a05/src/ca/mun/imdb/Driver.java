/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #5                       //
//  Program File Name: IMDB.java                               //
//         Login Name: oram                                    //
//       Student Name: Oram, Timothy A.                        //
//       Student Name: Alfosool Saheb, Ali Mohammad            //
//       Student Name: Chen, Shike                             //
//       Student Name: Zakzouk, Omar S.                        //
/////////////////////////////////////////////////////////////////

package ca.mun.imdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;

import ca.mun.imdb.command.AddUser;
import ca.mun.imdb.command.Command;
import ca.mun.imdb.command.DeleteUser;
import ca.mun.imdb.command.DisplayDatabase;
import ca.mun.imdb.command.DisplayPersonalList;
import ca.mun.imdb.command.DisplayRecommendation;
import ca.mun.imdb.command.EditRecommendation;
import ca.mun.imdb.command.Help;
import ca.mun.imdb.command.ListDatabase;
import ca.mun.imdb.command.ListRecommendation;
import ca.mun.imdb.command.LoadRecommendation;
import ca.mun.imdb.command.MergeRecommendation;
import ca.mun.imdb.command.SearchDatabase;
import ca.mun.imdb.command.SearchRecommendation;
import ca.mun.imdb.command.VerifyRecommendation;
import ca.mun.imdb.command.ViewHistoryDatabase;
import ca.mun.imdb.command.ViewHistoryRecommendation;
import ca.mun.imdb.entity.User;
import ca.mun.imdb.entity.User.UserType;

//This is the main class of this program. It will take user commands and run them until the user type 'exit' or cancel the program using Ctrl+c.
public class Driver {
	private final int loginLimit = 3;

	private Application application;

	private User user;

	private LinkedHashMap<String, Command> commands = new LinkedHashMap<String, Command>();

	public Driver(Application application) {
		this.application = application;
	}

	public void run() {
		String input;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// do first run check
		this.checkFirstRun();
		
		// if login fails exit
		if (!this.userLogin()) {
			return;
		}

		System.out.println("\nLogin Successful");
		System.out
				.println("Enter a command. Enter 'help' to see the available commands.\n");

		// this while loop is where is users enter their commands
		// and it will go on until the user terminate the program by
		// typing "exit"
		try {
			System.out.print("$ ");
			while (!(input = br.readLine()).equals("exit")) {
				this.processCommand(input);
				System.out.print("$ ");
			}
		} catch (IOException e) {
			System.out.println("Unknown IO Error");
			e.printStackTrace();
		}

		System.out.println("System shutting down.");
	}
	private void processCommand(String input) {
		
		// skip empty input
		if (input.isEmpty()) {
			return;
		}
		
		String tmp[] = input.split("\\s+", 2);
		String command = tmp[0];
		
		String args[];
		if (tmp.length != 2) { // if other arguments
			args = new String[0];
		} else {
			args = tmp[1].split("\\s+");
		}
		
		
		if (this.commands.containsKey(command)) {
			Command cmd = this.commands.get(command);
			try {
				cmd.call(args);
			} catch (InvalidArgumentException e) {
				System.out.println(e.getMessage());
			} catch (InvalidDatabaseException e) {
				System.out.println("An invalid database name was provided");
			} catch (InvalidListExcpetion e) {
				System.out.println("An invalid list name was provided");
			} catch (IMDBException e) {
				System.out.println("An unkown error has occured");
			} catch (InputMismatchException e) {
				System.out.println("Invalid input provided");
			}
		} else {
			System.out.println("An unkowwn command was provided.");
		}
	}
	
	private void checkFirstRun() {
		// there are users, we can quit
		if (this.application.hadUsers()) {
			return;
		}
		System.out.println("No users found. Creating Administrator Account.");
		
		while (true) {
		
			Scanner in = new Scanner(System.in);
			
			System.out.print("Username: ");
			String username = in.nextLine();
			
			System.out.print("Password: ");
			String password = in.nextLine();
			
			System.out.print("Confirm Password: ");
			
			// check password confirm
			if (!in.nextLine().equals(password)) {
				System.out.println("Passwords do no match");
				continue;
			}
			
			this.application.addUser(UserType.ADMIN, username, password);
			break;
		}
	}

	private boolean userLogin() {

		int loginCounter = 0;
		String username;
		String password;

		do {
			// getting the username and password.
			Scanner s = new Scanner(System.in);
			System.out.println("\n-- SYSTEM LOGIN --");
			System.out.print("Username: ");
			username = s.nextLine();
			System.out.print("Password: ");
			password = s.nextLine();

			this.user = this.application.validateUserPassword(username,
					password);

			// valid login
			if (this.user != null) {
				this.setUserCommands();
				return true;
			}

			loginCounter++;
			System.out.println("Incorrect Username or Password");

		} while (loginCounter < this.loginLimit);

		System.out.println("Too many attempts. System will now exit.");

		return false;
	}

	private void setUserCommands() {
		switch (this.user.getUserType()) {
			case ADMIN:
				this.commands.put("adduser", new AddUser(this.application));
				this.commands.put("deluser", new DeleteUser(this.application));
				break;
			case VERIFIER:
				this.commands.put("listrec", new ListRecommendation(this.application));
				this.commands.put("listdb", new ListDatabase(this.application));
				this.commands.put("displayrec", new DisplayRecommendation(this.application));
				this.commands.put("searchrec", new SearchRecommendation(this.application));
				this.commands.put("searchdb", new SearchDatabase(this.application, false));
				this.commands.put("verifyrec", new VerifyRecommendation(this.application));
				this.commands.put("mergerec", new MergeRecommendation(this.application));
				this.commands.put("viewhistorydb", new ViewHistoryDatabase(this.application));
				this.commands.put("viewhistoryrec", new ViewHistoryRecommendation(this.application));
				break;
			case BROWSER:
				this.commands.put("listdb", new ListDatabase(this.application));
				this.commands.put("searchdb", new SearchDatabase(this.application, true));
				this.commands.put("displaydb", new DisplayDatabase(this.application));
				this.commands.put("displayplist", new DisplayPersonalList(this.application));
				break;
			case EDITOR:
				this.commands.put("loadrec", new LoadRecommendation(this.application));
				this.commands.put("listrec", new ListRecommendation(this.application));
				this.commands.put("listdb", new ListDatabase(this.application));
				this.commands.put("displayrec", new DisplayRecommendation(this.application));
				this.commands.put("searchrec", new SearchRecommendation(this.application));
				this.commands.put("searchdb", new SearchDatabase(this.application, false));
				this.commands.put("viewhistorydb", new ViewHistoryDatabase(this.application));
				this.commands.put("viewhistoryrec", new ViewHistoryRecommendation(this.application));
				this.commands.put("editrec", new EditRecommendation(this.application));
				break;
		}
		
		ArrayList<String> cmds = new ArrayList<String>(this.commands.keySet());
		cmds.add("help");
		cmds.add("exit");
		this.commands.put("help", new Help(cmds.toArray(new String[0])));
	}
}