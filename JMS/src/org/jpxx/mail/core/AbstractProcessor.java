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

import java.net.Socket;

/**
 * JMS Processor. The old name of this class is <code>Processor</code>.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.3 $, $Date: 2008/05/30 $
 * @since JMS 0.0.1
 * 
 */
public abstract class AbstractProcessor extends Thread {

    /**
     * The TCP/IP socket over which the POP3 interaction is occurring
     * Client socket
     */
    protected Socket clientSocket;

    public AbstractProcessor(Socket socket) {
        clientSocket = socket;
    }

    /**
     * Do with the Command
     * @since JMS 0.0.1
     * @param handler
     * @param session
     * @return the last action
     */
    protected abstract int doCommand(CommandsHandler handler, Session session);
}