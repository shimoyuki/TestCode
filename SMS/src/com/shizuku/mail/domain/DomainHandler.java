package com.shizuku.mail.domain;

import java.io.File;

import com.shizuku.configuration.XmlConfig;
import com.shizuku.mail.Constants;
import com.shizuku.mail.util.FileUtils;

/**
 * Configures current mail server's domain.
 */
public class DomainHandler extends XmlConfig {

	private static final String DOMAIN = "Domain";

	public DomainHandler() {
		super(Constants.CONFIG_FILE_PATH);
	}

	/**
	 * Add a new domain to this server
	 * 
	 * @param domain
	 *            Server domain(Host name or IP address)
	 * @return If add successfully then return true, else return false.
	 */
	public boolean addDomain(String domain) {
		if (domain == null) {
			return false;
		}

		domain = domain.replace(" ", "");

		File f = new File(Constants.HOME_DIR + domain + File.separator);
		if (!f.exists()) {
			f.mkdirs();
		}

		String theDomain = get(DOMAIN);
		if (theDomain == null || theDomain.equals("")) {
			return put(DOMAIN, domain) != null;
		}

		if (theDomain.contains(domain)) {
			return false;
		} else {
			return put(DOMAIN, theDomain + "," + domain) != null;
		}
	}

	/**
	 * Modify domain
	 * 
	 * @param oldDomain
	 *            The domain you want to modify
	 * @param newDomain
	 *            new domain
	 * @return If modify successfully then return true, else return false.
	 */
	public boolean modifyDomain(String oldDomain, String newDomain) {
		if (oldDomain == null || newDomain == null) {
			return false;
		}

		newDomain = newDomain.replace(" ", "");

		String theDomain = get(DOMAIN);
		if (theDomain.equals("")) {
			return false;
		}

		if (!theDomain.contains(oldDomain)) {
			return false;
		} else {
			theDomain = theDomain.replace(oldDomain, newDomain);
			File f = new File(Constants.HOME_DIR + oldDomain);
			f.renameTo(new File(Constants.HOME_DIR + newDomain));
			return put(DOMAIN, theDomain) != null;
		}
	}

	/**
	 * 
	 * @param domain
	 *            The domain you want to delete
	 * @return If delete successfully then return true, else return false.
	 */
	public boolean deleteDoamin(String domain) {
		String theDomain = get(DOMAIN);
		if (theDomain.equals("")) {
			return false;
		}

		if (!theDomain.contains(domain)) {
			return false;
		} else {
			/**
			 * Delete all user and and their files
			 */
			try {
				FileUtils.delete(Constants.HOME_DIR + domain);
			} catch (Exception e) {
				return false;
			}

			if (theDomain.lastIndexOf(",") == -1) {
				/**
				 * Delete the last domain
				 */
				return put(DOMAIN, "") != null;
			}

			if (theDomain.startsWith(domain)) {
				theDomain = theDomain.replace(domain + ",", "");
			} else {
				theDomain = theDomain.replace("," + domain, "");
			}
			return put(DOMAIN, theDomain) != null;
		}
	}

	/**
	 * Get a set of domains
	 * 
	 * @return Current server domains. If no domain, return NULL.
	 */
	public String[] getDomains() {
		String theDomain = get(DOMAIN);
		if (theDomain == null || theDomain.equals("")) {
			return new String[] { "shizuku.com" };
		} else {
			if (theDomain.lastIndexOf(",") == -1) {
				String domains[] = new String[1];
				domains[0] = theDomain;
				return domains;
			} else {
				return theDomain.split(",");
			}
		}
	}

	/**
	 * Get server default domain
	 * 
	 * @return server default domain
	 */
	public String getDefaultDomain() {
		String domains[] = getDomains();
		if (domains == null) {
			return "shizuku.com";
		} else {
			return domains[0];
		}
	}

	/**
	 * Check domain exist or not
	 * 
	 * @param domain
	 *            The domain you want to check.
	 * @return domain exist return true, otherwise return false
	 */
	public boolean isExist(String domain) {
		String domains[] = getDomains();
		if (domains == null) {
			return false;
		} else {

			for (int i = 0; i < domains.length; i++) {
				if (domains[i].equals(domain)) {
					return true;
				}
			}
			return false;
		}
	}
}
