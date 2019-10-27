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

import org.apache.log4j.Logger;
import org.jpxx.mail.Constants;
import org.jpxx.mail.core.AbstractProcessor;
import org.jpxx.mail.core.CommandLine;
import org.jpxx.mail.core.CommandsHandler;
import org.jpxx.mail.core.ServerState;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.pop3.command.Pop3CommandHandler;
import org.jpxx.mail.domain.DomainHandler;
import org.jpxx.mail.util.JMSUtils;

/**
 * Pop3 AbstractProcessor
 * 
 * @author Jun Li
 * @version $Revision: 0.0.3 $, $Date: 2008/05/30 $
 * @since JMS 0.0.1
 * 
 */
public class Pop3Processor extends AbstractProcessor implements Pop3States, ServerState {

    /**
     * Creates an instance of Logger and initializes it. 
     * It is to write log for <code>Pop3Processor</code>.
     */
    private Logger log = Logger.getLogger(Pop3Processor.class);

    /**
     * Handle a client connection
     * @param socket
     */
    public Pop3Processor(Socket socket) {
        super(socket);
    }

    @Override
    public void run() {
        Session session = new Pop3Session();
        session.setClientSocket(clientSocket);
        int lastCommand = READY;
        try {
            clientSocket.setSoTimeout(Constants.DEFAULT_TIMEOUT);
            StringBuffer IDBuffer = new StringBuffer(128);
            IDBuffer.append("<");
            IDBuffer.append(JMSUtils.getID());
            IDBuffer.append("@");
            IDBuffer.append(new DomainHandler().getDefaultDomain());
            IDBuffer.append(">");

            StringBuffer buffer = new StringBuffer(256);
            buffer.append(OK);
            buffer.append(" ");
            buffer.append(Constants.NAME);
            buffer.append(" ");
            buffer.append("POP3 Server");
            buffer.append(" ");
            buffer.append("Ready");
            buffer.append(" ");
            buffer.append(IDBuffer.toString());

            session.addOperation("SHARE_ID", IDBuffer.toString());

            session.writeResponse(buffer.toString());
            session.setLastAction(READY);
            CommandsHandler handler = Pop3CommandHandler.getSingletonInstance();

            while (lastCommand != UPDATE && lastCommand != AUTHENTICATION_QUIT) {
                lastCommand = doCommand(handler, session);
            }
        } catch (SocketException se) {
            log.error(se);
        } finally {
            MailRepository mr = (MailRepository) session.getOperation().get("MAIL_REPOSITORY");
            if (mr != null) {
                mr.unlockMailRepository();
            }
            session.close();
        }
    }

    @Override
    protected int doCommand(CommandsHandler handler, Session session) {
        try {
            CommandLine commandLine = session.getCommandLine(session.readCommandLine());
            try {
                String name = commandLine.getName();
                handler.doCommand(name, session);
                return session.getLastAction();
            } catch (Exception e) {
                return AUTHENTICATION_QUIT;
            }
        } catch (IOException e) {
            return AUTHENTICATION_QUIT;
        }
    }
}