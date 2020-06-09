package tn.esprit.consomitounsi.services.intrf;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.consomitounsi.entities.LostPass;
import tn.esprit.consomitounsi.entities.User;


@Remote
public interface IUserServicesRemote {
	public int addUser(User user);
	public void removeUser(int id);
	public void updateUser(User user);
	public User findUserById(int id);
	public List<User> findAllUsers();
	User login(String username, String password);
	boolean verifyEmail(String token);
	boolean userExist(User newUser);
	User getUserByEmail(String email);
	void requestPassword(LostPass lp);
	LostPass findRequestPass(String token);
	void removeRequest(LostPass lp);
	boolean requestExist(User user);
	int adminAddUser(User user);
	void lockUser(User user);
	void unlockUser(User user);
	int adminModUser(User user,boolean pass);
	List<User> getAllClients();
	List<User> getAllStaff();
}
