package tn.esprit.consomitounsi.services.impl;

import java.security.NoSuchAlgorithmException;
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
	SecUtils sec = new SecUtils();

	@Override
	public int addUser(User user) {
		try {
			user.setSalt(SecUtils.getSalt());
			System.out.println("salt : " + user.getSalt());
			String pass = user.getPassword();
			String secPass = sec.getSecurePassword(pass, user.getSalt());
			user.setPassword(secPass);
			em.persist(user);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("add failed");
		}

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
		List<User> users = em.createQuery("from User", User.class).getResultList();
		return users;
	}

	@Override
	public User login(String username, String password) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username=:username", User.class);
		query.setParameter("username", username);
		User user = null;
		try {
			user = query.getSingleResult();
			if (user != null) {
				String passToVerify = user.getSalt() + password;
				if(sec.verifyPassword(user.getPassword(), passToVerify))
					return user;
			}
		} catch (Exception e) {
			System.out.println("Erreur : " + e);
			return null;
		}
		return null;
	}

}
