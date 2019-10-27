package com.shizuku.mail.user;


public class User {

	/**
	 * User's login name
	 */
	private String userName = null;
	/**
	 * User's password
	 */
	private String password = null;

	/**
	 * Mail server domain
	 */
	private String domain   = "shizuku.com";
	
	/**
	 * User's level
	 */
	private int level   = 2;
	
	/**
	 * User's memory size
	 */
	private long size   = 1024;
	
	/**
	 * User's telephone
	 */
	private String tel   = null;
	
	/**
	 * User's gender
	 */
	private String gender   = null;
	
	/**
	 * User's head picture
	 */
	private String pciture   = null;

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public User(String userName, String password, String domain) {
		this.userName = userName;
		this.password = password;
		this.domain = domain;
	}

	public User(String userName, String password, String domain, int level,
			long size) {
		super();
		this.userName = userName;
		this.password = password;
		this.domain = domain;
		this.level = level;
		this.size = size;
	}

	public User(String userName, String password, String domain, int level,
			long size, String tel, String gender, String pciture) {
		super();
		this.userName = userName;
		this.password = password;
		this.domain = domain;
		this.level = level;
		this.size = size;
		this.tel = tel;
		this.gender = gender;
		this.pciture = pciture;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPciture() {
		return pciture;
	}

	public void setPciture(String pciture) {
		this.pciture = pciture;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password
				+ ", domain=" + domain + ", level=" + level + ", size=" + size
				+ ", tel=" + tel + ", gender=" + gender + ", pciture="
				+ pciture + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
}
