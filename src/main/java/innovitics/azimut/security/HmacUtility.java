package innovitics.azimut.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class HmacUtility {
	
	public String generateHmac256(String message, String key){
        try 
        {
        	byte[] bytes = hmac("HmacSHA256", key.getBytes(), message.getBytes());
        	return bytesToHex(bytes);
        }
        catch (InvalidKeyException | NoSuchAlgorithmException exception)
        {
        	exception.printStackTrace();
        	return "";
        }
		   
    }

    byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    /*String bytesToHex(byte[] bytes) {   
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
*/
    
    String bytesToHex(byte[] bytes) {
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
