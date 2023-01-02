package innovitics.azimut.utilities.fileutilities;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.exceptions.BusinessException;

@Component
public class SecureStorageService implements ISecureStorage{

	@Autowired BlobFileUtility blobFileUtility;
	
	@Override
	public BlobData uploadFile(MultipartFile file, boolean generateSasToken,String containerName, String subDirectory, boolean useOriginalFileName) throws BusinessException, IOException 
	{
		return	blobFileUtility.uploadFile(file, generateSasToken, containerName, subDirectory, useOriginalFileName);
	}

	@Override
	public String generateFileRetrievalUrl(String path, String fileName, String subDirectory,boolean generateWithToken) throws IOException 
	{
		return blobFileUtility.generateFileRetrievalUrl(path, fileName, subDirectory, generateWithToken);
	}

	@Override
	public String generateFileRetrievalUrl(String path, String fileName, String subDirectory,boolean generateWithToken, Long tokenValidityMinutes) throws IOException 
	{
		return blobFileUtility.generateFileRetrievalUrl(path, fileName, subDirectory, generateWithToken, tokenValidityMinutes);
	}

	@Override
	public void deleteFile(String path, String fileName, String subDirectory,boolean generateWithToken) throws IOException {
		
		this.blobFileUtility.deleteFileFromBlob(path, fileName, subDirectory, generateWithToken);
		
	}

	
}
