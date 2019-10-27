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
package org.jpxx.mail.core.smtp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import org.jpxx.mail.Constants;
import org.jpxx.mail.core.AbstractProcessor;
import org.jpxx.mail.core.CommandLine;
import org.jpxx.mail.core.CommandsHandler;
import org.jpxx.mail.core.ServerState;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.smtp.command.SmtpCommandsHandler;

/**
 * Smtp Server Processor
 * 
 * @author Jun Li
 * @version $Revision: 0.0.3 $, $Date: 2008/05/30 $
 * @since JMS 0.0.1
 * 
 */
public class SmtpProcessor extends AbstractProcessor implements SmtpCommands, ServerState {

    /**
     * Handle a client connection
     * @param socket
     */
    public SmtpProcessor(Socket socket) {
        super(socket);
    }

    @Override
    public void run() {
        Session session = new SmtpSession();
        session.setClientSocket(clientSocket);
        int lastCommand = READY;
        try {
            clientSocket.setSoTimeout(Constants.DEFAULT_TIMEOUT);
            StringBuffer buffer = new StringBuffer(128);
            buffer.append("220");
            buffer.append(" ");
            buffer.append(Constants.NAME);
            buffer.append(" ");
            buffer.append("SMTP Server");
            buffer.append(" ");
            buffer.append("Ready");
            session.writeResponse(buffer.toString());

            CommandsHandler handler = SmtpCommandsHandler.getSingletonInstance();

            while (lastCommand != QUIT) {
                lastCommand = doCommand(handler, session);
            }
        } catch (SocketException se) {
        } finally {
            if (session.getOperation().get("MAIL_HANDLER") != null) {
                MailHandler mh = (MailHandler) session.getOperation().get("MAIL_HANDLER");
                // close connection and send mail
                mh.sendMessage(true);
            }
            session.close();
        }
    }

    /**
     * 
     * @param handler
     * @param session
     * @return
     */
    @Override
    protected int doCommand(CommandsHandler handler, Session session) {
        try {
            CommandLine commandLine = session.getCommandLine(session.readCommandLine());
            try {
                String name = commandLine.getName();
                handler.doCommand(name, session);
                return session.getLastAction();
            } catch (Exception e) {
                return QUIT;
            }
        } catch (IOException e) {
            return QUIT;
        }
    }
}
