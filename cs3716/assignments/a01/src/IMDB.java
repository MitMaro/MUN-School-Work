/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////

import ca.mitmaro.imdb.Application;
import ca.mitmaro.imdb.ImdbCommandLine;
import ca.mitmaro.commandline.userinterface.NumberedMenu;
import ca.mitmaro.commandline.term.Terminal;

public class IMDB {
	public static void main(String[] args) {
		ImdbCommandLine cl = new ImdbCommandLine(new Application());
		System.exit(cl.mainloop());
	}
 }
