package innovitics.azimut.utilities.fileutilities;

import com.azure.storage.blob.BlobClient;

public class BlobData {
	
	private BlobClient blobClient;
	private String url;
	private String token;
	private String fileName;
	private String subDirectory;
	private double fileSize;
	
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public BlobClient getBlobClient() {
		return blobClient;
	}
	public void setBlobClient(BlobClient blobClient) {
		this.blobClient = blobClient;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

	public String getSubDirectory() {
		return subDirectory;
	}
	public void setSubDirectory(String subDirectory) {
		this.subDirectory = subDirectory;
	}
	
	
	
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	public String getConcatinated(boolean concatinateToken)
	{
		return concatinateToken? this.getUrl()+"?"+this.getToken():this.getUrl();
	}
	@Override
	public String toString() {
		return "BlobData [blobClient=" + blobClient + ", url=" + url + ", token=" + token + ", fileName=" + fileName
				+ ", subDirectory=" + subDirectory + ", fileSize=" + fileSize + "]";
	}
	

	
}
