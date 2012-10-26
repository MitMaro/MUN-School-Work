/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.command.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation to specify a help message for a command method
 * 
 * @author Tim Oram (MitMaro)
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Help {
	
	/**
	 * The usage string for the command
	 */
	public String usage();
	
	/**
	 * The help message for this usage
	 */
	public String help();
}
