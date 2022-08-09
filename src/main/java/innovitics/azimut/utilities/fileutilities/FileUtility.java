package innovitics.azimut.utilities.fileutilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.utilities.ParentUtility;

@Component
public class FileUtility{

	public String uploadFile(MultipartFile file) throws IOException
	{
		File convertFile= new File("E:\\Development\\Java_Projects\\SystemLogs\\azimut"+file.getOriginalFilename());
		convertFile.createNewFile();
		try(FileOutputStream foFileOutputStream=new FileOutputStream(convertFile))
		{
			foFileOutputStream.write(file.getBytes());
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
		}
		return "File has been uploaded successfully";
	}	
	
	public boolean isFilePopulated(MultipartFile file)
	{
		return file!=null&&file.getSize()>0;
	}
	
	public double getApproximatedFileSizeInMegabytes(long size)
	{
		return (double)size/1000000;
	}
	public long getFileSizeInMegabytes(long size)
	{
		return size/1000000;
	}
}
