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

import org.apache.log4j.Logger;

/**
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.mail.core.AbstractServer.java $, $Date: 2009-4-29 $
 * @since JMS 0.0.1
 */
public abstract class AbstractServer implements Server {

    /**
     * Creates an instance of Logger and initializes it. It is to write log for
     * <code>AbstractServer</code>.
     */
    private Logger          log = Logger.getLogger(AbstractServer.class);
    protected ServerService service;
    protected int[]         ports;

    public AbstractServer() {
        /**
         * Initials ServerService
         */
        service = new MailServerService();
    }

    /**
     * @see Server#restart()
     * @since JMS 0.0.1
     */
    public void restart() {
        log.info("Now Stop Server ......");
        stop();
        log.info("Now Start Server ......");
        start();
        log.info("Restart Server OK.");
    }

    /**
     * Clear services
     * 
     * @since JMS 0.0.1
     */
    protected void clearServcice() {
        service.getService().clear();
    }

    public int[] getPorts() {
        return this.ports;
    }

    public void setPorts(int[] ports) {
        this.ports = ports;
    }
}
