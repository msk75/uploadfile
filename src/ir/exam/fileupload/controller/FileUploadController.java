package ir.exam.fileupload.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import ir.exam.fileupload.manager.FileUploadManager;

// Trying to save the file in hard
@Path("/upload")
public class FileUploadController {
	private static final String UPLOAD_FOLDER = "c:/uploadedFiles/";
	FileUploadManager fileUploadManager = FileUploadManager.getFileUploadManager();

	public FileUploadController() {
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream inputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		if (inputStream == null || fileDetail == null)
			return Response.status(400).entity("Invalid data").build();
		try {
			fileUploadManager.createFolderIfNotExists(UPLOAD_FOLDER);
		} catch (SecurityException se) {
			return Response.status(500).entity("Can not create destination folder on server").build();
		}
		String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();
		try {
			fileUploadManager.saveToFile(inputStream, uploadedFileLocation);
		} catch (IOException e) {
			return Response.status(500).entity("Can not save file").build();
		}
		return Response.status(200).entity("File saved to " + uploadedFileLocation).build();
	}
}
