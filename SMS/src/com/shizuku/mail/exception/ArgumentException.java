package com.shizuku.mail.exception;

/**
 * <code>ArgumentException</code> is the subclass of <code>Exception</code>.
 * When the arguments are not found or have bad format, throws this exception.
 * 
 */
public class ArgumentException extends Exception {

	private static final long serialVersionUID = 7681367362395495895L;
	private String message = null;

	public ArgumentException() {
		super();
	}

	public ArgumentException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * Returns a short description of this throwable.
	 * 
	 * @return a string representation of this throwable.
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(256);
		sb.append(message);
		sb.append("The command line includes command name and zero or more "
				+ "arguments, which must be separated by <SP>. ");
		sb.append(super.toString());
		return sb.toString();
	}
}
