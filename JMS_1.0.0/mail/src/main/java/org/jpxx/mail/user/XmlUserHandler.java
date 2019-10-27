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
package org.jpxx.mail.user;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jpxx.commons.util.FileUtils;
import org.jpxx.commons.util.MD5;
import org.jpxx.mail.Constants;
import org.jpxx.mail.SysConfig;
import org.jpxx.mail.domain.DomainHandler;
import org.jpxx.mail.exception.InvalidEmailAddressException;
import org.jpxx.mail.util.EmailAddress;

/**
 * Xml database
 * 
 * @author Jun Li
 * @version $Revision: 0.0.3 $, $Date: 2008/10/27 $
 * 
 * @since version 0.0.1
 * 
 */
public class XmlUserHandler extends AbstractMailUserHandler {

    /**
     * The file which store the user's information.
     */
    private final static String USER_DATA_FILE = "User.xml";

    /**
     * Creates an instance of Logger and initializes it. It is to write log for
     * <code>XmlUserHandler</code>.
     */
    private Logger              log            = Logger.getLogger(XmlUserHandler.class);
    private SysConfig           sc             = null;
    private DomainHandler       dc             = null;

    public XmlUserHandler() {
        sc = new SysConfig();
        dc = new DomainHandler();
    }

    /**
     * @see org.jpxx.mail.user.MailUserHandler#check(EmailAddress emailAddress,
     *      String password, String id)
     */
    public boolean check(EmailAddress emailAddress, String password, String id) {

        String domain = emailAddress.getDomain();

        if (!dc.isExist(domain)) {
            return false;
        }
        String path = getFilePath(domain);
        User user = null;
        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = builder.build(new File(path));
            user = this.getUser(doc, emailAddress.getUserName(), domain);
        } catch (Exception ex) {
            return false;
        }

        if (user == null) {
            return false;
        }

        String pwd = user.getPassword();

        if (id == null || id.trim().equals("")) {
            return password.equals(pwd);
        }
        String encryptedPwd = MD5.encode(id + pwd);

        if (password.equals(encryptedPwd)) {
            return true;
        }

        return false;
    }

    /**
     * Check user exist or not
     * 
     * @see org.jpxx.mail.user.MailUserHandler#checkUser(String user,String
     *      domain)
     */
    public boolean checkUser(String user, String domain) {
        try {
            EmailAddress ea = new EmailAddress(user + "@" + domain);
            return checkUser(ea);
        } catch (InvalidEmailAddressException ex) {
            log.info(ex.getMessage());
            return false;
        }
    }

    /**
     * @see org.jpxx.mail.user.MailUserHandler#checkUser(EmailAddress
     *      emailAddress)
     */
    public boolean checkUser(EmailAddress emailAddress) {
        String domain = emailAddress.getDomain();
        if (!dc.isExist(domain)) {
            return false;
        }

        String path = getFilePath(domain);

        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = builder.build(new File(path));

            User user = this.getUser(doc, emailAddress.getUserName(), domain);

            if (user != null) {
                return true;
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return false;
    }

    @Override
    public boolean addUser(String userName, String password) {
        String domain = dc.getDefaultDomain();
        if (domain == null) {
            log.error("Please add a domain to server!");
            return false;
        }
        return addUser(userName, password, domain);
    }

    /**
     * @param userName
     * @param password
     * @param domain
     * @return If add successfully then reutrn true, else return false.
     */
    @Override
    public boolean addUser(String userName, String password, String domain) {
        if (!dc.isExist(domain) || this.checkUser(userName, domain)) {
            return false;
        }

        String path = getFilePath(domain);

        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = builder.build(new File(path));

            Element root = doc.getRootElement();
            Element _name = new Element("Name");
            _name.setText(userName);
            Element _password = new Element("Password");
            _password.setText(password);

            Element user = new Element("User");
            user.addContent(_name);
            user.addContent(_password);

            root.addContent(user);

            File f = new File(sc.getHomeDir(userName, domain));
            if (!f.exists()) {
                f.mkdirs();
            }

            return this.save(doc, path);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return false;
    }

    /**
     * 
     * @param userName
     *            Current user's name
     * @param password
     *            User's new password
     * @return If modify Successfully return true, otherwise return false
     */
    @Override
    public boolean modifyUser(String userName, String password) {
        String domain = dc.getDefaultDomain();
        if (domain == null) {
            log.error("Please add a domain to server!");
            return false;
        }
        return modifyUser(userName, password, domain);
    }

    /**
     * @see MailUserHandler#modifyUser(java.lang.String, java.lang.String,
     *      java.lang.String, long)
     * @param userName
     *            Current user's name
     * @param password
     *            User's new password
     * @param domain
     *            User's mailbox domain
     * @return If modify Successfully return true, otherwise return false
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean modifyUser(String userName, String password, String domain) {
        if (!dc.isExist(domain)) {
            return false;
        }

        String path = getFilePath(domain);

        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = builder.build(new File(path));

            Element root = doc.getRootElement();
            List<Element> list = root.getChildren("User");
            for (Iterator<Element> i = list.iterator(); i.hasNext();) {
                Element user = i.next();
                Element _name = user.getChild("Name");
                if (_name.getTextTrim().equals(userName)) {
                    if (password != null && !password.trim().equals("")) {
                        Element _password = user.getChild("Password");
                        _password.setText(password);
                    }

                    return this.save(doc, path);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString());
        }
        return false;
    }

    /**
     * @see MailUserHandler#deleteUser(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public boolean deleteUser(String userName, String domain) {
        if (!dc.isExist(domain) || !this.checkUser(userName, domain)) {
            return false;
        }

        String path = getFilePath(domain);

        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = builder.build(new File(path));

            Element root = doc.getRootElement();
            List<Element> list = root.getChildren("User");

            for (Iterator<Element> i = list.iterator(); i.hasNext();) {
                Element user = i.next();
                Element _name = user.getChild("Name");
                if (_name != null && _name.getTextTrim().equals(userName)) {
                    root.removeContent(user);
                    FileUtils.delete(Constants.HOME_DIR + domain + File.separator + userName
                                     + File.separator);
                    return this.save(doc, path);
                }
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return false;
    }

    /**
     * List all users in a domain.
     * 
     * @param domain
     *            mail server domain
     * @return users list.
     * 
     * @since JMS 0.0.3
     */
    @SuppressWarnings("unchecked")
    public List<String> list(String domain) {
        if (!dc.isExist(domain)) {
            return null;
        }

        String path = getFilePath(domain);

        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = builder.build(new File(path));

            Element root = doc.getRootElement();
            List<Element> list = root.getChildren("User");

            List<String> users = new ArrayList<String>();
            for (Iterator<Element> i = list.iterator(); i.hasNext();) {
                Element user = i.next();
                Element _name = user.getChild("Name");
                if (_name != null) {
                    users.add(_name.getTextTrim());
                }
            }

            return users;
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return null;
    }

    /**
     * Get a user's information
     * 
     * @param userName
     *            User's login name
     * @param domain
     *            mail server domain
     * @return User object
     * 
     * @since JMS 0.0.3
     */
    public User getUser(String userName, String domain) {
        try {
            String path = getFilePath(domain);
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = builder.build(new File(path));
            return getUser(doc, userName, domain);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return null;
    }

    /**
     * @param doc
     * @param userName
     * @return User object
     */
    @SuppressWarnings("unchecked")
    private User getUser(Document doc, String userName, String domain) {
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren("User");
        for (Iterator<Element> i = list.iterator(); i.hasNext();) {
            Element e = i.next();
            if (e.getChild("Name").getText().equals(userName)) {
                User user = new User(userName, e.getChild("Password").getText(), domain);
                return user;
            }
        }
        return null;
    }

    /**
     * Create Xml config File
     * 
     * @param path
     *            Config file path
     */
    private void create(String filePath) {
        Element root = new Element("UserList");
        Document doc = new Document();
        doc.setContent(root);

        FileWriter writer;
        XMLOutputter outputter = new XMLOutputter();
        try {
            writer = new FileWriter(filePath);
            Format format = Format.getPrettyFormat();
            format.setEncoding("UTF-8");
            outputter.setFormat(format);
            outputter.output(doc, writer);
            writer.close();
        } catch (IOException e) {
        }
    }

    /**
     * 
     * @param domain
     * @return The file path.
     */
    private String getFilePath(String domain) {
        String userXmlFileDir = Constants.HOME_DIR + domain + File.separator;
        String filePath = userXmlFileDir + USER_DATA_FILE;
        // log.error(emailAddress.toString());
        File f = new File(userXmlFileDir);
        if (!f.exists()) {
            f.mkdirs();
        }

        f = new File(filePath);
        if (!f.exists()) {
            create(filePath);
        }
        return filePath;
    }

    /**
     * Save document.
     * 
     * @param doc
     *            Document
     * @param path
     *            The xml file path.
     * @return If svae successfully then return true.
     */
    private boolean save(Document doc, String path) {
        XMLOutputter outputter = new XMLOutputter();
        try {
            FileWriter writer = new FileWriter(path);
            Format format = Format.getPrettyFormat();
            format.setEncoding("UTF-8");
            format.setIndent("        ");

            outputter.setFormat(format);
            outputter.output(doc, writer);
            writer.close();

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
