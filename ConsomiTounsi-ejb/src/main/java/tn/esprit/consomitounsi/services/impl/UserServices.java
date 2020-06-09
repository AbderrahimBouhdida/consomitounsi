package tn.esprit.consomitounsi.services.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.consomitounsi.entities.LostPass;
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
			String pass = user.getPassword();
			String secPass = sec.getSecurePassword(pass, user.getSalt());
			user.setPassword(secPass);
			user.setValid(false);
			user.setLocked(false);
			em.persist(user);
			String tok = user.getIdUser()+"."+sec.generateToken(15);
			user.setVerifToken(tok);
			EmailService email = new EmailService();
			String body = "Please click on the link below to activate your account\n"
					+ "http://localhost:9080/ConsomiTounsi-web/verify-account.jsf?token="+tok;
			email.sendEmail("servhice@consomitounsi.tn", "Verification", user.getEmail(), body);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("add failed");
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user.getIdUser();
	}

	@Override
	public int adminAddUser(User user) {
		try {
			user.setSalt(SecUtils.getSalt());
			String pass = SecUtils.generateRandomPassword(10);
			String secPass = sec.getSecurePassword(pass, user.getSalt());
			user.setPassword(secPass);
			user.setValid(true);
			user.setLocked(false);
			em.persist(user);
			EmailService email = new EmailService();
			String body = "Here's your account details \n"
					+"Username : "+user.getUsername()
					+"Password : "+pass
					+"Please consider changing the password after first login"
					+ "http://localhost:9080/pindiv-web/store/index.jsf";
			email.sendEmail("service@consomitounsi.tn", "Verification", user.getEmail(), body);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("add failed");
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user.getIdUser();
	}
	
	@Override
	public int adminModUser(User user, boolean passReq) {
		try {
			if(passReq) {
				user.setSalt(SecUtils.getSalt());
				String pass = SecUtils.generateRandomPassword(10);
				String secPass = sec.getSecurePassword(pass, user.getSalt());
				user.setPassword(secPass);
				
				EmailService email = new EmailService();
				String body = "Here's your account details \n"
						+"Username : "+user.getUsername()
						+"Password : "+pass
						+"Please consider changing the password after first login"
						+ "http://localhost:9080/pindiv-web/store/index.jsf";
				email.sendEmail("service@consomitounsi.tn", "Verification", user.getEmail(), body);
				
			}
			em.merge(user);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("add failed");
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user.getIdUser();
	}
	
	@Override
	public void removeUser(int id) {
		em.remove(em.find(User.class, id));
		}

	@Override
	public void updateUser(User userNewValues) {
		User user = em.find(User.class, userNewValues.getIdUser());
		Optional.ofNullable(userNewValues.getPassword()).ifPresent(p -> {
			if(!user.getPassword().equals(p)) {
				try {
					user.setSalt(SecUtils.getSalt());
					System.out.println("salt : " + user.getSalt());
					String pass = p;
					String secPass = sec.getSecurePassword(pass, user.getSalt());
					user.setPassword(secPass);
				} catch (NoSuchAlgorithmException e) {
					
				}
			}
		});
		Optional.ofNullable(userNewValues.getFirstName()).ifPresent(user::setFirstName);
		Optional.ofNullable(userNewValues.getLastName()).ifPresent(user::setLastName);
		Optional.ofNullable(userNewValues.getPhone()).ifPresent(user::setPhone);
		Optional.ofNullable(userNewValues.getImg()).ifPresent(user::setImg);
		Optional.ofNullable(userNewValues.isLocked()).ifPresent(user::setLocked);
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
	public List<User> getAllClients(){
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.role='USER'", User.class);
		try {
			return query.getResultList();
		} catch (Exception e) {
			System.out.println(e+toString());
			return null;
		}
	}
	
	@Override
	public List<User> getAllStaff(){
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.role<>'USER'", User.class);
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
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
			return null;
		}
		return null;
	}

	@Override
	public boolean userExist(User newUser) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username=:username OR u.email=:email", User.class);
		query.setParameter("username", newUser.getUsername());
		query.setParameter("email", newUser.getEmail());
		try {
			if(query.getResultList().size()!=0) {
				return true;
			}
		} catch (Exception e) {
			
		}
		return false;
	}

	@Override
	public boolean verifyEmail(String token) {
		if(token.contains(".")) {
			int id = 0;
			id = Integer.valueOf(token.split("\\.")[0]);
			User us = em.find(User.class, id);
			if(us.isValid())
				return false;
			if(us.getVerifToken().equals(token)) {
				us.setValid(true);
				return true;
			}
				
		}
		return false;
	}
	
	@Override
	public User getUserByEmail(String email) {
		User user=null;
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email=:email", User.class);
		query.setParameter("email", email);
		try {
			user = query.getSingleResult();
			
		} catch (Exception e) {
			
		}
		return user;
	}
	
	@Override
	public void requestPassword(LostPass lp) {
		try {
		em.persist(lp);
		}catch (Exception e) {
			System.out.println("Already exist");
		}
	}
	
	@Override
	public LostPass findRequestPass(String token) {
		LostPass lp=null;
		TypedQuery<LostPass> query = em.createQuery("SELECT l FROM LostPass l WHERE l.token=:token", LostPass.class);
		query.setParameter("token", token);
		try {
			lp = query.getSingleResult();
			
		} catch (Exception e) {
			System.out.println("cannot find request");
		}
		return lp;
	}
	
	@Override
	public boolean requestExist(User user) {
		LostPass lp=null;
		TypedQuery<LostPass> query = em.createQuery("SELECT l FROM LostPass l WHERE l.user=:user", LostPass.class);
		query.setParameter("user", user);
		try {
			lp = query.getSingleResult();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public void removeRequest(LostPass lp) {
		lp = em.merge(lp);
		em.remove(lp);
	}
	
	@Override
	public void lockUser(User user) {
		User tmp = em.find(User.class, user.getIdUser());
		if(!tmp.isLocked())
			tmp.setLocked(true);
	}
	
	@Override
	public void unlockUser(User user) {
		User tmp = em.find(User.class, user.getIdUser());
		if(tmp.isLocked())
			tmp.setLocked(false);
	}
	
}
