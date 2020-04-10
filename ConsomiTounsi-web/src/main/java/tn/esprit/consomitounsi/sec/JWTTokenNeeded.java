package tn.esprit.consomitounsi.sec;

import javax.ws.rs.NameBinding;

import tn.esprit.consomitounsi.entities.Roles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD})
public @interface JWTTokenNeeded {
	public Roles[] roles() default Roles.ALL;
}