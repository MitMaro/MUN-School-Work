/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #5                       //
//  Program File Name: IMDB.java                               //
//         Login Name: oram                                    //
//       Student Name: Oram, Timothy A.                        //
//              MUN #:                                         //
//       Student Name: Alfosool Saheb, Ali Mohammad            //
//              MUN #:                                         //
//       Student Name: Chen, Shike                             //
//              MUN #:                                         //
//       Student Name: Zakzouk, Omar S.                        //
//              MUN #:                                         //
/////////////////////////////////////////////////////////////////
import java.io.IOException;

import ca.mun.imdb.Application;
import ca.mun.imdb.Driver;


public class IMDB {
	public static void main (String[] args) {
		
		
		Application application = null;
		
		try {
			application = Application.loadState();
		} catch (IOException e) {
			System.out.println("Unexpected IO error during state read");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Unexpected IO error during state read");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		if (application == null) {
			application = new Application();
		}
		
		Driver commandline_driver = new Driver(application);
		commandline_driver.run();
		try {
			application.saveState();
		} catch (IOException e) {
			System.out.println("Unexpected IO error during state write");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
