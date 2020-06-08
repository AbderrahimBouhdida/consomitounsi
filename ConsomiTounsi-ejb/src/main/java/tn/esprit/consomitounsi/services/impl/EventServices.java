package tn.esprit.consomitounsi.services.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import tn.esprit.consomitounsi.entities.Event;
import tn.esprit.consomitounsi.services.intrf.EventServiceRemote;

@LocalBean
@Stateless
public class EventServices implements EventServiceRemote {
	
	
	@PersistenceContext
    EntityManager em;

	@Override
	public int addEvent(Event Event) {
		em.persist(Event);
        return Event.getIdevent();
	}

	@Override
	public void removeEvent(int id) {
		 em.remove(em.find(Event.class, id));
	     System.out.println("Event deleted");
		
	}

	@Override
	public void updateEvent(Event connectionNewValues) {
		Event coll = em.find(Event.class, connectionNewValues.getIdevent());
		//coll.setPassword(connectionNewValues.getPassword());
		coll.setName(connectionNewValues.getName());
		
	}
	
	@Override
	public void updateEventv2(Event event) {
		try {
			em.merge(event);
	    } catch (PersistenceException e) {
	        System.out.println("Update Error: " + e.getMessage());
	    }
		
	}

	@Override
	public Event findEventById(int id) {
		Event Event = em.find(Event.class, id);
        return Event;
	}

	@Override
	public List<Event> findAllEvent() {
		List<Event> Event = em.createQuery("from Event", Event.class).getResultList();
        return Event;
	}
	
	@Override
	public  List<Object[]> eventsbytype() {
        Query query = em.createQuery("SELECT type,Count(type) FROM Event e GROUP BY e.type");
	     List<Object[]> results = query.getResultList();	
	     	     
        return results;
	    }

	

}
