package com.shizuku.mail.test;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 *
 * 
 * 
 * 
 */
public class SendMail {

	private final static String contentType = "text/html;charset=GBK";
	private static Logger log = Logger.getLogger(SendMail.class);

	public static boolean send(String to, String subject, String message) {
		String smtp = "127.0.0.1";
		String user = "system@127.0.0.1";
		String pass = "system";
		String mail = "system@shizuku.com";
		return send(user, pass, mail, to, smtp, subject, message);
	}

	public static boolean send(final String user, final String pass,
			String fromAddr, String toAddr, String host, String subject,
			String message) {
		try {
			Properties props = new Properties();

			props.put("mail.smtp.port", 25);
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.localhost", host);
			props.put("mail.debug", "true");
			props.put("mail.smtp.from", fromAddr);
			props.put("mail.smtp.auth", "true");

			PopupAuthenticator pa = new PopupAuthenticator(user, pass);
			Session sess = Session.getDefaultInstance(props, pa);
			// Session sess = Session.getInstance(props, pa);

			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(message, contentType);
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(bodyPart);
			Message msg = new MimeMessage(sess);
			msg.setFrom(new InternetAddress(fromAddr));
			InternetAddress[] tos = InternetAddress.parse(toAddr);
			msg.setRecipients(Message.RecipientType.TO, tos);
			msg.setContent(message, contentType);
			msg.setSubject(subject);
			msg.setHeader("X-Priority", "1");

			Transport t = sess.getTransport("smtp");
			t.connect(host, 25, user, pass);
			t.sendMessage(msg, msg.getAllRecipients());
			// Transport.send(msg);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	/**
	 *
	 * Authenticator
	 */
	static class PopupAuthenticator extends Authenticator {

		private String user;
		private String pass;

		public PopupAuthenticator(String user, String pass) {
			this.user = user;
			this.pass = pass;
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, pass);
		}
	}
}