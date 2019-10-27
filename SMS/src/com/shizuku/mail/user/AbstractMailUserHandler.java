package com.shizuku.mail.user;

import com.shizuku.mail.util.EmailAddress;

public abstract class AbstractMailUserHandler implements MailUserHandler {

	/**
	 * @see com.shizuku.mail.user.MailUserHandler#check(EmailAddress
	 *      emailAddress, String password)
	 */
	public boolean check(EmailAddress emailAddress, String password) {
		return check(emailAddress, password, null);
	}

	/**
	 * @see com.shizuku.mail.user.MailUserHandler#addUser(User user)
	 */
	public boolean addUser(User user) {
		String domain = user.getDomain();

		if (domain == null || domain.equals("")) {
			return addUser(user.getUserName(), user.getPassword());
		} else {
			return addUser(user.getUserName(), user.getPassword(), domain);
		}
	}

	public abstract boolean addUser(String userName, String password);

	public abstract boolean addUser(String userName, String password,
			String domain);

	public boolean modifyUser(User user) {
		String domain = user.getDomain();

		if (domain == null || domain.equals("")) {
			return modifyUser(user.getUserName(), user.getPassword());
		} else {
			return modifyUser(user.getUserName(), user.getPassword(), domain);
		}
	}

	public abstract boolean modifyUser(String userName, String password);

	public abstract boolean modifyUser(String userName, String password,
			String domain);

	/**
	 * @see MailUserHandler#deleteUser(com.shizuku.mail.Util.EmailAddress)
	 */
	public boolean deleteUser(EmailAddress emailAddress) {
		return this.deleteUser(emailAddress.getUserName(),
				emailAddress.getDomain());
	}
}
