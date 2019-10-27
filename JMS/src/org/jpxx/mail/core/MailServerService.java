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
package org.jpxx.mail.core;

import java.util.HashMap;

/**
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version $ org.jpxx.mail.core.MailServerService.java $, $Date: 2009-4-29 $
 * 
 */
public class MailServerService implements ServerService {

    private HashMap<String, Object> map = null;

    public MailServerService() {
        /**
         * Initials an empty <tt>HashMap</tt> with the specified initial
         * capacity and load factor.
         */
        map = new HashMap<String, Object>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpxx.mail.core.ServerService#addService(java.lang.String,
     * java.lang.Object)
     */
    public void addService(String name, Object service) {
        map.put(name, service);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpxx.mail.core.ServerService#getService()
     */
    public HashMap<String, Object> getService() {
        return this.map;
    }

}
