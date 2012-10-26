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
 * An annotation to specify a method as a CLI command
 * 
 * @author Tim Oram (MitMaro)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	/**
	 * The name of this command
	 */
	public String name();
}
