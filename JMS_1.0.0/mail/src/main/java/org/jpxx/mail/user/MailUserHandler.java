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

import org.jpxx.mail.util.EmailAddress;

/**
 * The user who store in database
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/23 22:00:00 $
 * 
 */
interface MailUserHandler {

    /**
     * Ckeck user's login
     * 
     * @param emailAddress
     *            user's mail adress
     * @param password
     *            user's password
     * @return if user match password return true, else return false.
     */
    public boolean check(EmailAddress emailAddress, String password);

    /**
     * Ckeck user's login(APOP Command)
     * 
     * @param emailAddress
     *            user's mail adress
     * @param password
     *            user's password
     * @param id
     *            the sharing id was sent by POP3 Server(APOP command).
     * @return if user match password return true, else return false.
     */
    public boolean check(EmailAddress emailAddress, String password, String id);

    /**
     * Check user exist or not
     * 
     * @param user
     *            The user's name who you want to check
     * @param domain
     * 
     * @return If user exist then return true, otherwise return false.
     */
    public boolean checkUser(String user, String domain);

    /**
     * Check user exist or not
     * 
     * @param emailAddress
     * @return If user exist then return true, else return false.
     */
    public boolean checkUser(EmailAddress emailAddress);

    /**
     * Add a new user with default domain and default mailbox size
     * 
     * @param user
     *            User object
     * @return If add Successfully return true, otherwise return false
     */
    public boolean addUser(User user);

    /**
     * Modify user's password with default domain
     * 
     * @param user
     *            User object
     * @return If modify Successfully return true, otherwise return false
     */
    public boolean modifyUser(User user);

    /**
     * Delete a user from the domain
     * 
     * @param userName
     *            Current user's name who you want delete
     * @param domain
     *            The current user belong to this domain
     * @return If delete Successfully return true, otherwise return false
     */
    public boolean deleteUser(String userName, String domain);

    /**
     * Delete a user
     * 
     * @param emailAddress
     * @return If delete Successfully return true, otherwise return false
     */
    public boolean deleteUser(EmailAddress emailAddress);

    /**
     * List all users in a domain.
     * 
     * @param domain
     *            mail server domain
     * @return users list.
     * 
     * @since JMS 0.0.3
     */
    public List<String> list(String domain);

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
    public User getUser(String userName, String domain);
}
