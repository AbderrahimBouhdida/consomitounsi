package tn.esprit.consomitounsi.entities;

public enum States {
	ARIANA("Ariana"),BEJA("Béja"),BENAROUS("Ben Arous"),BIZERTE("Bizerte"),GABES("Gabès"),GAFSA("Gafsa"),JENDOUBA("Jendouba"),KAIROUAN("Kairouan"),KASSERINE("Kasserine"),KEBILI("Kebili"),KEF("Kef"),MAHDIA("Mahdia"),MANOUBA("Manouba"),MEDENINE("Medenine"),MONASTIR("Monastir"),NABEUL("Nabeul"),SFAX("Sfax"),SIDIBOUZID("Sidi Bouzid"),SILIANA("Siliana"),SOUSSE("Sousse"),TATAOUINE("Tataouine"),TOZEUR("Tozeur"),TUNIS("Tunis"),ZAGHOUAN("Zaghouan");
	
	private final String displayName;
	 
    States(final String display)
    {
        this.displayName = display;
    }

    @Override public String toString()
    {
        return this.displayName;
    }
}
