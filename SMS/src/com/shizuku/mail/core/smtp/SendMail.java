package com.shizuku.mail.core.smtp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.shizuku.mail.SysConfig;
import com.shizuku.mail.dns.DNS;
import com.shizuku.mail.domain.DomainHandler;
import com.shizuku.mail.exception.InvalidEmailAddressException;
import com.shizuku.mail.exception.SmtpProtocolException;
import com.shizuku.mail.util.Base64;
import com.shizuku.mail.util.EmailAddress;

/**
 * Mail Transfer Agent.
 * 
 * This file will process the email. Send it to every destnation.
 */
public class SendMail {

	/**
	 * Creates an instance of Logger and initializes it. It is to write log for
	 * <code>SendMail</code>.
	 */
	private static Logger log = Logger.getLogger(SendMail.class);
	private static final int DEFAULT_PORT = 25;
	private BufferedReader reply = null;
	private PrintStream send = null;
	private Socket socket = null;

	/**
	 * 
	 * @param host
	 * @throws java.net.UnknownHostException
	 * @throws java.io.IOException
	 * @throws com.shizuku.mail.Exception.SmtpProtocolException
	 */
	private SendMail(String host) throws UnknownHostException, IOException,
			SmtpProtocolException {
		this(host, DEFAULT_PORT);
	}

	/**
	 * 
	 * @param host
	 * @param port
	 * @throws java.net.UnknownHostException
	 * @throws java.io.IOException
	 * @throws com.shizuku.mail.Exception.SmtpProtocolException
	 */
	private SendMail(String host, int port) throws UnknownHostException,
			IOException, SmtpProtocolException {
		socket = new Socket(host, port);
		reply = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		send = new PrintStream(socket.getOutputStream());

		String rstr = reply.readLine();

		if (!rstr.startsWith("220")) {
			throw new SmtpProtocolException("I want 220 I get: " + rstr);
		}
		while (rstr.indexOf('-') == 3) {
			rstr = reply.readLine();
			if (!rstr.startsWith("220")) {
				throw new SmtpProtocolException("I want 220 I get: " + rstr);
			}
		}
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @param fileName
	 * @param header
	 * @throws java.io.IOException
	 * @throws java.net.ProtocolException
	 * @throws com.shizuku.mail.Exception.SmtpProtocolException
	 */
	private void sendMessage(String from, String to, String fileName,
			List<String> header) throws IOException, ProtocolException,
			SmtpProtocolException {
		String rstr;
		String sstr;

		EmailAddress ea = null;
		try {
			ea = new EmailAddress(to);
		} catch (InvalidEmailAddressException e) {
			return;
		}

		String host = ea.getDomain();

		response("HELO " + host);
		rstr = readLine();

		if (!rstr.startsWith("250")) {
			throw new SmtpProtocolException("I say HELO server says " + rstr);
		}
		sstr = "MAIL FROM:<" + from + ">";

		response(sstr);
		rstr = readLine();

		if (!rstr.startsWith("250")) {
			throw new SmtpProtocolException("I say Mail From:<" + from + ">"
					+ " server says :" + rstr);
		}
		sstr = "RCPT TO:<" + to + ">";
		response(sstr);
		rstr = readLine();
		if (!rstr.startsWith("250")) {
			throw new SmtpProtocolException("I say RCPT TO:<" + to + ">"
					+ " server says " + rstr);
		}

		response("DATA");
		rstr = readLine();

		if (!rstr.startsWith("354")) {
			throw new SmtpProtocolException("I say DATA server says " + rstr);
		}
		// send any headers
		if (header != null) {
			for (int i = 0; i < header.size(); i++) {
				response(header.get(i).toString());
			}
		}

		// send the data - read from file
		FileInputStream fisTheFile = null;

		BufferedReader br = null;
		String strCurrentLine;

		fisTheFile = new FileInputStream(fileName);

		br = new BufferedReader(new InputStreamReader(fisTheFile));

		// send the file one line at a time
		strCurrentLine = br.readLine();
		while (strCurrentLine != null) {
			response(strCurrentLine);
			strCurrentLine = br.readLine();
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				log.info("Thread Error! " + e.toString());
			}
		}

		fisTheFile.close();

		send.print("\r\n");
		send.print(".");
		send.print("\r\n");
		send.flush();

		rstr = readLine();
		if (!rstr.startsWith("250")) {
			throw new SmtpProtocolException(
					"I say .(end of message) server says " + rstr);
		}
	}

	/**
     * 
     */
	private void close() {
		try {
			response("QUIT");
			socket.close();
		} catch (IOException ioe) {
			// As though there's anything I can do about it now...
		}
	}

	/**
	 * 
	 * @param str
	 */
	private void response(String str) {
		send.println(str);
		send.flush();
	}

	/**
	 * 
	 * @return
	 */
	private String readLine() {
		try {
			String line = reply.readLine();
			System.err.println(line);
			return line;
		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * Send some mail from a file, find the mail host from a dns server then
	 * send the mail to the mail host
	 * 
	 * @param from
	 * @param to
	 * @param mailPath
	 * @return if send successfully then return true.
	 */
	public synchronized static boolean send(String from, String to,
			String mailPath) {
		return send(from, to, mailPath, null);
	}

	/**
	 * Send some mail from a file, find the mail host from a dns server then
	 * send the mail to the mail host
	 * 
	 * @param from
	 * @param to
	 * @param mailPath
	 * @param header
	 * @return if send successfully then return true.
	 */
	private synchronized static boolean send(String from, String to,
			String mailPath, List<String> header) {
		// Send OK? Message not sent yet
		boolean isSendOK = false;
		// The destnation mail server
		String mailHost = null;

		List<String> servers = null;
		ArrayList<String> errors = null;
		try {
			EmailAddress ea = new EmailAddress(to);
			mailHost = ea.getDomain();

			DomainHandler dc = new DomainHandler();
			/**
			 * Check if sending mail to self. If so then avoid the DNS lookup
			 */
			if (dc.isExist(mailHost)) {
				servers = new ArrayList<String>();
				// Sending mail to self ,no dns lookup needed
				servers.add("127.0.0.1");
			} else {
				/**
				 * not sending mail to self - do a dns lookup If first lookup
				 * fails then try again.
				 * 
				 * this is since nslookup sometimes times out due to the
				 * overload on the dns server.
				 * 
				 * Try 5 times.
				 */
				for (int i = 0; i < 5; i++) {
					servers = DNS.getMailServer(mailHost);
					/**
					 * If find then break
					 */
					if (servers.size() > 0) {
						break;
					}
				}
			}

			/**
			 * IF not find
			 */
			if (servers.size() == 0) {
				servers.add(ea.getDomain());
				servers.add("smtp." + ea.getDomain());
			}

			/**
			 * Send
			 */
			boolean stop = false;
			for (int i = 0; (i < servers.size() && (!stop)); i++) {
				errors = new ArrayList<String>();
				try {
					/**
					 * Grab the mail host
					 */
					mailHost = servers.get(i).toString();
					SendMail sm = new SendMail(mailHost);
					sm.sendMessage(from, to, mailPath, header);
					sm.close();
					stop = true;
					isSendOK = true;
					System.err.println(mailHost);
				} catch (UnknownHostException uhe) {
					errors.add("Failed to find host - " + mailHost);
					errors.add(uhe.getMessage());
					log.error(uhe.getMessage());
				} catch (ProtocolException pe) {
					errors.add("Some sort of protocol exception on - "
							+ mailHost);
					errors.add(pe.getMessage());
					log.error(pe.getMessage());
				} catch (IOException ioe) {
					errors.add("Error reading / writing to socket on - "
							+ mailHost);
					errors.add(ioe.getMessage());
					log.error(ioe.getMessage());
				} catch (SmtpProtocolException spe) {
					errors.add("SMTP error on - " + mailHost);
					errors.add(spe.getMessage());
					stop = true;
					log.error(spe.getMessage());
				} catch (Exception e) {
					errors.add("Unknown error on - " + mailHost);
					errors.add(e.getMessage());
					log.error("Unknown error on - " + mailHost + " "
							+ e.toString());
				}
			}
			/**
			 * End
			 */
			if (!isSendOK) {
				SysConfig sc = new SysConfig();
				DomainHandler domain = new DomainHandler();
				if (!from.equals(sc.getSystemReplyMail())) {
					List<String> e = new ArrayList<String>();

					e.add("Message-ID: " + ID(Calendar.getInstance()) + "@"
							+ domain.getDefaultDomain());
					e.add("Date: " + messageDate());
					e.add("From: "
							+ new EmailAddress(sc.getSystemReplyMail())
									.getUserName() + " <"
							+ sc.getSystemReplyMail() + ">");
					e.add("To: " + from);
					e.add("Subject: Delivery error");
					e.add("\r\n");
					for (int i = 0; i < errors.size(); i++) {
						e.add(errors.get(i).toString());
					}
					e.add("Original message: ");
					send(sc.getSystemReplyMail(), to, mailPath, e);
				}

			}
			return true;
		} catch (Exception e) {
			log.info(e.toString());
			return false;
		}
	}

	/**
	 * Message date format
	 * 
	 * @return a date string with mail message date format
	 */
	private static String messageDate() {
		Calendar c = Calendar.getInstance();

		String formatted = "";

		String Day[] = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		String Month[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		formatted = Day[c.get(Calendar.DAY_OF_WEEK) - 1] + ", ";
		formatted = formatted + String.valueOf(c.get(Calendar.DAY_OF_MONTH))
				+ " ";
		formatted = formatted + Month[c.get(Calendar.MONTH)] + " ";

		formatted = formatted + String.valueOf(c.get(Calendar.YEAR)) + " ";

		if (c.get(Calendar.HOUR_OF_DAY) < 10) {
			formatted = formatted + "0";
		}
		formatted = formatted + String.valueOf(c.get(Calendar.HOUR_OF_DAY))
				+ ":";
		if (c.get(Calendar.MINUTE) < 10) {
			formatted = formatted + "0";
		}
		formatted = formatted + String.valueOf(c.get(Calendar.MINUTE)) + ":";
		if (c.get(Calendar.SECOND) < 10) {
			formatted = formatted + "0";
		}
		formatted = formatted + String.valueOf(c.get(Calendar.SECOND)) + " ";

		int zoneOffset = -(c.get(Calendar.ZONE_OFFSET) + c
				.get(Calendar.DST_OFFSET)) / (60 * 1000);
		if (zoneOffset < 0) {
			formatted = formatted + "+";
		} else {
			formatted = formatted + "-";
		}

		if (Math.abs(zoneOffset) / 60 < 10) {
			formatted = formatted + "0";
		}
		formatted = formatted + String.valueOf(Math.abs(zoneOffset) / 60);
		if (Math.abs(zoneOffset) % 60 < 10) {
			formatted = formatted + "0";
		}
		formatted = formatted + String.valueOf(Math.abs(zoneOffset) % 60);

		return formatted;
	}

	/**
	 * Returns a temporary mail ID
	 * 
	 * @param c
	 *            An instance of Calendar
	 * @return a temporary mail ID
	 */
	private static String ID(Calendar c) {
		long num = Math.round(Math.random() * 899999 + 100000);
		String mac = c.getTimeInMillis() + "-" + num;
		String id = Base64.encodeStr(mac);
		return id;
	}
}
