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

public class FileUtil {
	
	public File openFile(String file_name) {
		return new File(file_name);
	}
	
	public BufferedReader getBufferedReader(String file_name) throws IOException {
		return this.getBufferedReader(this.openFile(file_name));
	}
	
	public BufferedReader getBufferedReader(File file) throws IOException {
		return new BufferedReader(new FileReader(file));
	}

	public ObjectOutputStream getBufferedObjectWriter(String file_name) throws IOException {
		return this.getBufferedObjectWriter(new File(file_name));
	}
	
	public ObjectOutputStream getBufferedObjectWriter(File file) throws IOException {
		return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
	}
	
	public ObjectInputStream getBufferedObjectReader(String file_name) throws IOException {
		return this.getBufferedObjectReader(new File(file_name));
	}
	
	public ObjectInputStream getBufferedObjectReader(File file) throws IOException {
		return new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
	}
	
}
