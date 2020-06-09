package tn.esprit.consomitounsi.services.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
		String hash = argon2.hash(1, 1024 * 1024, 4, password);
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
	///////////////////////////////////////////////////////
	private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	private static final String NUMBER = "0123456789";
	private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

	private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
	// optional, make it more random
	private static final String PASSWORD_ALLOW_BASE_SHUFFLE = shuffleString(PASSWORD_ALLOW_BASE);
	private static final String PASSWORD_ALLOW = PASSWORD_ALLOW_BASE_SHUFFLE;

	private static SecureRandom random = new SecureRandom();
	
	public static String generateRandomPassword(int length) {
		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			int rndCharAt = random.nextInt(PASSWORD_ALLOW.length());
			char rndChar = PASSWORD_ALLOW.charAt(rndCharAt);
			sb.append(rndChar);

		}

		return sb.toString();

	}

	public static String shuffleString(String string) {
		List<String> letters = Arrays.asList(string.split(""));
		Collections.shuffle(letters);
		return letters.stream().collect(Collectors.joining());
	}
	
}
