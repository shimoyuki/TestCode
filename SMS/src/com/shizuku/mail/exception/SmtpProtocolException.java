package com.shizuku.mail.exception;

/**
 * Catch the error int SMTP Protocol.
 */
public class SmtpProtocolException extends Exception {

	private static final long serialVersionUID = -3911352827335942264L;

	/**
	 * 
	 * @param message
	 */
	public SmtpProtocolException(String message) {
		super(message);
	}
}
