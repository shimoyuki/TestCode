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

import java.util.List;

import org.jpxx.mail.domain.DomainHandler;
import org.jpxx.mail.util.EmailAddress;

/**
 * Handle user
 * 
 * @author Jun Li
 * @version 0.0.2, Date: 2008/05/08
 * @since 0.0.1
 */
public class UserHandler {

    private MailUserHandler handler = null;

    public UserHandler() {
        handler = new XmlUserHandler();
    }

    /**
     * Check user's login
     * 
     * @param emailAddress
     *            user's mail adress
     * @param password
     *            user's password
     * @return true if only if emailAddress match password, false otherwise.
     */
    public boolean check(EmailAddress emailAddress, String password) {
        return handler.check(emailAddress, password);
    }

    /**
     * Ckeck user's login(APOP Command)
     * 
     * @param emailAddress
     *            user's mail adress
     * @param password
     *            user's password
     * @param id
     *            the sharing id was sent by POP3 Server(APOP command).
     * @return true if only if emailAddress and APOP id match password, false
     *         otherwise.
     */
    public boolean check(EmailAddress emailAddress, String password, String id) {
        return handler.check(emailAddress, password, id);
    }

    /**
     * Check user exist or not
     * 
     * @param user
     * @param domain
     * @return true if user exist, false otherwise.
     */
    public boolean checkUser(String user, String domain) {
        return handler.checkUser(user, domain);
    }

    /**
     * Check user exist or not
     * 
     * @param emailAddress
     * @return true if user exist, false otherwise.
     */
    public boolean checkUser(EmailAddress emailAddress) {
        return handler.checkUser(emailAddress);
    }

    /**
     * Add a new user with default mailbox size.
     * 
     * @param userName
     *            user's login name
     * @param password
     *            user's password
     * @param domain
     *            mail server domain
     * @return If add successfully return true, otherwise return false.
     */
    public boolean addUser(String userName, String password, String domain) {
        User user = new User(userName, password, domain);
        return handler.addUser(user);
    }

    /**
     * Modify user's mailbox size.
     * 
     * @param userName
     *            user's name
     * @param size
     *            Mailbox size
     * @return If modify successfully then return true, otherwise return false.
     */
    public boolean modifyUser(String userName) {
        return modifyUser(userName, null);
    }

    /**
     * Modify user's password
     * 
     * @param userName
     *            user's name
     * @param password
     *            new password
     * @return If modify successfully then return true, otherwise return false.
     */
    public boolean modifyUser(String userName, String password) {
        return modifyUser(userName, password, null);
    }

    /**
     * Modify user's password
     * 
     * @param userName
     *            user's name
     * @param password
     *            new password
     * @param domain
     *            Mail server domain
     * @return If modify successfully then return true, otherwise return false.
     */
    public boolean modifyUser(String userName, String password, String domain) {
        User user = new User(userName, password, domain);
        return handler.modifyUser(user);
    }

    /**
     * Delete a user from defaut server.
     * 
     * @deprecated Replaced by #deleteUser(String userName, String domain) or
     *             #deleteUser(EmailAddress emailAddress)
     * 
     * @since JMS 0.0.1
     * @param userName
     *            User's Name
     * @return If delete right return true, else return false.
     */
    @Deprecated
    public boolean deleteUser(String userName) {
        String domain = new DomainHandler().getDefaultDomain();
        return this.deleteUser(userName, domain);
    }

    /**
     * Delete a User.
     * 
     * @param userName
     *            User's Name
     * @param domain
     *            User's mail server domain
     * @return If delete right return true, else return false.
     */
    public boolean deleteUser(String userName, String domain) {
        return handler.deleteUser(userName, domain);
    }

    /**
     * Delete a User.
     * 
     * @param emailAddress
     *            EmailAddress Object
     * @return If delete right return true, else return false.
     */
    public boolean deleteUser(EmailAddress emailAddress) {
        return handler.deleteUser(emailAddress);
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
    public List<String> list(String domain) {
        return handler.list(domain);
    }

    /**
     * Get a user's information
     * 
     * @param userName
     *            User's login name
     * @param domain
     *            mail server domain
     * @return User object. If the user not exist, return NULL.
     * 
     * @since JMS 0.0.3
     */
    public User getUser(String userName, String domain) {
        return handler.getUser(userName, domain);
    }
}
