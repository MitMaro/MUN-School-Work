/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A class that contains various methods for working with files
 * 
 * @author Tim Oram (MitMaro)
 */
public class FileUtil {
	
	/**
	 * 
	 * Wrapper around the File class
	 * 
	 * @param file_name The file path
	 * @return An instance of the file with the file_name
	 */
	public File openFile(String file_name) {
		return new File(file_name);
	}
	
	/**
	 * Gets a buffered reader for the provided file_name
	 * @param file_name The file path
	 * @return A buffered reader ready to be read
	 * @throws IOException
	 */
	public BufferedReader getBufferedReader(String file_name) throws IOException {
		return this.getBufferedReader(this.openFile(file_name));
	}
	
	/**
	 * Gets a buffered reader for the provided file instance
	 * @param file A open file instance
	 * @return A buffered reader wrapping the file instance
	 * @throws IOException
	 */
	public BufferedReader getBufferedReader(File file) throws IOException {
		return new BufferedReader(new FileReader(file));
	}

	/**
	 * Gets an object output steam for the provided file path
	 * 
	 * @param file_name The file path
	 * @return A object output stream useful for object serialization
	 * @throws IOException
	 */
	public ObjectOutputStream getBufferedObjectWriter(String file_name) throws IOException {
		return this.getBufferedObjectWriter(new File(file_name));
	}
	
	/**
	 * Gets an object output stream for the provided file instance
	 * 
	 * @param file A file instance
	 * @return
	 * @throws IOException
	 */
	public ObjectOutputStream getBufferedObjectWriter(File file) throws IOException {
		return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
	}
	
	/**
	 * Gets a object input stream from a file path
	 * 
	 * @param file_name The file path
	 * @return A object input stream ready for reading
	 * @throws IOException
	 */
	public ObjectInputStream getBufferedObjectReader(String file_name) throws IOException {
		return this.getBufferedObjectReader(new File(file_name));
	}
	
	/**
	 * Gets a object input stream wrapping the given file instance
	 * 
	 * @param file A file instance
	 * @return A wrapped object output stream
	 * @throws IOException
	 */
	public ObjectInputStream getBufferedObjectReader(File file) throws IOException {
		return new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
	}
	
}
