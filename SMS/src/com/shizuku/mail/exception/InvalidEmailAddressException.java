package com.shizuku.mail.exception;

/**
 * Signals that an email address is invalid .
 * 
 */
public class InvalidEmailAddressException extends Exception {

	private static final long serialVersionUID = -7273172411454239531L;

	public InvalidEmailAddressException() {
		super();
	}

	public InvalidEmailAddressException(String message) {
		super(message);
	}
}
