package com.shizuku.mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.shizuku.mail.core.Server;
import com.shizuku.mail.util.SMSUtils;

/**
 * <p>
 * This is a core class for SMS. You can start, restart or stop it.
 * </p>
 * 
 * 
 */
public class MailServer {

	/**
	 * The software name
	 */
	public final static String SOFTWARE_NAME = Constants.NAME;
	/**
	 * The software Version
	 */
	public final static String SOFTWARE_VERSION = Constants.VERSION;

	/**
	 * Creates an instance of Logger and initializes it. It is to write log for
	 * <code>MailServer</code>.
	 */
	private static Logger log = Logger.getLogger(MailServer.class);
	/**
	 * Singleton instance of MailServer
	 */
	private static MailServer singletonInstance = null;
	/**
	 * Server state. Started or stopped. 1 -started, 0 -stopped
	 */
	private static int state = 0;

	private static Map<String, Server> server = null;

	/**
	 * To make sure start one SMS.
	 * 
	 * @return singleton instance of MailServer
	 */
	public static MailServer getSingletonInstance() {
		if (singletonInstance == null) {
			singletonInstance = new MailServer();
		}
		return singletonInstance;
	}

	private MailServer() {

		InputStream is = null;

		try {
			is = getResourceAsStream("SMS.properties");
			log.debug("load SMS.properties.");
		} catch (Exception e) {
			is = null;
		}

		if (is == null) {
			log.error("Need SMS.properties in the classpath");
			return;
		}

		try {
			server = new HashMap<String, Server>();
			Properties p = new Properties();
			p.load(is);

			Enumeration e = p.keys();
			while (e.hasMoreElements()) {

				String key = null;
				try {
					key = e.nextElement().toString();
					String value = p.get(key).toString();
					int index = value.indexOf(",");

					Server s = null;
					if (index > 0) {
						try {
							String className = value.substring(0, index);
							int port = Integer.parseInt(value
									.substring(index + 1));
							s = (Server) Class.forName(className).newInstance();
							s.setPorts(new int[] { port });

						} catch (Exception ex) {

						}
					} else {
						s = (Server) Class.forName(value).newInstance();
					}
					server.put(key.toUpperCase(), s);
				} catch (Exception ex) {
					log.warn("Service \"" + key + "\" not found.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	/**
	 * Return the thread context class loader if available. Otherwise return
	 * null.
	 *
	 * The thread context class loader is available for JDK 1.2 or later, if
	 * certain security conditions are met.
	 *
	 * @exception LogConfigurationException
	 *                if a suitable class loader cannot be identified.
	 */
	private static ClassLoader getContextClassLoader() {
		ClassLoader classLoader = null;

		if (classLoader == null) {
			try {
				// Are we running on a JDK 1.2 or later system?
				Method method = Thread.class.getMethod("getContextClassLoader");

				// Get the thread context class loader (if there is one)
				try {
					classLoader = (ClassLoader) method.invoke(Thread
							.currentThread());
				} catch (IllegalAccessException e) {
					; // ignore
				} catch (InvocationTargetException e) {
					/**
					 * InvocationTargetException is thrown by 'invoke' when the
					 * method being invoked (getContextClassLoader) throws an
					 * exception.
					 *
					 * getContextClassLoader() throws SecurityException when the
					 * context class loader isn't an ancestor of the calling
					 * class's class loader, or if security permissions are
					 * restricted.
					 *
					 * In the first case (not related), we want to ignore and
					 * keep going. We cannot help but also ignore the second
					 * with the logic below, but other calls elsewhere (to
					 * obtain a class loader) will trigger this exception where
					 * we can make a distinction.
					 */
					if (e.getTargetException() instanceof SecurityException) {
						; // ignore
					} else {
						// Capture 'e.getTargetException()' exception for
						// details
						// alternate: log 'e.getTargetException()', and pass
						// back 'e'.
						throw new RuntimeException("");
					}
				}
			} catch (NoSuchMethodException e) {
				// Assume we are running on JDK 1.1
				; // ignore
			}
		}

		if (classLoader == null) {
			classLoader = MailServer.class.getClassLoader();
		}

		// Return the selected class loader
		return classLoader;
	}

	@SuppressWarnings("unchecked")
	private static InputStream getResourceAsStream(final String name) {
		InputStream is = null;
		try {
			is = new FileInputStream(SMSUtils.getClasspath() + name);
			return is;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			is = null;
			log.debug(SMSUtils.getClasspath() + name + " not found");
		}

		return (InputStream) AccessController
				.doPrivileged(new PrivilegedAction() {
					public Object run() {
						ClassLoader threadCL = getContextClassLoader();

						if (threadCL != null) {
							return threadCL.getResourceAsStream(name);
						} else {
							return ClassLoader.getSystemResourceAsStream(name);
						}
					}
				});
	}

	/**
	 * <p>
	 * Starts SMS. Before you start server, please adds a domain firstly for
	 * server. SMS supports two or more domains.
	 * </p>
	 * 
	 * <p>
	 * This method that must not be executed by two threads simultaneously. Note
	 * the American spelling, <b>synchronized</b>, that all Java terms use. A
	 * crucial block of code you don’t want two threads accessing simultaneously
	 * can be designed to allow only one thread through at a time.
	 * </p>
	 * 
	 */
	public synchronized void startServer() {
		if (state == 1) {
			log.error("Starts failed." + " Server is already started.");
		} else {
			log.info("Start server");
			Set<String> keys = server.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				Server s = server.get(i.next());
				s.start();
			}
			state = 1;
		}
	}

	/**
	 * Restart SMS.
	 * 
	 * <p>
	 * This method that must not be executed by two threads simultaneously.
	 * </p>
	 * 
	 */
	public synchronized void restartServer() {
		if (state == 0) {
			log.error("Restart failed."
					+ " Can't restart here. Server not started.");
		} else {
			Set<String> keys = server.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				Server s = server.get(i.next());
				s.restart();
			}
		}
	}

	/**
	 * Stop SMS.
	 * 
	 * <p>
	 * This method that must not be executed by two threads simultaneously.
	 * </p>
	 * 
	 */
	public synchronized void stopServer() {
		if (state == 0) {
			log.error("Stop failed." + " Server not started at all.");
		} else {
			Set<String> keys = server.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				Server s = server.get(i.next());
				s.stop();
			}
			state = 0;
		}
	}

	/**
	 * Returns server current state. 0 -stopped, 1 -started;
	 * 
	 * @return server current state. 0 -stopped, 1 -started;
	 */
	public static int getState() {
		return state;
	}
}
