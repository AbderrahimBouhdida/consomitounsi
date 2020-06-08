package tn.esprit.consomitounsi.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import tn.esprit.consomitounsi.entities.Donationtype;


@ManagedBean(name = "datadon")
@ApplicationScoped
public class Datadon implements Serializable {
	private static final long serialVersionUID = 1L;

	public Donationtype[] getTypes() {
		return Donationtype.values();
	}
}
