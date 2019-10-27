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
package org.jpxx.mail.core.pop3.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.pop3.MailRepository;
import org.jpxx.mail.core.pop3.Pop3States;
import org.jpxx.mail.core.pop3.message.Mail;
import org.jpxx.mail.exception.ArgumentException;

/**
 * Handle TOP command.
 * a message-number (required) which may NOT refer to to a
 * message marked as deleted, and a non-negative number
 * of lines (required).<p>
 * 
 * If the POP3 server issues a positive response, then the
 * response given is multi-line. After the initial +OK, the
 * POP3 server sends the headers of the message, the blank
 * line separating the headers from the body, and then the
 * number of lines of the indicated message’s body, being
 * careful to byte-stuff the termination character (as with
 * all multi-line responses).<p>
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 19:42:00 $
 * 
 */
public class TopCommand implements Command, Pop3States {

    /**
     * @see org.jpxx.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The session of client and server
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        int state = session.getLastAction();

        String argument1 = null;
        String argument2 = null;
        try {
            argument1 = session.getCommandLine().getArgument(1);
            argument2 = session.getCommandLine().getArgument(2);
        } catch (ArgumentException ae) {
        }
        if (state == TRANSACTION) {
            int num = 0;
            int lines = 0;
            try {
                num = Integer.parseInt(argument1);
                lines = Integer.parseInt(argument2);
            } catch (NumberFormatException nfe) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Usage: TOP [mail number] [Line number]");
                session.writeResponse(buffer.toString());
                return;
            }

            try {
                ArrayList userMailbox = (ArrayList) session.getOperation().get("USER_MAIL_BOX");
                Mail m = (Mail) userMailbox.get(num - 1);
                MailRepository mr = (MailRepository) session.getOperation().get("MAIL_REPOSITORY");
                if (!m.getState()) {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(OK);
                    buffer.append(" ");
                    buffer.append("Message follows");
                    session.writeResponse(buffer.toString());
                    try {
                        mr.write(
                            new PrintWriter(session.getClinetSocket().getOutputStream(), true), m,
                            lines);
                    } finally {
                        session.writeResponse(".");
                    }
                } else {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(ERR);
                    buffer.append(" ");
                    buffer.append("Message (");
                    buffer.append(num);
                    buffer.append(") already deleted.");
                    session.writeResponse(buffer.toString());
                }
            } catch (IOException ioe) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Error while retrieving message.");
                session.writeResponse(buffer.toString());
            } catch (IndexOutOfBoundsException iob) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Message (");
                buffer.append(num);
                buffer.append(") does not exist.");
                session.writeResponse(buffer.toString());
            } catch (Exception e) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Error while retrieving message.");
                session.writeResponse(buffer.toString());
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("TOP Not allowed Here.");
            session.writeResponse(buffer.toString());
        }
    }
}
