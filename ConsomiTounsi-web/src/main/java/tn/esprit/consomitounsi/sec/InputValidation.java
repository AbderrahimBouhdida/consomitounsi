package tn.esprit.consomitounsi.sec;

import java.util.regex.Pattern;

public class InputValidation {

	public boolean isPassword(String password) {

		if (password != null) {
			return Pattern.matches(
					"^(?=[ -~]*?[A-Z])(?=[ -~]*?[a-z])(?=[ -~]*?[0-9])(?=[ -~]*?[#?!@$%^&*-])[ -~]{8,72}$",
					password);
		}

		return false;
	}

	public boolean isEmail(String email) {
		if (email != null) {
			return Pattern.matches(
					"^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
					email);
		}

		return false;
	}

	public boolean isPhoneNumber(String phone) {

		if (phone != null) {
			return Pattern.matches("^[0-9]{8}$", phone);
		}

		return false;
	}


}
