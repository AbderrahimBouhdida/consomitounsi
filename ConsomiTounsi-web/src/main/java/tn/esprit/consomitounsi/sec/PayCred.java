package tn.esprit.consomitounsi.sec;

import com.paypal.base.rest.APIContext;

public class PayCred {
	String clientId = "AfVWBC8wN-Qp5Ymvh7tx5ouiOIK5yXBlQXAkvft3iDms9ifKm6w5GJI6YPdBGqgLqanOrax7WLq9tp_l";
	String clientSecret = "EFXMB2OdYZuU5LXgtkk2X9vKrzz-EGxdL_Npw6-tBB-2fjqu_b0IqdrjQKNg38ff3bpzK_FFZWSExKPD";

	APIContext context = new APIContext(clientId, clientSecret, "sandbox");

	public APIContext getContext() {
		return context;
	}

	public void setContext(APIContext context) {
		this.context = context;
	}
	
}
