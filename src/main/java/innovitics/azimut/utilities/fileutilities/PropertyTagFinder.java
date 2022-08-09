package innovitics.azimut.utilities.fileutilities;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.springframework.stereotype.Component;
@Component
public class PropertyTagFinder {

	
	public String findValue(String tag) {
		
		final Properties properties = new Properties();
		try  {
			
			
			/*
			  InputStream input = new FileInputStream("E:/CoPal/error.config");
			  
			  prop.load(input);
			  
			  return prop.getProperty(tag);
			 */
					  
			properties.load(new StringReader(FileReaderProp.readUnicodeJava11("E:/CoPal/error.config")));
			return properties.getProperty(tag);
			
			
			
			

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
	

