package tn.esprit.consomitounsi.services.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import tn.esprit.consomitounsi.entities.Collection;
import tn.esprit.consomitounsi.services.intrf.CollectionServiceRemote;

@LocalBean
@Stateless
public class CollectionServices implements CollectionServiceRemote {
	
	
	@PersistenceContext
    EntityManager em;

	@Override
	public int addCollection(Collection collection) {
		em.persist(collection);
        return collection.getIdcollection();
	}

	@Override
	public void removeCollection(int id) {
		 em.remove(em.find(Collection.class, id));
	     System.out.println("Collection deleted");
		
	}

	@Override
	public void updateCollection(Collection connectionNewValues) {
		Collection coll = em.find(Collection.class, connectionNewValues.getIdcollection());
		//coll.setPassword(connectionNewValues.getPassword());
		coll.setName(connectionNewValues.getName());
		
	}
	
	@Override
	public void updateCollectionv2(Collection collection) {
		try {
			em.merge(collection);
	    } catch (PersistenceException e) {
	        System.out.println("Update Error: " + e.getMessage());
	    }
		
	}
	


	@Override
	public Collection findCollectionById(int id) {
		Collection collection = em.find(Collection.class, id);
        return collection;
	}

	@Override
	public List<Collection> findAllCollection() {
		List<Collection> collection = em.createQuery("from Collection", Collection.class).getResultList();
        return collection;
	}

	

	

}
