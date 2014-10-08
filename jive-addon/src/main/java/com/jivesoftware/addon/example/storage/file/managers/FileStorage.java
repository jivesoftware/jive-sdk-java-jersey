/**
 * 
 */
package com.jivesoftware.addon.example.storage.file.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author david.nicholls
 *
 */
public class FileStorage {
	
	private static final Logger log = LoggerFactory.getLogger(FileStorage.class);

	private static final String FILE_STORE_LOCATION = "./filestore/";
	private static final String TRASH_FOLDER = "trash/";
	private static final String WORKSPACES_FOLDER = "workspaces/";
	private static final String FILES_FOLDER = "/files/";
	private static final String ATTACHMENTS_FOLDER = "/attachments/";
	
	public static boolean testConnectivity() {		
		File f = new File(FILE_STORE_LOCATION + "connectivitytest");
		log.info("Testing file write access to : " + f.getAbsolutePath());
		if (f.exists()) {
			if (!f.delete()) {
				return false;
			}
		}
		try {
			if (!f.createNewFile()) {
				return false;
			}
		} catch (IOException e) {
			log.error("Connectivity test failed due to : " + e.getMessage(), e);
			return false;
		}
		f.delete();
		return true;
	}
	
	public static void createWorkspace(String workspaceId) {
		File f = new File(getWorkspaceFolder(workspaceId));
		f.mkdirs();
	}
	
	public static Long uploadAttachment(String workspaceId, InputStream inputStream, String fileName, String externalID) {
		OutputStream outputStream = null;
		File file = null;
	 
		try {
	 
			String name = getAttachmentName(fileName, externalID);
			file = new File(getAttachmentLocation(workspaceId, name));
			
			outputStream = new FileOutputStream(file);
	 
			int read = 0;
			byte[] bytes = new byte[1024];
	 
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

	 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	 
			}		
		}
		if (file != null) {
			return file.length();
		} else {
			return null;
		}
	}
	
	public static void deleteAttachment(String workspaceId, String fileName, String externalID) {
		String name = getAttachmentName(fileName, externalID);
		File file = new File(getAttachmentLocation(workspaceId, name));		
		file.delete();
	}
	
	public static InputStream downloadAttachment(String workspaceId, String fileName, String externalID) {
		String name = getAttachmentName(fileName, externalID);
		File file = new File(getAttachmentLocation(workspaceId, name));		
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		return fileInputStream;
	}
	
	public static Long uploadFile(String workspaceId, InputStream inputStream, String fileName, String externalID, String externalVersionId) {
		OutputStream outputStream = null;
		File file = null;
	 
		try {
	 
			String name = getFileName(fileName, externalID, externalVersionId);
			file = new File(getFileLocation(workspaceId, name));
			
			outputStream = new FileOutputStream(file);
	 
			int read = 0;
			byte[] bytes = new byte[1024];
	 
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

	 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	 
			}		
		}
		if (file != null) {
			return file.length();
		} else {
			return null;
		}
	}
	
	public static InputStream downloadFile(String workspaceId, String fileName, String externalID, String externalVersionId) {
		String name = getFileName(fileName, externalID, externalVersionId);
		File file = new File(getFileLocation(workspaceId, name));		
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		return fileInputStream;
	}
	
	public static void trashFile(String workspaceId, String fileName, String externalID) {
		File dir = new File(getFilesWorkspaceFolder(workspaceId));
		File trashDir = new File(getTrashWorkspaceFolder(workspaceId));
		trashDir.mkdirs();
		
		String[] files = dir.list();
		
		for (String string : files) {
			if (string.startsWith(externalID + "-")) {
				File f = new File(dir, string);	
				boolean ret = f.renameTo(new File(trashDir, string));
				if (!ret) {
					System.out.println("Failed to move file : " + trashDir + "/" + string);
				}
			}
		}	
	}
	
	public static void unTrashFile(String workspaceId, String fileName, String externalID) {
		File dir = new File(getFilesWorkspaceFolder(workspaceId));
		File trashDir = new File(getTrashWorkspaceFolder(workspaceId));
		trashDir.mkdirs();
		
		String[] files = trashDir.list();
		
		for (String string : files) {
			if (string.startsWith(externalID + "-")) {
				File f = new File(trashDir, string);	
				boolean ret = f.renameTo(new File(dir, string));	
				if (!ret) {
					System.out.println("Failed to move file : " + trashDir + "/" + string);
				}
			}
		}	
	}
	
	public static void deleteFile(String workspaceId, String fileName, final String externalID) {
		File dir = new File(getTrashWorkspaceFolder(workspaceId));
		dir.mkdirs();
		
		String[] files = dir.list();
		
		for (String string : files) {
			if (string.startsWith(externalID + "-")) {
				File f = new File(dir, string);		
				f.delete();
			}
		}	
	}
	
	public static void deleteVersion(String workspaceId, String fileName, String externalID, String externalVersionId) {
		String name = getFileName(fileName, externalID, externalVersionId);
		File file = new File(getFileLocation(workspaceId, name));		
		file.delete();
	}
	
	private static String getFileLocation(String workspaceId, String fileName) {
		String dir = getFilesWorkspaceFolder(workspaceId);
		File file = new File(dir);
		file.mkdirs();
		return dir + fileName;
	}
	
	private static String getAttachmentLocation(String workspaceId, String fileName) {
		String dir = getAttachmentsWorkspaceFolder(workspaceId);
		File file = new File(dir);
		file.mkdirs();
		return dir + fileName;
	}
	
	private static String getWorkspaceFolder(String workspaceId) {
		return FILE_STORE_LOCATION + WORKSPACES_FOLDER + workspaceId;
	}
	
	private static String getTrashWorkspaceFolder(String workspaceId) {
		return FILE_STORE_LOCATION + WORKSPACES_FOLDER + TRASH_FOLDER + workspaceId;
	}
	
	private static String getFilesWorkspaceFolder(String workspaceId) {
		return FILE_STORE_LOCATION + WORKSPACES_FOLDER + workspaceId + FILES_FOLDER;
	}
	
	private static String getAttachmentsWorkspaceFolder(String workspaceId) {
		return FILE_STORE_LOCATION + WORKSPACES_FOLDER + workspaceId + ATTACHMENTS_FOLDER;
	}
	
	private static String getAttachmentName(String fileName, String externalID) {
		return externalID;
	}
	
	private static String getFileName(String fileName, String externalID, String externalVersionId) {
		return externalID + "-" + externalVersionId;
	}
}
