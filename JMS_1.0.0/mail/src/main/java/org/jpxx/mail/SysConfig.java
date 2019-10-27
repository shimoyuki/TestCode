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
package org.jpxx.mail;

import java.io.File;

import org.jpxx.configuration.XmlConfig;
import org.jpxx.mail.domain.DomainHandler;

/**
 * This clsss is to manage JMS config.
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.mail.SysConfig.java $, $Date: 2009-4-29 $
 * 
 */
public class SysConfig extends XmlConfig {

    private static final String SMTP_PORT                  = "SmtpPort";
    private static final String POP3_PORT                  = "Pop3Port";
    private static final String SYSYTEM_REPLY_MAIL         = "SystemReplyMail";

    private static final int    DEFAULT_SMTP_PORT          = 25;
    private static final int    DEFAULT_POP3_PORT          = 110;
    private static final String DEFAULT_SYSYTEM_REPLY_MAIL = "postmaster@jpxx.org";

    public SysConfig() {
        super(Constants.CONFIG_FILE_PATH);
    }

    /**
     * JMS supports one or more SMTP server threads in one server with different
     * ports.
     * 
     * @return the array of smtp server ports
     */
    public int[] getSmtpPort() {
        String smtpPort = this.get(SMTP_PORT);
        if (smtpPort == null || smtpPort.equals("")) {
            this.put(SMTP_PORT, String.valueOf(DEFAULT_SMTP_PORT));
            return getDefaultSmtpPort();
        } else {
            return getDefaultPort(smtpPort);
        }
    }

    /**
     * JMS supports one or more POP3 server threads in one server with different
     * ports.
     * 
     * @return the array of pop3 server ports
     */
    public int[] getPop3Port() {
        String pop3Port = this.get(POP3_PORT);
        if (pop3Port == null || pop3Port.equals("")) {
            this.put(POP3_PORT, String.valueOf(DEFAULT_POP3_PORT));
            return getDefaultPop3Port();
        } else {
            return getDefaultPort(pop3Port);
        }
    }

    public String getSystemReplyMail() {
        DomainHandler dc = new DomainHandler();
        String domain = dc.getDefaultDomain();
        if (domain == null) {
            return DEFAULT_SYSYTEM_REPLY_MAIL;
        }

        String reply = get(SYSYTEM_REPLY_MAIL);
        if (reply == null) {
            put(SYSYTEM_REPLY_MAIL, "postmaster@" + domain);
            return "postmaster@" + domain;
        } else {
            return get(SYSYTEM_REPLY_MAIL);
        }
    }

    /**
     * 
     * @return User's home directory
     */
    public String getHomeDir(String userName, String domain) {
        String path = Constants.HOME_DIR + domain + File.separator + userName + File.separator
                      + "MailBox" + File.separator;
        return path;
    }

    public String getTempDir() {
        File f = new File(Constants.TEMP_DIR);
        if (!f.exists()) {
            f.mkdirs();
        }
        return Constants.TEMP_DIR;
    }

    private int[] getDefaultSmtpPort() {
        return getDefaultPort(DEFAULT_SMTP_PORT);
    }

    private int[] getDefaultPop3Port() {
        return getDefaultPort(DEFAULT_POP3_PORT);
    }

    private int[] getDefaultPort(String port) {
        if (port.lastIndexOf(",") == -1) {
            return getDefaultPort(Integer.parseInt(port));
        } else {
            String p[] = port.split(",");
            int _p[] = new int[p.length];
            for (int i = 0; i < p.length; i++) {
                try {
                    _p[i] = Integer.parseInt(p[i]);
                } catch (Exception e) {
                    throw new UnknownError(_p[i] + " is not a number!");
                }
            }
            return _p;
        }
    }

    private int[] getDefaultPort(int port) {
        int _port[] = new int[1];
        _port[0] = port;
        return _port;
    }

}
