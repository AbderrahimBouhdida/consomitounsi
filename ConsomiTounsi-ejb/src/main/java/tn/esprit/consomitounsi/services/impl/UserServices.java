package tn.esprit.consomitounsi.services.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.intrf.IUserServicesRemote;



@Stateless
@LocalBean
public class UserServices implements IUserServicesRemote {
	@PersistenceContext
	EntityManager em;

	@Override
	public int addUser(User user) {
		em.persist(user);
		return user.getIdUser();
	}

	@Override
	public void removeUser(int id) {
		em.remove(em.find(User.class, id));
		System.out.println("user deleted");
	}

	@Override
	public void updateUser(User userNewValues) {
		User user = em.find(User.class, userNewValues.getIdUser());
		user.setPassword(userNewValues.getPassword());
	}

	@Override
	public User findUserById(int id) {
		User user = em.find(User.class, id);
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		List<User> users = em.createQuery("from user", User.class).getResultList();
		return users;
	}

	@Override
	public User login(String username, String password) {
		TypedQuery<User> query = em
				.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password ", User.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
		User user = null;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Erreur : " + e);
			return null;
		}
		return user;
	}

}
