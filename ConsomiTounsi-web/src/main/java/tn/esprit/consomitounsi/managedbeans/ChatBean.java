package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jose4j.lang.JoseException;

import tn.esprit.consomitounsi.entities.Message;
import tn.esprit.consomitounsi.sec.Crypto;
import tn.esprit.consomitounsi.services.impl.MessageServices;



@ManagedBean
@SessionScoped
public class ChatBean implements Serializable {

	private String msg;
	@EJB
	MessageServices mm;

	
	@ManagedProperty(value = "#{loginBean}")
	LoginBean loginBean;

	private List<Message> messages = new ArrayList<Message>();
	private Date lastUpdate;
	private Message message = new Message();

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String sendMessage() {
		
		try {
			Crypto c = new Crypto();
			message.setMessage(c.enc(msg));
			message.setUser(loginBean.getuser());
			mm.sendMessage(message);
			message = new Message();
			msg = "";
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "chat?faces-redirect=true";
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void firstUnreadMessage(ActionEvent evt) {
		FacesContext ctx = FacesContext.getCurrentInstance();

		Message m = mm.getFirstAfter(lastUpdate);

	}

	public List<Message> getMessages() {
		messages = mm.getAll();
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String dec(String st) throws JoseException {
		Crypto c = new Crypto();
		return c.dec(st);
	}
}
