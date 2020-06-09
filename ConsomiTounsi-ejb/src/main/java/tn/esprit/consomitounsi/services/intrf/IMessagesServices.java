package tn.esprit.consomitounsi.services.intrf;

import java.util.Date;
import java.util.List;

import tn.esprit.consomitounsi.entities.Message;



public interface IMessagesServices {

	void sendMessage(Message msg);

	Message getFirstAfter(Date after);

	List<Message> getAll();

}
