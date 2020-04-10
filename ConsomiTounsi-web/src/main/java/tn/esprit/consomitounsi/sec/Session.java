package tn.esprit.consomitounsi.sec;

import io.jsonwebtoken.Claims;

public class Session {
	
	public static int getUserId(String rawToken) {
		String token = rawToken.substring("Bearer".length()).trim();
		Claims claims = LoginToken.decodeJWT(token);
		int id = claims.get("Id",Integer.class);
		return id;
	}

	private Session() {
		super();
	}
	

}
