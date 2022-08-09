package innovitics.azimut.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.services.NotificationCenterService;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;
import innovitics.azimut.utilities.fileutilities.FileUtility;

@RestController
@RequestMapping("/api")
public class Greeting {

	public final static Logger logger = LogManager.getLogger(Greeting.class.getName());

	@Autowired NotificationCenterService notificationCenterService;
	@Autowired FileUtility fileUtility;
	//@Autowired AzureBlobAdapter azureBlobAdapter;
	@Autowired BlobFileUtility blobFileUtility;
	@Autowired protected ConfigProperties configProperties;
	@GetMapping("/greeting") 
	protected String read() {
			logger.info("greeting");
			return notificationCenterService.findAll().toString();
		
	}
	
	
	@PostMapping(value="/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
	protected String upload(@RequestParam ("file") MultipartFile file) throws IOException {
			logger.info("upload");
			return this.fileUtility.uploadFile(file);
			//return this.azureBlobAdapter.upload(file, "propic", "ProfilePictures");
			//return this.blobFileUtility.uploadToFileToBlob(file,true,configProperties.getBlobProfilePicturePath()).toString();
	
		
	}
	
	
	
}
