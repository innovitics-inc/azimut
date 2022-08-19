package innovitics.azimut.utilities.fileutilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;

import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Component
public class BlobFileUtility extends ParentUtility{
 @Autowired FileUtility fileUtility;
	
	public BlobData uploadFileToBlob(MultipartFile file,boolean generateSasToken,String containerName,String subDirectory,boolean useOriginalFileName) throws IOException, BusinessException {
		String fileName="";
		BlobData blobData=new BlobData();
		
		try {
		if(!useOriginalFileName)
		fileName= this.generateRandomName(file);
		else
		fileName=file.getOriginalFilename();
		
		if(!StringUtility.isStringPopulated(subDirectory))
		subDirectory=DateUtility.getCurrentYearMonth();
		
		
		BlobClient blobClient = this.generateBlobClientAndFileName(containerName+"/"+subDirectory,fileName,blobData).getBlobClient();
		blobClient.upload(file.getInputStream(), file.getSize(), true);
		
		blobData.setUrl(blobClient.getBlobUrl());
		blobData.setFileName(fileName);
		blobData.setFileSize(fileUtility.getApproximatedFileSizeInMegabytes(file.getSize()));
		blobData.setSubDirectory(subDirectory);
		if (generateSasToken) 
			blobData.setToken(this.generateSasTokenString(blobClient));		
		this.logger.info("Generated URL:::"+blobData.getConcatinated(generateSasToken));
		}
		catch(Exception exception)
		{
			this.logger.info("Could not upload the file.");
			exception.printStackTrace();
			throw new BusinessException(ErrorCode.UPLOAD_FAILURE);
		}
		return blobData;
	}
	
		private BlobData generateBlobClientAndFileName(String path,String fileName,BlobData blobData)
		{
			BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
					.connectionString(this.configProperties.getBlobConnectionString())
					.containerName(path).buildClient();
			BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
			blobData.setBlobClient(blobClient);
			
			return blobData;
		}
		
	private  String generateSasTokenString(BlobClient blobClient) {
		BlobSasPermission blobSasPermission = new BlobSasPermission().setReadPermission(true);
		OffsetDateTime expiryTime = OffsetDateTime.now()
				.plusMinutes(Long.parseLong(this.configProperties.getSasTokenDurationInMinutes()));
		BlobServiceSasSignatureValues values = new BlobServiceSasSignatureValues(expiryTime, blobSasPermission).setStartTime(OffsetDateTime.now()
						.minusMinutes(Long.parseLong(this.configProperties.getSasTokenDurationInMinutes()) - 1));
		return  blobClient.generateSas(values);

	}
	
	
	String 	generateRandomName(MultipartFile file)
	{
		String extension=StringUtility.generateSubStringStartingFromCertainIndex(file.getOriginalFilename(),'.');
		UUID uuid=UUID.randomUUID();
		return uuid.toString()+extension;
	}
	
	public String generateFileRetrievalUrl(String path,String fileName,String subDirectory,boolean generateWithToken) throws IOException
	{
		BlobData blobData=new BlobData();
		blobData.setFileName(fileName);
		blobData=this.generateBlobClientAndFileName(path+"/"+subDirectory, fileName, blobData);
		
		if (generateWithToken)
		{
			blobData.setToken(this.generateSasTokenString(blobData.getBlobClient()));
		}
		blobData.setUrl(this.configProperties.getBlobContainerUrl()+path+"/"+subDirectory+"/"+fileName);
		
		return blobData.getConcatinated(generateWithToken);
	}
	
	public String generateFileRetrievalUrl(String path,String fileName,String subDirectory,boolean generateWithToken,Long tokenValidityMinutes) throws IOException
	{
		BlobData blobData=new BlobData();
		blobData.setFileName(fileName);
		blobData=this.generateBlobClientAndFileName(path+"/"+subDirectory, fileName, blobData);
		
		if (generateWithToken)
		{
			blobData.setToken(this.generateSasTokenString(blobData.getBlobClient(),tokenValidityMinutes));
		}
		blobData.setUrl(this.configProperties.getBlobContainerUrl()+path+"/"+subDirectory+"/"+fileName);
		
		return blobData.getConcatinated(generateWithToken);
	}
	private  String generateSasTokenString(BlobClient blobClient,Long tokenValidityMinutes) {
		BlobSasPermission blobSasPermission = new BlobSasPermission().setReadPermission(true);
		OffsetDateTime expiryTime = OffsetDateTime.now()
				.plusMinutes(tokenValidityMinutes.longValue());
		BlobServiceSasSignatureValues values = new BlobServiceSasSignatureValues(expiryTime, blobSasPermission).setStartTime(OffsetDateTime.now()
						.minusMinutes(tokenValidityMinutes.longValue() - 1L));
		return  blobClient.generateSas(values);

	}
	
	public void deleteFileFromBlob(String containerName,String subDirectory,String fileName,boolean generateSasToken) throws IOException {
		this.logger.info("accessing delete");
		try 
		{
			this.blobClientGeneration(containerName, subDirectory, fileName).getBlobClient().delete();
		}
		catch(Exception exception)
		{
			this.logger.info("Could not delete the file");
			exception.printStackTrace();
		}
		
	}
	
	public void copyBlobFile(String sourceContainerName,String destinationContainerName,String subDirectory,String fileName,boolean generateSasToken) throws IOException {
		this.logger.info("accessing read");
		try 
		{
		
		this.logger.info("source blob:::");	
		BlobData sourceBlobData=this.blobClientGeneration(sourceContainerName, subDirectory, fileName);
		this.logger.info("destination blob:::");
		BlobData destinationBlobData=this.blobClientGeneration(destinationContainerName, subDirectory, fileName);
		this.logger.info("copying from source to destination:::");
		destinationBlobData.getBlobClient().beginCopy(sourceBlobData.getBlobClient().getBlobUrl(), null);
		this.logger.info("deleting from source:::");
		sourceBlobData.getBlobClient().delete();
		
		}
		catch(Exception exception)
		{
			this.logger.info("Could not copy the file");
			exception.printStackTrace();
		}
		
	}
	
	
	BlobData blobClientGeneration(String containerName,String subDirectory,String fileName)
	{
		BlobData blobData=new BlobData();
		blobData.setFileName(fileName);
		blobData= this.generateBlobClientAndFileName(containerName+"/"+subDirectory, fileName, blobData);
		
		this.logger.info("Blob URL::::::: "+blobData.getBlobClient().getBlobUrl());
		return blobData;
	}
	public BlobData uploadFileToBlob(InputStream inputStream,String fileName,Long fileSize,boolean generateSasToken,String containerName,String subDirectory) throws IOException, BusinessException 
	{	
		BlobData blobData=new BlobData();
		if(!StringUtility.isStringPopulated(subDirectory))
		subDirectory=DateUtility.getCurrentYearMonth();
		BlobClient blobClient = this.generateBlobClientAndFileName(containerName+"/"+subDirectory,fileName,blobData).getBlobClient();
		try 
		{
		blobClient.upload(inputStream,fileSize);
		blobData.setUrl(blobClient.getBlobUrl());
		blobData.setFileName(fileName);
		blobData.setFileSize(fileUtility.getApproximatedFileSizeInMegabytes(fileSize));
		blobData.setSubDirectory(subDirectory);
		if (generateSasToken) 
			blobData.setToken(this.generateSasTokenString(blobClient));		
		this.logger.info("Generated URL:::"+blobData.getConcatinated(generateSasToken));
		}
		catch(Exception exception)
		{
			this.logger.info("Could not upload the file.");
			exception.printStackTrace();
			throw new BusinessException(ErrorCode.UPLOAD_FAILURE);
		}
		return blobData;
	}

}
