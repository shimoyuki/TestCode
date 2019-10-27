/****************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.     *
 *                                                              *
 * Copyright 2008 Jun Li(SiChuan University, the School of      *
 * Software Engineering). All rights reserved.                  *
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

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.Logger;

/**
 * Server Handler
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/05/09 10:23:00 $
 */
public abstract class AbstractServerHandler extends Thread {

    /**
     * Creates an instance of Logger and initializes it. 
     * It is to write log for <code>AbstractServerHandler</code>.
     */
    protected Logger            log          = Logger.getLogger(AbstractServerHandler.class);
    protected ServerSocket      serverSocket = null;
    protected AbstractProcessor processor    = null;

    public AbstractServerHandler(int port) throws IOException {
        /**
         * listen to the given port
         */
        serverSocket = new ServerSocket(port);
    }

    /**
     * Get Pop3 Server ServerSocket
     * @return The object of ServerSocket in current port.
     */
    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }
}
