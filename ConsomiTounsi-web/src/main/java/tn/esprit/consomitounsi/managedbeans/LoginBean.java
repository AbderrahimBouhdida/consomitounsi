package tn.esprit.consomitounsi.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.Path;

import tn.esprit.consomitounsi.entities.Cart;
import tn.esprit.consomitounsi.entities.CartProduct;
import tn.esprit.consomitounsi.entities.Genders;
import tn.esprit.consomitounsi.entities.LostPass;
import tn.esprit.consomitounsi.entities.Product;
import tn.esprit.consomitounsi.entities.Roles;
import tn.esprit.consomitounsi.entities.User;
import tn.esprit.consomitounsi.sec.LoginToken;
import tn.esprit.consomitounsi.services.impl.CartServices;
import tn.esprit.consomitounsi.services.impl.EmailService;
import tn.esprit.consomitounsi.services.impl.UserServices;



@ManagedBean
@SessionScoped
@Path("/test")
public class LoginBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5798523708080341554L;
	private String login;
	private String password;
	private User user;
	private Boolean loggedIn = false;
	private String name;
	private Roles role = Roles.GUEST;
	private User newUser = new User();
	private Cart userCart;
	private String email;
	private String token;
	private String newPass;
	private String reNewPass;
	private Genders[] genders;
	private boolean captcha;
	@EJB
	UserServices userService;
	@EJB
	CartServices carts;

	private List<User> users = new ArrayList<User>();

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public String getName() {
		name = user.getFirstName() + " " + user.getLastName();

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public User getuser() {
		return user;
	}

	public void setuser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		users = userService.findAllUsers();
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Cart getUserCart() {
		return userCart;
	}

	public void setUserCart(Cart userCart) {
		this.userCart = userCart;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public Genders[] getGenders() {
		genders = Genders.values();
		return genders;
	}

	public void setGenders(Genders[] genders) {
		this.genders = genders;
	}

	public String doLogin() {
		String navigateTo = "null";

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", "Correct");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		user = userService.login(login, password);
		if (user != null) {
			if (!user.isValid()) {
				FacesContext.getCurrentInstance().addMessage("login:btnLogin", new FacesMessage("Verify your E-mail"));
				return "index?faces-redirect=true";
			}
			if (user.isLocked()) {
				FacesContext.getCurrentInstance().addMessage("login:btnLogin", new FacesMessage("account locked"));
				return "index?faces-redirect=true";
			}
			if (!carts.isCartAvailaible(user)) {
				Cart cr = new Cart(user, true);
				carts.addCart(cr);
				System.out.println("cart created !");
			}
			userCart = carts.findActiveCartByUserId(user);
			FacesContext context = FacesContext.getCurrentInstance();
			Application application = context.getApplication();
			CartBean cart = application.evaluateExpressionGet(context, "#{cartBean}", CartBean.class);
			Optional.ofNullable(cart.getGuestCart().getProducts()).ifPresent(c -> {
				for (CartProduct cartProduct : c) {
					Product tmp = cartProduct.getProduct();
					tmp.setQuantity(cartProduct.getQuantity());
					carts.addProdCart(user, tmp);
				}
			});
			String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
			navigateTo = viewId;
			loggedIn = true;
			token = LoginToken.createJWT("ConsomiTounsi", user.getIdUser(), user.getUsername(), user.getRole(), 0l);
			role = user.getRole();
		} else {
			FacesContext.getCurrentInstance().addMessage("login:btnLogin", new FacesMessage("Bad Credentials"));
		}

		return navigateTo;
	}

	public String doLogout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		loggedIn = false;
		return "/store/index?faces-redirect=true";
	}

	public String register() {
		if (newPass.equals(reNewPass)) {
			newUser.setRole(Roles.USER);
			newUser.setPassword(newPass);
			userService.addUser(newUser);
			newPass = "";
			reNewPass = "";
			newUser = new User();
			FacesContext.getCurrentInstance().addMessage("reg:register",
					new FacesMessage("Successful", "Account created !"));
			return "/verification?faces-redirect=true";
		}
		FacesContext.getCurrentInstance().addMessage("reg:register", new FacesMessage("Passwords mismatch"));

		return "";
	}

	public String adminRedirect() {
		String navigateTo = "null";
		role = Roles.GUEST;
		Optional.ofNullable(user).ifPresent(e -> role = e.getRole());
		if (role.equals(Roles.ADMIN)) {
			System.out.println("shpuld go to panel");
			navigateTo = "/panel/admin/index?faces-redirect=true";
		} else if (role.equals(Roles.GUEST)) {
			System.out.println("Guest");
			navigateTo = "account-dashboard?faces-redirect=true";
		} else {
			System.out.println(role);
		}
		return navigateTo;
	}

	public String requestPass() {
		System.out.println("ssd" + this.email);
		User tmp = userService.getUserByEmail(this.email);
		if (tmp != null) {
			if (!userService.requestExist(tmp)) {
				LostPass lp = new LostPass();
				lp.setUser(tmp);
				lp.setToken(UUID.randomUUID().toString());
				userService.requestPassword(lp);
				EmailService es = new EmailService();
				String body = "Please click on the link below to recover your password\n"
						+ "http://localhost:9080/pindiv-web/password.jsf?vtoken=" + lp.getToken();
				try {
					es.sendEmail("service@consomitounsi.tn", "Password Recovery", email, body);
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					System.out.println("can't send email");
				}
			}
		}
		FacesContext.getCurrentInstance().addMessage("lpf:lp", new FacesMessage("Successful", "Check your email"));
		return "";
	}

	public boolean isCaptcha() {
		return captcha;
	}

	public void setCaptcha(boolean captcha) {
		this.captcha = captcha;
	}

}