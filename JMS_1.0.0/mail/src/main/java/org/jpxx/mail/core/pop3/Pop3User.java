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

import org.jpxx.mail.util.EmailAddress;

/**
 * This is an interface to define some functions about the pop users
 * who are online.
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/05/06 22:17:00 $
 */
public interface Pop3User {
    /**
     * Check user is online or not. A user can't login two times at the same
     * time before logout.
     * @param emailAddress User emailAddress Object
     * @return If current user is online, return true. Otherwise return false.
     */
    public boolean isOnline(EmailAddress emailAddress);

    /**
     * When a user login, don't lock he or she until it QUIT or connectless.
     * @param emailAddress User emailAddress Object
     */
    public void lockUser(EmailAddress emailAddress);

    /**
     * When a user logout or connectless, unlock it.
     * @param emailAddress User emailAddress Object
     */
    public void unlockUser(EmailAddress emailAddress);
}
