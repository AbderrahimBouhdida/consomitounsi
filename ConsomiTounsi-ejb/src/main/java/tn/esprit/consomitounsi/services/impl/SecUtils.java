package tn.esprit.consomitounsi.services.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;  

public class SecUtils {
	Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);
	public static String getSalt() throws NoSuchAlgorithmException
	{
	    //Always use a SecureRandom generator
	    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	    //Create array for salt
	    byte[] salt = new byte[16];
	    //Get a random salt
	    sr.nextBytes(salt);
	    //return salt
	    return salt.toString();
	}
	public String getSecurePassword(String clearPass, String salt) throws NoSuchAlgorithmException {
		String password = salt+clearPass;
		System.out.println("dadadada : "+password);
		String hash = argon2.hash(4, 1024 * 1024, 8, password);
		return hash;
	}
	public boolean verifyPassword(String hash,String password) {
		return argon2.verify(hash, password);
	}
	
	public String generateToken(int tokenLength) {
		String token = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		
		StringBuffer tokenBuffer = new StringBuffer();
		Random random = new Random();
		
		while(tokenBuffer.length() < tokenLength) {
			int index = (int) (random.nextFloat() * token.length());
			tokenBuffer.append(token.substring(index, index+1));
		}
		return tokenBuffer.toString();
	}
	
}
