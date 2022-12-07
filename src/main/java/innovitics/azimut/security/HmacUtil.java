package innovitics.azimut.security;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;
@Component
public class HmacUtil {


	public String generateHmac256(String message,String key)
	{	
	
	String result="";
	try {
   	 	
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
   	 	SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
   	 	sha256_HMAC.init(secret_key);
   	 	result= toHexString(sha256_HMAC.doFinal(message.getBytes("UTF-8")));
   		}
   	catch(Exception exception)
   		{
   			exception.printStackTrace();
   		}
	return result;
   }
   
   public static  String toHexString(byte[] bytes) {
       char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
       char[] hexChars = new char[bytes.length * 2];
       int v;
       for ( int j = 0; j < bytes.length; j++ ) {
           v = bytes[j] & 0xFF;
           hexChars[j*2] = hexArray[v/16];
           hexChars[j*2 + 1] = hexArray[v%16];
       }
       return new String(hexChars);
   }
}
