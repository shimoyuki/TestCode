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

/**
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/23 22:01:00 $
 * 
 */
public class User {

    /**
     * User's login name
     */
    private String userName = null;
    /**
     * User's password
     */
    private String password = null;

    /**
     * Mail server domain
     */
    private String domain   = null;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, String domain) {
        this.userName = userName;
        this.password = password;
        this.domain = domain;
    }

    /**
     * User's login name
     * @return User's login name
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Set User's login name
     * @param userName User's login name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * User's password
     * @return User's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set User's password
     * @param password User's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
