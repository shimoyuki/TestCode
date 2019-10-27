package com.shizuku.mail.user;

import java.util.List;

import com.shizuku.mail.SysConfig;
import com.shizuku.mail.domain.DomainHandler;
import com.shizuku.mail.util.EmailAddress;

public class UserHandler {

	private MailUserHandler handler = null;

	public UserHandler() {
		String memory = new SysConfig().get("Memory");
		if (memory==null?false:memory.equals("db")) {
			handler = new DBUserHandler();
		}
		else{
			handler = new XmlUserHandler();
		}
	}

	/**
	 * Check user's login
	 * 
	 * @param emailAddress
	 *            user's mail adress
	 * @param password
	 *            user's password
	 * @return true if only if emailAddress match password, false otherwise.
	 */
	public boolean check(EmailAddress emailAddress, String password) {
		return handler.check(emailAddress, password);
	}

	/**
	 * Ckeck user's login(APOP Command)
	 * 
	 * @param emailAddress
	 *            user's mail adress
	 * @param password
	 *            user's password
	 * @param id
	 *            the sharing id was sent by POP3 Server(APOP command).
	 * @return true if only if emailAddress and APOP id match password, false
	 *         otherwise.
	 */
	public boolean check(EmailAddress emailAddress, String password, String id) {
		return handler.check(emailAddress, password, id);
	}

	/**
	 * Check user exist or not
	 * 
	 * @param user
	 * @param domain
	 * @return true if user exist, false otherwise.
	 */
	public boolean checkUser(String user, String domain) {
		return handler.checkUser(user, domain);
	}

	/**
	 * Check user exist or not
	 * 
	 * @param emailAddress
	 * @return true if user exist, false otherwise.
	 */
	public boolean checkUser(EmailAddress emailAddress) {
		return handler.checkUser(emailAddress);
	}

	/**
	 * Add a new user with default mailbox size.
	 * 
	 * @param userName
	 *            user's login name
	 * @param password
	 *            user's password
	 * @param domain
	 *            mail server domain
	 * @return If add successfully return true, otherwise return false.
	 */
	public boolean addUser(String userName, String password, String domain) {
		User user = new User(userName, password, domain);
		return handler.addUser(user);
	}

	/**
	 * Modify user's mailbox size.
	 * 
	 * @param userName
	 *            user's name
	 * @param size
	 *            Mailbox size
	 * @return If modify successfully then return true, otherwise return false.
	 */
	public boolean modifyUser(String userName) {
		return modifyUser(userName, null);
	}

	/**
	 * Modify user's password
	 * 
	 * @param userName
	 *            user's name
	 * @param password
	 *            new password
	 * @return If modify successfully then return true, otherwise return false.
	 */
	public boolean modifyUser(String userName, String password) {
		return modifyUser(userName, password, null);
	}

	/**
	 * Modify user's password
	 * 
	 * @param userName
	 *            user's name
	 * @param password
	 *            new password
	 * @param domain
	 *            Mail server domain
	 * @return If modify successfully then return true, otherwise return false.
	 */
	public boolean modifyUser(String userName, String password, String domain) {
		User user = new User(userName, password, domain);
		return handler.modifyUser(user);
	}

	/**
	 * Delete a user from defaut server.
	 * 
	 * @deprecated Replaced by #deleteUser(String userName, String domain) or
	 *             #deleteUser(EmailAddress emailAddress)
	 * 
	 * @param userName
	 *            User's Name
	 * @return If delete right return true, else return false.
	 */
	@Deprecated
	public boolean deleteUser(String userName) {
		String domain = new DomainHandler().getDefaultDomain();
		return this.deleteUser(userName, domain);
	}

	/**
	 * Delete a User.
	 * 
	 * @param userName
	 *            User's Name
	 * @param domain
	 *            User's mail server domain
	 * @return If delete right return true, else return false.
	 */
	public boolean deleteUser(String userName, String domain) {
		return handler.deleteUser(userName, domain);
	}

	/**
	 * Delete a User.
	 * 
	 * @param emailAddress
	 *            EmailAddress Object
	 * @return If delete right return true, else return false.
	 */
	public boolean deleteUser(EmailAddress emailAddress) {
		return handler.deleteUser(emailAddress);
	}

	/**
	 * List all users in a domain.
	 * 
	 * @param domain
	 *            mail server domain
	 * @return users list.
	 * 
	 */
	public List<String> list(String domain) {
		return handler.list(domain);
	}

	/**
	 * Get a user's information
	 * 
	 * @param userName
	 *            User's login name
	 * @param domain
	 *            mail server domain
	 * @return User object. If the user not exist, return NULL.
	 * 
	 */
	public User getUser(String userName, String domain) {
		return handler.getUser(userName, domain);
	}
}
