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

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import org.jpxx.mail.core.AbstractServerHandler;

/**
 * Handles Pop3 Server
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/25 $
 * @since JMS 0.0.1
 * 
 */
public class Pop3ServerHandler extends AbstractServerHandler {

    public Pop3ServerHandler(int port) throws IOException {
        super(port);
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = null;
            /**
             * Listen client
             */
            while (true) {
                try {
                    clientSocket = serverSocket.accept();
                    /**
                     * wait for a connection and when one comes in then
                     * tell process to go process it
                     */
                    processor = new Pop3Processor(clientSocket);
                    processor.start();
                } catch (SocketException e) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}