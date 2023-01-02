package innovitics.azimut.utilities.fileutilities;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;

public abstract class ParentStorage extends ParentUtility{
	public abstract BlobData uploadFile(MultipartFile file,boolean generateSasToken,String containerName,String subDirectory,boolean useOriginalFileName) throws IOException, BusinessException;
	
	public abstract String generateFileRetrievalUrl(String path,String fileName,String subDirectory,boolean generateWithToken) throws IOException;
	
	public abstract String generateFileRetrievalUrl(String path,String fileName,String subDirectory,boolean generateWithToken,Long tokenValidityMinutes) throws IOException;
	
	
	
	protected String 	generateRandomName(MultipartFile file)
	{
		String extension=StringUtility.generateSubStringStartingFromCertainIndex(file.getOriginalFilename(),'.');
		UUID uuid=UUID.randomUUID();
		return uuid.toString()+extension;
	}
	protected String 	generateRandomName(String extension)
	{
		UUID uuid=UUID.randomUUID();
		return uuid.toString()+"."+extension;
	}


}
