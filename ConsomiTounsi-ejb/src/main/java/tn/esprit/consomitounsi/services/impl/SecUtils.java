package tn.esprit.consomitounsi.services.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;  

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
	
	
}
