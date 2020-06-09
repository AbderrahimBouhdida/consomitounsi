package tn.esprit.consomitounsi.entities;

public enum Genders {
	
	FEMALE("Female"),MALE("Male");
	
	private final String displayName;
	 
    Genders(final String display)
    {
        this.displayName = display;
    }

    @Override public String toString()
    {
        return this.displayName;
    }
}
