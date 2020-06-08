package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import tn.esprit.consomitounsi.entities.Collectiontype;


@ManagedBean(name = "data")
@ApplicationScoped
public class Data implements Serializable {
	private static final long serialVersionUID = 1L;

	public Collectiontype[] getTypes() {
		return Collectiontype.values();
	}
}
