package tn.esprit.consomitounsi.entities.gestionlivraison;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Exchange extends ReclamationTreatment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	public Exchange() {

	}

	public Exchange(String code, Reclamation rec) {
		super(rec);
		this.code = code;

	}

	public Exchange(int id, String code, Reclamation rec) {
		super(id, rec);
		this.code = code;

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exchange other = (Exchange) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "code=" + code;

	}

}
