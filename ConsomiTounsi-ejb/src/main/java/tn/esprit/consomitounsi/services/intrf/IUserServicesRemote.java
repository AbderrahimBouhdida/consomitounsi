package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.User;



@Remote
public interface IUserServicesRemote {
	public int addUser(User user);
	public void removeUser(int id);
	public void updateUser(User user);
	public User findUserById(int id);
	public List<User> findAllUsers();
	User login(String username, String password);
	boolean userExist(User user);
}
