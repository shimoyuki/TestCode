/****************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.     *
 *                                                              *
 * Copyright 2009 Jun Li( The SOFTWARE ENGINEERING COLLEGE OF   *
 * SiChuan University). All rights reserved.                    *
 *                                                              *
 * Licensed to the JMS under one  or more contributor license   *
 * agreements.  See the LICENCE file  distributed with this     *
 * work for additional information regarding copyright          *
 * ownership.  The JMS licenses this file  you may not use this *
 * file except in compliance  with the License.                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package org.jpxx.mail.domain;

import java.io.File;

import org.jpxx.commons.util.FileUtils;
import org.jpxx.configuration.XmlConfig;
import org.jpxx.mail.Constants;

/**
 * Configures current mail server's domain.
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.mail.domain.DomainHandler.java $, $Date: 2009-4-29
 *          $
 * 
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
     * @return If add successfully ten return true, else return true.
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
     * @return If modify successfully ten return true, else return true.
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
     * @return If delete successfully ten return true, else return true.
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
     * @return Current server domains. If no dimain, return NULL.
     */
    public String[] getDomains() {
        String theDomain = get(DOMAIN);
        if (theDomain == null || theDomain.equals("")) {
            return new String[] { "127.0.0.1" };
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
            return "127.0.0.1";
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
