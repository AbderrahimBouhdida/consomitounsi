package tn.esprit.consomitounsi.entities;


//test v2
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1210738760881297022L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idUser;
	@Enumerated(EnumType.STRING)
	private Roles role;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	@Temporal(TemporalType.DATE)
	private Date dob;
	private String address;
	private String phone;
	private String img;
	private String salt;
	private String verifToken;
	private boolean isValid;
	private boolean isLocked;
	@Enumerated(EnumType.STRING)
	private Genders gender;
	
	public User() {
	}

	public User(Roles role, String firstName, String lastName, String email, String username, String password, Date dob,
			String address, String phone, String img) {
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.dob = dob;
		this.address = address;
		this.phone = phone;
		this.img = img;
	}
	

	public User(Roles role, String firstName, String lastName, String email, String username, String password, Date dob,
			String address, String phone, String img, String salt) {
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.dob = dob;
		this.address = address;
		this.phone = phone;
		this.img = img;
		this.salt = salt;
	}

	public User(int idUser, Roles role, String firstName, String lastName, String email, String username, String password,
			Date dob, String address, String phone, String img, String salt) {
		this.idUser = idUser;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.dob = dob;
		this.address = address;
		this.phone = phone;
		this.img = img;
		this.salt = salt;
	}

	
	
	public String getVerifToken() {
		return verifToken;
	}

	public void setVerifToken(String verifToken) {
		this.verifToken = verifToken;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Genders getGender() {
		return gender;
	}

	public void setGender(Genders gender) {
		this.gender = gender;
	}

}
