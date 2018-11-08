package ir.exam.fileupload.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUploadManager {

	private static FileUploadManager fileUploadManager = new FileUploadManager();

	private FileUploadManager() {

	}

	public static FileUploadManager getFileUploadManager() {
		return fileUploadManager;
	}

	// save the file to folder using target
	public void saveToFile(InputStream inStream, String target) throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}

	// creates a folder if it not already exists
	public void createFolderIfNotExists(String dirName) throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
	
}
