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

import java.util.ArrayList;
import java.util.LinkedHashMap;

import ca.mun.imdb.IMDBException;

public class Help extends Command {
	
	private ArrayList<String> commands;
	
	public Help(String[] cmds) {
		LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
		
		commands.put("help", "help: Displays this message ");
		commands.put("loadrec", "loadrec LIST-FILE as LIST-NAME: Loads a recommendation list, LIST-FILE, and stores it under LIST-NAME");
		commands.put("mergerec", "mergerec LIST-NAME with DB-NAME: Merges the list, LIST-NAME, with the database, DB-NAME");
		commands.put("listrec", "listrec: Lists the recommendation lists in the sytem.");
		commands.put("listdb", "listdb: Lists the databases in the system");
		commands.put("displaydb", "displaydb DB-NAME: Displays the database, DB-NAME\n\tdisplaydb sorted DB-NAME: Displays the sorted database, DB-NAME");
		commands.put("editrec", "editrec LIST-NAME: Edit recommendation list, LIST-NAME");
		commands.put("verifyrec", "verifyrec LIST-NAME: Verifies recommendation list, LIST-NAME");
		commands.put("viewhistorydb", "viewhistorydb DB-NAME: View the history for database, DB-NAME");
		commands.put("viewhistoryrec", "viewhistoryrec LIST-NAME: View the history for the recommendation list, LIST-NAME"); 
		commands.put("displayplist", "displayplist: Display your personal movie list");
		commands.put("displayrec", "displayrec LIST-NAME: Display recommendation list, LIST-NAME.\n\tdisplayrec sorted LIST-NAME: Display sorted recommendation list, LIST-NAME");
		commands.put("searchdb", "searchdb DB-NAME for title TITLE YEAR: Search database, DB-NAME, for movie matching TITLE and YEAR\n\tsearchdb DB-NAME for genre GENRE-PHRASE: Search database, DB-NAME, for movie matching GENRE-PHRASE.");
		commands.put("searchrec", "searchrec LIST-NAME for title TITLE YEAR: Search recommendation list, LIST-NAME, for movie matching TITLE and YEAR\n\tsearchrec LIST-NAME for genre GENRE-PHRASE: Search recommendation list, LIST-NAME, for movie matching GENRE-PHRASE.");
		commands.put("adduser", "adduser {\"editor\",\"verifier\",\"browser\",\"admin\"} USER-NAME USER-PASSWORD: Create user with provided username, password and user type");
		commands.put("deluser", "deluser USER-NAME: Delete user, USER-NAME");
		commands.put("exit", "exit: exit the system");
		
		
		this.commands = new ArrayList<String>();
		for (String cmd: cmds) {
			if (!commands.containsKey(cmd)) {
				throw new RuntimeException(String.format("Invalid command name: %s", cmd));
			}
			
			this.commands.add(commands.get(cmd));
		}
		
	}
	
	public void call(String[] args) throws IMDBException {
		System.out.println("System Operations:");
		
		for (String line: this.commands) {
			System.out.println("\t" + line);
		}
	}
}