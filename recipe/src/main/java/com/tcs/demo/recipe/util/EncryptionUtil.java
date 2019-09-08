/**
 * 
 */
package com.tcs.demo.recipe.util;


import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



/**
 * Utility methods for Encryption & Decryption of Plain text
 * @author Dhiraj
 *
 */
public class EncryptionUtil {
	
	//128 bit (16 char) aes string
	private static final String AESKEY = "ukPLOkuqozUQAqwf";   //store in properties file for security
	private static final String INITIALIZATIONVECTOR = "ukPLOkuqozUQAqwf";
	
	//private final static Logger LOGGER = LogManager.getLogger(EncryptionUtil.class);
	

	/**
	 * Encrypt the text using 128 bit AES encryption. 
	 * @param textToEncrypt
	 * @return encryptedString
	 * @throws UnsupportedEncodingException 
	 * @throws GeneralSecurityException 
	 */
	public static String encrypt(String textToEncrypt) throws GeneralSecurityException, UnsupportedEncodingException {
			IvParameterSpec iv = new IvParameterSpec(INITIALIZATIONVECTOR.getBytes("UTF-8"));
			 SecretKeySpec skeySpec = new SecretKeySpec(AESKEY.getBytes("UTF-8"), "AES");
			 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		      cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
		      byte[] encrypted = cipher.doFinal(textToEncrypt.getBytes());
		      return   Base64.getEncoder().encodeToString(encrypted);
	}
	
	/**
	 * Decrypt the string using 128bit AES decryption.
	 * Throws generalized Exception if any of (UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException) occurs
	 * @param textToDecrypt
	 * @return
	 * @throws GeneralSecurityException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String decrypt(String textToDecrypt) throws GeneralSecurityException, UnsupportedEncodingException   {
		IvParameterSpec iv = new IvParameterSpec(INITIALIZATIONVECTOR.getBytes("UTF-8"));
		SecretKeySpec skeySpec = new SecretKeySpec(AESKEY.getBytes("UTF-8"), "AES");
		 
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec,iv);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(textToDecrypt));
 
        return new String(original);
	}
	
	//could be added to unit test case
/*	public static void main(String[] args) throws Exception {
		String s =  encrypt("password@1234");
		System.out.println(s);
		System.out.println(s+" "+decrypt("qaPGe16uwLOPfVSd1y0Img=="));
	
		System.out.println(decrypt("ONmeNLA7MdEJ+kJ/nzMeXahF5BIjile8MPoQOoH0UNw="));
	}*/
}
