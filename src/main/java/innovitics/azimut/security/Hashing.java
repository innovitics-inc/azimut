package innovitics.azimut.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import innovitics.azimut.utilities.datautilities.StringUtility;
@Component
public class Hashing {
	
	public  String digest(String input) {
		    try {
		        MessageDigest messageDigest = MessageDigest.getInstance(StringUtility.SHA_256_ALGORITHM);
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
