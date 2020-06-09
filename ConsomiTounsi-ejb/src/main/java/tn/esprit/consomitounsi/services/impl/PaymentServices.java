package tn.esprit.consomitounsi.services.impl;



import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import tn.esprit.consomitounsi.entities.Cart;



@Stateless
@Remote
public class PaymentServices {
	private static final String CLIENT_ID = "ARJ0XS6akW6-phUWfYGkqo9uCoPAw28bu5T-kVbc80eqqFJXOvVdvXhffY6iULtTXmVWVd5aPYJXvaYg";
	private static final String CLIENT_SECRET = "EN6tOfP4d5fd0pbais0h2p4EINcBs7orE8OG1_SiJpYPoSN2O85NxjK6hIqiMiVJkucZIfxicxZMpkUw";
	private static final String MODE = "sandbox";

	public String authorizePayment(Cart cart)			
			throws PayPalRESTException {		

		// Set payer details
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		// shipping address
		ShippingAddress Sh = new ShippingAddress();
		Sh.setCountryCode("TN");
		Sh.setCity("FAHS");
		Sh.setLine1("test Street");
		Sh.setState("TUNIS");
		Sh.setPostalCode("75001");
		ItemList itemList = new ItemList();
	    itemList.setShippingAddress(Sh);
		// Set redirect URLs
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:3000/cancel");
		redirectUrls.setReturnUrl("http://localhost:9080/ConsomiTounsi-web/account/order.jsf");

		// Set payment details
		//float subTotal = Float.valueOf(String.format("%2f",cart.getTotalPrice()));
		Details details = new Details();
		details.setShipping("1");
		details.setSubtotal(String.valueOf(cart.getTotalPrice()));
		details.setTax("1");

		// Payment amount
		float am = cart.getTotalPrice()+1+1;
		Amount amount = new Amount();
		amount.setCurrency("EUR");
		// Total must be equal to sum of shipping, tax and subtotal.
		amount.setTotal(String.valueOf(am));
		amount.setDetails(details);

		
		// Transaction information
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
		  .setDescription("This is the payment transaction description.")
		  .setItemList(itemList);

		// Add transaction to a list
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// Add payment details
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setRedirectUrls(redirectUrls);
		payment.setTransactions(transactions);

		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

		Payment approvedPayment = payment.create(apiContext);

		System.out.println("=== CREATED PAYMENT: ====");
		System.out.println(approvedPayment);

		return getApprovalLink(approvedPayment);

	}
	
	public Payer getPayerInformation() {
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setFirstName("William")
				 .setLastName("Peterson")
				 .setEmail("william.peterson@company.com");
		
		payer.setPayerInfo(payerInfo);
		
		return payer;
	}
	
	public RedirectUrls getRedirectURLs() {
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:8080/PaypalTest/cancel.jsp");
		redirectUrls.setReturnUrl("http://localhost:8080/PaypalTest/review_payment");
		
		return redirectUrls;
	}
	
	public List<Transaction> getTransactionInformation() {
		Details details = new Details();
		details.setShipping("1");
		details.setSubtotal("5");
		details.setTax("1");

		// Payment amount
		Amount amount = new Amount();
		amount.setCurrency("USD");
		// Total must be equal to sum of shipping, tax and subtotal.
		amount.setTotal("7");
		amount.setDetails(details);

		// Transaction information
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
		  .setDescription("This is the payment transaction description.");

		// Add transaction to a list
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		
		return transactions;
	}
	
	public String getApprovalLink(Payment approvedPayment) {
		List<Links> links = approvedPayment.getLinks();
		String approvalLink = null;
		
		for (Links link : links) {
			if (link.getRel().equalsIgnoreCase("approval_url")) {
				approvalLink = link.getHref();
				break;
			}
		}		
		
		return approvalLink;
	}

	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		Payment payment = new Payment().setId(paymentId);

		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

		return payment.execute(apiContext, paymentExecution);
	}
	
	public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		return Payment.get(apiContext, paymentId);
	}
}
