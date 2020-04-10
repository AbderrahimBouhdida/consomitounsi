package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;


import tn.esprit.consomitounsi.entities.Collection;

@Remote
public interface CollectionServiceRemote {
	public int addCollection(Collection collection);
    public void removeCollection(int id);
    public void updateCollection(Collection collection);
    public void updateCollectionv2(Collection collection);
    public Collection findCollectionById(int id);
    public List<Collection> findAllCollection();
    

}
