package tn.esprit.consomitounsi.services.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tn.esprit.consomitounsi.entities.Message;
import tn.esprit.consomitounsi.services.intrf.IMessagesServices;


@Stateless
@LocalBean
public class MessageServices implements IMessagesServices{
	@PersistenceContext
    EntityManager em;
	
	@Override
    public void sendMessage(Message msg) {
        msg.setDateSent(new Date());
        em.persist(msg);
    }
 
    @Override
    public Message getFirstAfter(Date after) {
        List<Message> messages = em.createQuery("FROM Message m",Message.class).getResultList();
    	if(messages.isEmpty())
            return null;
        if(after == null)
            return messages.get(0);
        for(Message m : messages) {
            if(m.getDateSent().after(after))
                return m;
        }
        return null;
    }
    
    @Override
    public List<Message> getAll(){
    	return em.createQuery("FROM Message m ORDER BY m.dateSent DESC",Message.class).getResultList();
    }
}
