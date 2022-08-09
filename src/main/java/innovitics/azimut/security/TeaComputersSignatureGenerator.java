package innovitics.azimut.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import java.util.Base64;
@Component
public class TeaComputersSignatureGenerator {
	@Autowired ArrayUtility arrayUtility;
	protected static final Logger logger = LogManager.getLogger(TeaComputersSignatureGenerator.class);
	public String generateSignature(String... params)
	{
		StringBuffer stringBuffer=new StringBuffer();
		if(arrayUtility.isArrayPopulated(params))
		{
			logger.info("Param Array is populated:::");
			for (int i=0;i<params.length;i++)
			{
				logger.info(params[i]);
				stringBuffer.append(params.clone()[i]);				
			}
			
			 String hashedString = digest(StringUtility.SHA_256_ALGORITHM,stringBuffer.toString());
			
			 String encodedString = Base64.getEncoder().encodeToString(hashedString.getBytes());
						
			return encodedString;
		}
		else 
			return "";
	}
	
	 private  String digest(String alg, String input) {
		    try {
		        MessageDigest messageDigest = MessageDigest.getInstance(alg);
		        byte[] buffer = input.getBytes(StringUtility.UTF_8_ENCODING);
		        messageDigest.update(buffer);
		        byte[] digest = messageDigest.digest();
		        return encodeHex(digest);
		    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
		        e.printStackTrace();
		        return e.getMessage();
		    }
		}
	 
	 private  String encodeHex(byte[] digest) {
		    StringBuilder sb = new StringBuilder();
		    for (int i = 0; i < digest.length; i++) {
		        sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
		    }
		    return sb.toString();
		}

}
