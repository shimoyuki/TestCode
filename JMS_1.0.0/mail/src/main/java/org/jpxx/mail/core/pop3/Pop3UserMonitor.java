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
package org.jpxx.mail.core.pop3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jpxx.mail.util.EmailAddress;

/**
 *
 * @author Jun Li
 * @version 0.0.2, Date: 2008/05/06
 * @since 0.0.1
 */
public class Pop3UserMonitor implements Pop3User {

    /**
     * Store the online users.
     */
    private static List<String>    list;
    private static Pop3UserMonitor singletonInstance = null;

    /**
     * To make sure only one instance of Pop3UserList
     * @return singleton instance of Pop3UserList
     */
    public static Pop3UserMonitor getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new Pop3UserMonitor();
        }
        return singletonInstance;
    }

    private Pop3UserMonitor() {
        list = new ArrayList<String>();
    }

    /**
     * @see org.jpxx.mail.Pop3.Pop3User#isOnline(org.jpxx.mail.Util.EmailAddress) 
     * @param emailAddress User emailAddress Object
     * @return If this user is online, return true. Otherwise return false.
     */
    public boolean isOnline(EmailAddress emailAddress) {
        if (emailAddress == null) {
            return false;
        }
        for (Iterator<String> i = list.iterator(); i.hasNext();) {
            String email = i.next();
            if (email.equals(emailAddress.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Appends the user to the end of the list.
     * @see org.jpxx.mail.Pop3.Pop3User#lockUser(org.jpxx.mail.Util.EmailAddress) 
     * @param emailAddress User emailAddress Object
     */
    public void lockUser(EmailAddress emailAddress) {
        if (!isOnline(emailAddress)) {
            list.add(emailAddress.toString());
        }
    }

    /**
     * Remove the user from this list. If the list does not contain
     * the user, it is unchanged.
     * @see org.jpxx.mail.Pop3.Pop3User#unlockUser(org.jpxx.mail.Util.EmailAddress) 
     * @param emailAddress User emailAddress Object
     */
    public void unlockUser(EmailAddress emailAddress) {
        list.remove(emailAddress.toString());
    }
}
