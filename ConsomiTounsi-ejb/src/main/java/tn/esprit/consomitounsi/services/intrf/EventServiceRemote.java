package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.Event;;

@Remote
public interface EventServiceRemote {
	public int addEvent(Event event);
    public void removeEvent(int id);
    public void updateEvent(Event event);
    public void updateEventv2(Event event);
    public Event findEventById(int id);
    public List<Event> findAllEvent();

}
