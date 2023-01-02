package innovitics.azimut.utilities.fileutilities;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.exceptions.BusinessException;

public interface IStorage {

	public BlobData uploadFile(MultipartFile file,boolean generateSasToken,String containerName,String subDirectory,boolean useOriginalFileName) throws BusinessException, IOException;
	
	public String generateFileRetrievalUrl(String path,String fileName,String subDirectory,boolean generateWithToken) throws IOException;
	
	public String generateFileRetrievalUrl(String path,String fileName,String subDirectory,boolean generateWithToken,Long tokenValidityMinutes) throws IOException;
	
	public void deleteFile(String path,String fileName,String subDirectory,boolean generateWithToken) throws IOException;
}
