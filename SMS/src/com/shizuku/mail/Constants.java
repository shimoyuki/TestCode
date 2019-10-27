package com.shizuku.mail;

import com.shizuku.mail.util.SMSUtils;

/**
 * <pre>
 * This class defines some constant variables, such as software name, version,
 * etc. Since version 0.0.3, it is assigned a new name to Constants. (The old
 * name is Constant)
 * </pre>
 * 
 */
public final class Constants {

	/**
	 * Define the mail server name.
	 */
	public final static String NAME = "ShizukuMailServer";

	/**
	 * Current version of SMS.
	 */
	public final static String VERSION = "1.0.0";

	/**
	 * User home directory to store their mails.
	 */
	public final static String HOME_DIR = SMSUtils.getClasspath() + NAME
			+ "/MailStore/Domains/";

	/**
	 * Temporary directory.
	 */
	public final static String TEMP_DIR = SMSUtils.getClasspath() + NAME
			+ "/Temp/";

	/**
	 * The Config File Path
	 */
	public final static String CONFIG_FILE_PATH = SMSUtils.getClasspath()
			+ NAME + "/Config/" + "SystemConfig.xml";
	/**
	 * The client connection Timeout(millisecond).
	 */
	public final static int DEFAULT_TIMEOUT = 30000;
}
