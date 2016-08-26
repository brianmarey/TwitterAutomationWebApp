package com.careydevelopment.twitterautomation.service.impl;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Properties;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFile;

public class EncryptionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionService.class);
	
	private static final String KEYSTORE = "/etc/tomcat8/resources/keyStore.jks";
	
	
	public static void main(String[] args) {
		EncryptionService s = new EncryptionService();
		String enc = s.encrypt("brian");
		String dec = s.decrypt(enc);		
	}
	

	public String encrypt(String s) {
		String encrypted = "";
		
		try {
			Properties props = PropertiesFactory.getProperties(PropertiesFile.TWITTER_PROPERTIES);
        	String password = props.getProperty("keystore.password");
        	
            final Cipher cipher = Cipher.getInstance("RSA");
 
            // ENCRYPT using the PUBLIC key

            FileInputStream publicKeyFile = new FileInputStream(KEYSTORE);
        	
			KeyStore publicKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			publicKeyStore.load(publicKeyFile, password.toCharArray());
			Certificate publicCertificate = publicKeyStore.getCertificate("careydevelopment");
			PublicKey publicKey = publicCertificate.getPublicKey();

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(s.getBytes());
            //encrypted = new String(Base64.getEncoder().encode(encryptedBytes));
 		} catch (Exception e) {
			LOGGER.error("Problem encrypting!",e);
		}
		
		return encrypted;
	}

	
	public String decrypt(String s) {
		String decrypted = "";
		
		try {
        	Properties props = PropertiesFactory.getProperties(PropertiesFile.TWITTER_PROPERTIES);
        	String password = props.getProperty("keystore.password");

            final Cipher cipher = Cipher.getInstance("RSA");
            
            FileInputStream privateKeyFile = new FileInputStream(KEYSTORE);
            // DECRYPT using the PRIVATE key
            KeyStore privateKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			privateKeyStore.load(privateKeyFile, password.toCharArray());
			PrivateKey privateKey = (PrivateKey)privateKeyStore.getKey("careydevelopment", "Israel1!1!".toCharArray());
			
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //byte[] ciphertextBytes = Base64.getDecoder().decode(s.getBytes());
            //byte[] decryptedBytes = cipher.doFinal(ciphertextBytes);
            //decrypted = new String(decryptedBytes);
        } catch (Exception e) {
			LOGGER.error("Problem trying to decrypt!",e);
		}

		return decrypted;
	}
}
