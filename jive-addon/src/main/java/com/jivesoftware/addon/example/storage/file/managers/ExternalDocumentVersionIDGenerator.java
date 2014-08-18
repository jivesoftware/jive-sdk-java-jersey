/**
 * 
 */
package com.jivesoftware.addon.example.storage.file.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author david.nicholls
 *
 */
public class ExternalDocumentVersionIDGenerator {
	
	private static final String EXTERNAL_DOC_DIR = "./external-id/db/";
	private static final String EXTERNAL_DOC_FILE = "external-version-id-last.store";
	
	private static final String LAST_ID = "last-version-id";
	
	public static synchronized Long getNextID() {
		Long nextID = readNextID();
		if (nextID == null) {
			nextID = 1L;
		}
		writeLastID(nextID);
		return nextID;
	}

	private static Long readNextID() {
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
			File dir = new File(EXTERNAL_DOC_DIR);
			File file = new File(dir, EXTERNAL_DOC_FILE);
			dir.mkdirs();
			file.createNewFile();
			input = new FileInputStream(file);
	 
			// load a properties file
			prop.load(input);
	 
			// get the property value and print it out			
			Long lastID = Long.parseLong((prop.getProperty(LAST_ID, "1")));
			return lastID + 1;
	 
		} catch (IOException ex) {		
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void writeLastID(Long id) {
		Properties prop = new Properties();
		OutputStream output = null;
	 
		try {
			File dir = new File(EXTERNAL_DOC_DIR);
			File file = new File(dir, EXTERNAL_DOC_FILE);
			output = new FileOutputStream(file);
	 
			// set the properties value
			prop.setProperty(LAST_ID, Long.toString(id));
	 
			// save properties to project root folder
			prop.store(output, null);
	 
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
		}
	}
	
}
