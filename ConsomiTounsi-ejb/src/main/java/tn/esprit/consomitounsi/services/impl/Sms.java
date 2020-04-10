package tn.esprit.consomitounsi.services.impl;

import java.io.IOException;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;



public class Sms {

	public void sendSMS(String to, String body) {
		SMSKey key= new SMSKey();
		NexmoClient client = new NexmoClient.Builder()
				  .apiKey("98518560")
				  .apiSecret("QC0W6i0Fv4AQHdGj")
				  .build();
		
				TextMessage message = new TextMessage("ConsomiTounsi",to, body);

				SmsSubmissionResponse response;
				try {
					response = client.getSmsClient().submitMessage(message);
					for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
					    System.out.println(responseMessage);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NexmoClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
		

	}
}


