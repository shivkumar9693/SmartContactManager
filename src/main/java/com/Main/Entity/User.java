package com.Main.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
 

@Entity
@Table(name = "userDetails")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userid;
	@NotBlank(message = "Name Should Not be Empty")
	@Size(min = 4,max = 15,message = "username must be between 4 to 15")
	private String username;
	@Column(unique = true)
	@Pattern( regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
	        message = "Email must be valid (example: user@example.com)")
	private String email;
	private String imgurl;
	@NotBlank(message = "Password is required")
	private String password;
	@Column(length = 500)
	private String about;
	private String role;
	private boolean active;
	
	@OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY,orphanRemoval = true)
	private List<Contact> list=new ArrayList<>();
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public List<Contact> getList() {
		return list;
	}
	public void setList(List<Contact> list) {
		this.list = list;
	}
	public User(int userid, String username, String email, String imgurl, String password, String about, String role,
			boolean active, List<Contact> list) {
		super();
		this.userid = userid;
		this.username = username;
		this.email = email;
		this.imgurl = imgurl;
		this.password = password;
		this.about = about;
		this.role = role;
		this.active = active;
		this.list = list;
	}
	public User() {
		 
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", email=" + email + ", imgurl=" + imgurl
				+ ", password=" + password + ", about=" + about + ", role=" + role + ", active=" + active + ", list="
				+ list + "]";
	}
	 
	
	
	
	
}
