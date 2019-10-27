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
package org.jpxx.mail.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jpxx.mail.exception.InvalidEmailAddressException;

/**
 * <code>EmailAddress</code> the Class of email address
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.mail.util.EmailAddress.java $, $Date: 2009-4-29 $
 * 
 */
public class EmailAddress {

    /**
     * Email address
     */
    private String  email;
    /**
     * 
     */
    private boolean userNameIncludeDomain = false;

    /**
     * 
     * @param email
     *            Email address
     */
    public EmailAddress(String email) throws InvalidEmailAddressException {
        if (email == null) {
            throw new InvalidEmailAddressException("Email address is null");
        } else if (!isEmail(email)) {
            throw new InvalidEmailAddressException("Email address error");
        } else {
            this.email = email;
        }
    }

    /**
     * 
     * @param email
     *            Email address
     * @param userNameIncludeDomain
     *            Whether the username include domain
     */
    public EmailAddress(String email, boolean userNameIncludeDomain)
                                                                    throws InvalidEmailAddressException {
        this(email);
        this.userNameIncludeDomain = userNameIncludeDomain;
    }

    /**
     * Get username from email address
     * 
     * @return the user name from email address
     */
    public String getUserName() {
        if (userNameIncludeDomain) {
            return this.email;
        } else {
            return email.substring(0, email.lastIndexOf("@"));
        }
    }

    /**
     * Get mail server domain
     * 
     * @return the domain of current email address
     */
    public String getDomain() {
        return email.substring(email.lastIndexOf("@") + 1);
    }

    /**
     * Verify email address
     * 
     * @param email
     *            mail address
     * @return if it is an email address return true, else return true.
     */
    public static boolean isEmail(String email) {
        if (email == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",
            Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Grab mail address from a line with <>
     * 
     * @param str
     *            a string line
     * @return mail address
     */
    public static String grabMailAddress(String str) {
        if (str == null || str.trim().equals("")) {
            return null;
        }
        String strAddress = str.trim();
        int intFoundStart;
        int intFoundEnd;
        /**
         * check if the address contains a < if so then just use whats inside
         * otherwise use the address given
         */
        intFoundStart = str.indexOf('<');
        if (intFoundStart != -1) {
            // found it strip everything from inside
            intFoundEnd = str.indexOf('>');
            if (intFoundEnd != -1) {
                strAddress = str.substring(intFoundStart + 1, intFoundEnd);
            } else {
                strAddress = str.substring(intFoundStart + 1, str.length());
            }
        } else {
            // not found
            strAddress = null;
        }
        return strAddress;
    }

    /**
     * EmailAddress Object to email address String
     * 
     * @return email address
     */
    @Override
    public String toString() {
        return email;
    }
}
