package innovitics.azimut.utilities.fileutilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.management.Resource;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobItem;
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
 private Resource blobFile;
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
		
		public BlobContainerClient  generateBlobClient(String container)
		{
			BlobContainerClient  blobContainerClient = new BlobContainerClientBuilder()
					.connectionString(this.configProperties.getBlobConnectionString())
					.containerName(container).buildClient();
			return blobContainerClient;
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
	String 	generateRandomName(String extension)
	{
		UUID uuid=UUID.randomUUID();
		return uuid.toString()+"."+extension;
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
	
	public void deleteAllFilesFromBlob(String containerName,String subDirectory) throws IOException {
		this.logger.info("accessing delete all");
		try 
		{
		
			this.deleteAtLocation(containerName,subDirectory);
			
		}
		catch(Exception exception)
		{
			this.logger.info("Could not delete the directory");
			exception.printStackTrace();
		}
		
	}
	
	
	public  void deleteAtLocation(String container, String historical) 
	{
		BlobContainerClient blobContainerClient=this.generateBlobClient(container);
		if (checkIfPathExists(blobContainerClient, historical)) 
		   {
		        List<BlobItem> collect =this.listBlobItemsInFolder(blobContainerClient, historical);
		        for (BlobItem blobItem : collect) 
		        {
		            String name = blobItem.getName();
		            this.logger.info("Blob Item Name:::"+name);
		            if (name.endsWith("/")) 
		            {
		              this.logger.info("Accessing Folder");		              
		              deleteAtLocation(container, name);
		            } 
		            else 
		            {
		            	this.logger.info("Accessing File");
		            	blobContainerClient.getBlobClient(name).delete();
		            }
		        }
		        
		    }
	}

	
	public List<BlobItem> listBlobItemsInFolder(BlobContainerClient blobContainerClient,String subDirectory)
	{
		 List<BlobItem> collect = blobContainerClient.listBlobsByHierarchy(subDirectory).stream().collect(Collectors.toList());
		 
		 return collect;
	}
	public  boolean checkIfPathExists(BlobContainerClient blobContainerClient, String filePath) 
	{
	    this.logger.info("path exists? :::::"+blobContainerClient.exists());
		return blobContainerClient.exists();
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
	public BlobData uploadFileToBlob(InputStream inputStream,boolean generateSasToken,String containerName,String subDirectory,String extension) throws IOException, BusinessException 
	{	
		BlobData blobData=new BlobData();
		if(!StringUtility.isStringPopulated(subDirectory))
		subDirectory=DateUtility.getCurrentYearMonth();
		BlobClient blobClient = this.generateBlobClientAndFileName(containerName+"/"+subDirectory,this.generateRandomName(extension),blobData).getBlobClient();
		try 
		{
		blobClient.upload(BinaryData.fromStream(inputStream));
		blobData.setUrl(blobClient.getBlobUrl());
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
	
	public BlobData uploadFileToBlob(byte [] bytesArray,boolean generateSasToken,String containerName,String subDirectory,String fileName,String extension) throws IOException, BusinessException 
	{	
		BlobData blobData=new BlobData();
		if(!StringUtility.isStringPopulated(subDirectory))
		subDirectory=DateUtility.getCurrentYearMonth();
		BlobClient blobClient = this.generateBlobClientAndFileName(containerName+"/"+subDirectory,fileName+"."+extension,blobData).getBlobClient();
		try 
		{
		blobClient.upload(BinaryData.fromBytes(bytesArray));
		blobData.setUrl(blobClient.getBlobUrl());
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
	public String downloadBlobToFile(String containerName,String subDirectory,String fileName) throws IOException, BusinessException 
	{	
		BlobData blobData=new BlobData();
		if(!StringUtility.isStringPopulated(subDirectory))
		subDirectory=DateUtility.getCurrentYearMonth();
		BlobClient blobClient = this.generateBlobClientAndFileName(containerName+"/"+subDirectory,fileName,blobData).getBlobClient();
		File outputFile=new File("E:\\Backend Team\\Azimut\\"+fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);            
		blobClient.downloadStream(fileOutputStream);
		fileOutputStream.close();
		return "E:\\Backend Team\\Azimut\\"+fileName;
	}
	public ByteArrayOutputStream downloadStreamFromBlob(String containerName,String subDirectory,String fileName) throws IOException, BusinessException 
	{	
		BlobData blobData=new BlobData();
		if(!StringUtility.isStringPopulated(subDirectory))
		subDirectory=DateUtility.getCurrentYearMonth();
		BlobClient blobClient = this.generateBlobClientAndFileName(containerName+"/"+subDirectory,fileName,blobData).getBlobClient();
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();            
		blobClient.downloadStream(byteArrayOutputStream);
		byteArrayOutputStream.close();
		return byteArrayOutputStream;
	}
	
	
}
