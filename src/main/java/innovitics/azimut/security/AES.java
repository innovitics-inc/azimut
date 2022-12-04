package innovitics.azimut.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.utilities.datautilities.StringUtility;
@Component
public class AES {
@Autowired ConfigProperties configProperties;
	
	 

	public String encryptThenHash(String value)
	{
		String encryptedValue=this.encrypt(value);
		String hashedValue=this.hashString(encryptedValue);
		return hashedValue;
	}

	public String ecryptWithoutSpecialCharacters(String value)
	{
		return this.encrypt(value).replaceAll("[-+^]*","");
	}



	public  String encrypt(String value) {
	    try {
	        IvParameterSpec iv = new IvParameterSpec(this.configProperties.getJwTokenInitVector().getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(this.configProperties.getJwTokenEncryptionKey().getBytes("UTF-8"), "AES");
	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	 
	        byte[] encrypted = cipher.doFinal(value.getBytes());
	        return Base64.encodeBase64String(encrypted);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	
	
	
	public  String decrypt(String encrypted) {
	    try {
	        IvParameterSpec iv = new IvParameterSpec(this.configProperties.getJwTokenInitVector().getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(this.configProperties.getJwTokenEncryptionKey().getBytes("UTF-8"), "AES");
	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
	 
	        return new String(original);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	 
	    return null;
	}
	
	
	
	
	
	
	
	public String hashString(String value)
	{
		 return digest(StringUtility.SHA_256_ALGORITHM,value);
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
	 
	 
	 
		public  String encrypt(String value,String key) {
		    try {
		        IvParameterSpec iv = new IvParameterSpec(this.configProperties.getJwTokenInitVector().getBytes("UTF-8"));
		        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		 
		        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		 
		        byte[] encrypted = cipher.doFinal(value.getBytes());
		        return Base64.encodeBase64String(encrypted);
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    return null;
		}
		
		
		
		public  String decrypt(String encrypted,String key) {
		    try {
		        IvParameterSpec iv = new IvParameterSpec(this.configProperties.getJwTokenInitVector().getBytes("UTF-8"));
		        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		 
		        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
		 
		        return new String(original);
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		 
		    return null;
		}
		
		
		
		
}
