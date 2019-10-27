package com.shizuku.mail.core.smtp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.shizuku.mail.SysConfig;
import com.shizuku.mail.domain.DomainHandler;
import com.shizuku.mail.exception.InvalidEmailAddressException;
import com.shizuku.mail.util.EmailAddress;

/**
 * <tt>MailHandler</tt> is to create an email with client's user. And sends it
 * to the destination mailboxs after creating successfully.
 * 
 */
public class MailHandler {

	private String from = null;
	private ArrayList<String> to = null;
	private String tempFilePath = null;
	private String tempFileName = null;
	private DataOutputStream dos = null;
	private FileOutputStream fos = null;
	private SysConfig sc = null;
	/**
	 * Creates an instance of Logger and initializes it. It is to write log for
	 * <code>MailHandler</code>.
	 */
	private Logger log = Logger.getLogger(MailHandler.class);

	/**
	 * 
	 * @param from
	 * @param to
	 */
	public MailHandler(String from, ArrayList<String> to) {
		this.from = from;
		this.to = to;

		sc = new SysConfig();
		if(sc.get("Memory") == null){
			sc.setMemory("db",  "jdbc:mysql://localhost:3306/SMS", "root", "kagari8243");
		}
		tempFileName = getRandomTempFileName();
		try {
			tempFilePath = sc.getTempDir() + tempFileName;
			fos = new FileOutputStream(tempFilePath, false);
			dos = new DataOutputStream(fos);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Write a new line to file
	 * 
	 * @param str
	 * @return If error occoured return false, otherwise return true.
	 */
	public boolean writeLine(String str) {
		try {
			dos.writeBytes(str + "\r\n");
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * End the message and close the file;
	 * 
	 * @return If error occoured return false, otherwise return true.
	 */
	public boolean closeMessage() {
		try {
			if (dos != null) {
				dos.close();
			}

			if (fos != null) {
				fos.close();
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Abort the message which was sent. Delete the file if it was created
	 * 
	 * @return If error occoured return false, otherwise return true.
	 */
	public boolean abortMessage() {
		if (fos != null) {
			try {
				fos.close();
				new File(tempFilePath).delete();
				return true;
			} catch (Exception e) {
				log.error(e);
				return false;
			}
		}
		return false;
	}

	/**
	 * A message to mail after the current client hangs up
	 * 
	 * @param flag
	 *            flag=false, abort this message. else send this message
	 * @return If error occoured return false, otherwise return true.
	 */
	public boolean sendMessage(boolean flag) {
		DomainHandler domain = new DomainHandler();
		EmailAddress ea = null;

		if (!flag) {
			return abortMessage();
		} else {
			try {
				for (Iterator<String> i = to.iterator(); i.hasNext();) {
					String _to = i.next();
					ea = new EmailAddress(_to);
					// Local user
					if (domain.isExist(ea.getDomain())) {
						copyFile(tempFilePath,
								sc.getHomeDir(ea.getUserName(), ea.getDomain())
										+ tempFileName);
					} // remote User
					else {
						SendMail.send(from, _to, tempFilePath);
					}
				}
				new File(tempFilePath).delete();
				return true;
			} catch (InvalidEmailAddressException e) {
				log.error(e.getMessage());
				return false;
			} catch (Exception e) {
				log.error(e.getMessage());
				return false;
			}
		}
	}

	/**
	 * 
	 * @param srcPath
	 * @param destPath
	 * @throws java.lang.Exception
	 * 
	 */
	private static void copyFile(String srcPath, String destPath)
			throws IOException {
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);

		if (destFile.exists()) {
			destFile.delete();
		} else {
			String destDir;
			if (destPath.lastIndexOf(File.separator) != -1) {
				destDir = destPath.substring(0,
						destPath.lastIndexOf(File.separator));
				File dir = new File(destDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
			}
		}

		DataInputStream dis = new DataInputStream(new BufferedInputStream(
				new FileInputStream(srcFile)));
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(destFile)));
		int i;
		while ((i = dis.read()) != -1) {
			dos.write(i);
		}

		if (dis != null) {
			dis.close();
		}

		if (dos != null) {
			dos.close();
		}
	}

	/**
	 * Create a temp file name
	 * 
	 * @return temp file name
	 */
	private String getRandomTempFileName() {
		Calendar c = Calendar.getInstance();
		long num = Math.round(Math.random()*99999);
		return c.getTimeInMillis() + "-" +String.format("%05d", num) + ".sms";
		//return c.getTimeInMillis() + "-" + Math.round(Math.random() * 8999 + 1000) + ".sms";
	}
}