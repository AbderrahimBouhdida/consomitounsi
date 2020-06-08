package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import tn.esprit.consomitounsi.entities.Eventtype;



@ManagedBean(name = "dataevent")
@ApplicationScoped
public class Dataevent implements Serializable {
	private static final long serialVersionUID = 1L;

	public Eventtype[] getTypes() {
		return Eventtype.values();
	}
}
