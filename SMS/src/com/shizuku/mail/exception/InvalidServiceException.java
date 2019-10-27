package com.shizuku.mail.exception;

/**
 * <code>InvalidServiceException</code> is the subclass of
 * <code>Exception</code>. When a service is not found in current Services Hash
 * Map, throws this exception.
 */
public class InvalidServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param error
	 */
	public InvalidServiceException(String error) {
		super(error);
	}
}
