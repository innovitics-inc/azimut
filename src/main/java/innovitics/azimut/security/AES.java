package innovitics.azimut.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.configproperties.ConfigProperties;
@Component
public class AES {
@Autowired ConfigProperties configProperties;
	
	 
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
	
	
}
