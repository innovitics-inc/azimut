package innovitics.azimut.utilities.fileutilities;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.multipart.MultipartFile;

public class DiskStorageUtility extends ParentStorage{

	@Override
	public BlobData uploadFile(MultipartFile file, boolean generateSasToken, String containerName,String subDirectory, boolean useOriginalFileName) throws IOException
	{		
		String fileName="";
		BlobData blobData=new BlobData();
		if(!useOriginalFileName)
		{
			fileName= this.generateRandomName(file);
		}
		else
		{
			fileName=file.getOriginalFilename();
		}
		double size = file.getSize();
	    double kilobytes = (size / 1024);
	    double megabytes = (kilobytes / 1024);
	    byte[] bytes = file.getBytes();
	    String filePath = containerName+"//"+subDirectory+"//"+fileName;
	    BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(new File(filePath)));
	    stream.write(bytes);
	    stream.close();
	    blobData.setFileSize(megabytes);
	    blobData.setSubDirectory(subDirectory);
	    blobData.setUrl(this.configProperties.getAppUrl()+"/"+containerName+"/"+subDirectory+"/"+fileName);		
		return blobData;
	}

	@Override
	public String generateFileRetrievalUrl(String path, String fileName, String subDirectory,boolean generateWithToken) throws IOException
	{
			return this.configProperties.getAppUrl()+"/"+path+"/"+subDirectory+"/"+fileName;
	
	}

	@Override
	public String generateFileRetrievalUrl(String path, String fileName, String subDirectory, boolean generateWithToken,Long tokenValidityMinutes) throws IOException 
	{
		return this.configProperties.getAppUrl()+"/"+path+"/"+subDirectory+"/"+fileName;
	}

}
