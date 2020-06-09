package tn.esprit.consomitounsi.managedbeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import tn.esprit.consomitounsi.entities.Adress;
import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.LostPass;
import tn.esprit.consomitounsi.entities.Roles;
import tn.esprit.consomitounsi.entities.States;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.services.impl.AdressService;
import tn.esprit.consomitounsi.services.impl.CartServices;
import tn.esprit.consomitounsi.services.impl.UserServices;


@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2496557588370399117L;

	private User userToMod = new User();

	private String destination = System.getenv("UPLOAD_TEST");
	private UploadedFile file;
	private String img = "placeholder.png";
	private Adress adrToMod;
	private States[] states;
	private States state;
	private String country;
	private int count = 5;
	private String newPass;
	private String reNewPass;
	private String token;
	private User selectedUser;
	private List<User> selectedUsers = new ArrayList<>();
	private User newUser = new User();
	List<User> users = new ArrayList<User>();
	private List<User> staff = new ArrayList<User>();
	private boolean passReq = false;
	private Roles[] roles;

	@EJB
	UserServices userServices;
	@EJB
	AdressService adressServices;
	@EJB
	CartServices cartServices;

	@ManagedProperty(value = "#{loginBean}")
	LoginBean loginBean;

	public List<User> getStaff() {
		staff = userServices.getAllStaff();
		return staff;
	}

	public void setStaff(List<User> staff) {
		this.staff = staff;
	}

	public List<User> getUsers() {
		users = userServices.getAllClients();
		return users;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUserToMod() {
		return userToMod;
	}

	public void setUserToMod(User userToMod) {
		this.userToMod = userToMod;
	}

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public Roles[] getRoles() {
		roles = Roles.values();
		return roles;
	}

	public void setRoles(Roles[] roles) {
		this.roles = roles;
	}

	public String userDetail(User user) {
		System.out.println(user.getEmail());
		Optional.ofNullable(user).ifPresent(u -> userToMod = user);
		if (userToMod != null)
			return "userdetail?faces-redirect=true";
		return "#";
	}

	public String addClient() {
		newUser.setRole(Roles.USER);
		try {
			if (file.getSize() != 0) {
				upload();
				TransferTile(file.getFileName(), file.getInputstream());
				newUser.setImg(file.getFileName());
			} else
				newUser.setImg("placeholder.png");
		} catch (Exception e) {
			newUser.setImg("placeholder.png");

		}
		userServices.adminAddUser(newUser);
		newUser = new User();
		return "all_clients?faces-redirect=true";
	}

	public String addStaff() {
		try {
			if (file.getSize() != 0) {
				upload();
				TransferTile(file.getFileName(), file.getInputstream());
				newUser.setImg(file.getFileName());
			} else
				newUser.setImg("placeholder.png");
		} catch (Exception e) {
			newUser.setImg("placeholder.png");

		}
		userServices.adminAddUser(newUser);
		newUser = new User();
		return "all_staffs?faces-redirect=true";
	}

	public String navToMod(User user) {
		userToMod = user;
		return "edit_client?faces-redirect=true";
	}

	public String navToStaffMod(User user) {
		userToMod = user;
		return "edit_staff?faces-redirect=true";
	}

	public String modClient() {
		try {
			if (file.getSize() != 0) {
				upload();
				TransferTile(file.getFileName(), file.getInputstream());
				userToMod.setImg(file.getFileName());
			}
		} catch (Exception e) {

		}
		userServices.adminModUser(userToMod, passReq);
		userToMod = new User();
		passReq = false;
		file = null;
		return "all_clients?faces-redirect=true";
	}

	public String modStaff() {
		try {
			if (file.getSize() != 0) {
				upload();
				TransferTile(file.getFileName(), file.getInputstream());
				userToMod.setImg(file.getFileName());
			}
		} catch (Exception e) {

		}
		userServices.adminModUser(userToMod, passReq);
		userToMod = new User();
		passReq = false;
		file = null;
		return "all_staffs?faces-redirect=true";
	}

	public String deleteUser(User user) {
		if (!(user.getIdUser() == loginBean.getuser().getIdUser())) {
			List<Cart> carts = cartServices.findCartByUserId(user);

			if (carts.size() > 0) {
				for (Cart cart : carts) {
					cartServices.removeCart(cart.getIdCart());
				}
			}
			userServices.removeUser(user.getIdUser());
		} else {
			FacesMessage msg = new FacesMessage("Fail", "you can't delete yourself");
			FacesContext.getCurrentInstance().addMessage(":form:msgs", msg);
		}

		return "";
	}

	public void deleteUsers() {
		for (User user : selectedUsers) {
			deleteUser(user);
		}
		selectedUsers = new ArrayList<User>();
	}

	public String lockAccount(User user) {
		if (!(user.getIdUser() == loginBean.getuser().getIdUser())) {
			userServices.lockUser(user);
		} else {
			FacesMessage msg = new FacesMessage("Fail", "you can't Lock yourself");
			FacesContext.getCurrentInstance().addMessage(":form:msgs", msg);
		}
		return "";
	}

	public String lockAccounts() {
		for (User user : selectedUsers) {
			lockAccount(user);
		}
		selectedUsers = new ArrayList<User>();
		return "";
	}

	public String unlockAccount(User user) {
		userServices.unlockUser(user);
		return "";
	}

	public String unlockAccounts() {
		for (User user : selectedUsers) {
			unlockAccount(user);
		}
		selectedUsers = new ArrayList<User>();
		return "";
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Adress getAdrToMod() {
		return adrToMod;
	}

	public void setAdrToMod(Adress adrToMod) {
		this.adrToMod = adrToMod;
	}

	public void upload() {

		if (file != null) {
			FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else
			System.out.println("file is null");
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void TransferTile(String fileName, InputStream in) {
		try {
			OutputStream out = new FileOutputStream(new File(destination + fileName));
			int reader = 0;
			byte[] bytes = new byte[(int) file.getSize()];
			while ((reader = in.read(bytes)) != -1) {
				out.write(bytes, 0, reader);
			}
			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getImg() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		LoginBean user = application.evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);
		Optional.ofNullable(user.getuser().getImg()).ifPresent(c -> this.img = c);

		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<Adress> addresses(User user) {
		return (user != null) ? adressServices.getAddresses(user) : new ArrayList<Adress>();
	}

	public void delAddress(Adress adress) {
		adressServices.removeAdress(adress.getId());
	}

	public String modAddress(Adress addr) {
		adrToMod = addr;
		this.country = addr.getCountry();
		this.state = addr.getState();
		return "account-edit-address?faces-redirect=true";
	}

	public String confirmModAddr() {
		adrToMod.setCountry(country);
		adrToMod.setState(state);
		adressServices.updateAdress(adrToMod);
		return "account-addresses?faces-redirect=true";
	}

	public String changePass() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String t = request.getParameter("vtoken");
		if (newPass.equals(reNewPass)) {
			LostPass tmp = userServices.findRequestPass(t);
			if (!tmp.equals(null)) {
				User u = tmp.getUser();
				u.setPassword(newPass);
				userServices.updateUser(u);
				userServices.removeRequest(tmp);
				FacesContext.getCurrentInstance().addMessage("passf:pass",
						new FacesMessage("Successful", "Password changed"));
				return "store/index?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "something went wrong"));
				return null;
			}
		} else {
			FacesContext.getCurrentInstance().addMessage("passf:pass", new FacesMessage("Failed", "Password Mismatch"));
		}

		return "password.jsf?vtoken=" + t + "&faces-redirect=true";

	}

	public States[] getStates() {
		return States.values();
	}

	public void setStates(States[] states) {
		this.states = states;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		// count = users.size();
		this.count = count;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getReNewPass() {
		return reNewPass;
	}

	public void setReNewPass(String reNewPass) {
		this.reNewPass = reNewPass;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public boolean isPassReq() {
		return passReq;
	}

	public void setPassReq(boolean passReq) {
		this.passReq = passReq;
	}

	public String navToEditProfile() {
		file = null;
		userToMod = loginBean.getuser();
		return "profile?faces-redirect=true";
	}

	public String editProfile() {
		try {
			if (file.getSize() != 0) {
				upload();
				TransferTile(file.getFileName(), file.getInputstream());
				userToMod.setImg(file.getFileName());
			}
		} catch (Exception e) {

		}
		userServices.updateUser(userToMod);
		userToMod = new User();
		file = null;
		return "dashboard?faces-redirect=true";
	}
	public int staffCount() {
		return this.getStaff().size();
	}
	public int userCount() {
		return this.getUsers().size();
	}
}
