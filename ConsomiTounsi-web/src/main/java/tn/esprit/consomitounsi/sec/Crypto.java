package tn.esprit.consomitounsi.sec;



import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;

public class Crypto {

	JsonWebEncryption jwe = new JsonWebEncryption();
	//Key key = new AesKey(ByteUtil.randomBytes(16));
	byte[] encodedKey = Base64.encodeBase64("your Keuys".getBytes());
	Key key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");


	public String enc(String payload) throws JoseException {

		jwe.setPayload(payload);
		 jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
		 jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
		 jwe.setKey(key);
		 String serializedJwe = jwe.getCompactSerialization();
		// System.out.println("Serialized Encrypted JWE: " + serializedJwe);
		return serializedJwe;

	}

	public String dec(String crypted) throws JoseException {
		jwe = new JsonWebEncryption();
		 jwe.setAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.WHITELIST, 
		        KeyManagementAlgorithmIdentifiers.A128KW));
		 jwe.setContentEncryptionAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.WHITELIST, 
		        ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256));
		 jwe.setKey(key);
		 jwe.setCompactSerialization(crypted);
		 return jwe.getPayload();
	}
}