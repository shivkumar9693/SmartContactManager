package com.Main.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ContactDetails")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	private String name;
	private String nickname;
	private String email;
	@Column(length = 1000)
	private String about;
	private String work;
	private String phoneno;
	private String imgurl;
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;

	public int getCid() {
		return cid;
	}

	public Contact(int cid, String name, String nickname, String email, String about, String work, String phoneno,
			String imgurl, User user) {
		super();
		this.cid = cid;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.about = about;
		this.work = work;
		this.phoneno = phoneno;
		this.imgurl = imgurl;
		this.user = user;
	}

	public Contact() {
		 
	}

	@Override
	public String toString() {
		return "contact [cid=" + cid + ", name=" + name + ", nickname=" + nickname + ", email=" + email + ", about="
				+ about + ", work=" + work + ", phoneno=" + phoneno + ", imgurl=" + imgurl + ", user=" + user + "]";
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}
