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

import java.util.ArrayList;
import java.util.Iterator;

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.pop3.Pop3States;
import org.jpxx.mail.core.pop3.message.Mail;
import org.jpxx.mail.exception.ArgumentException;

/**
 * Handle UIDL command. Handler method called upon receipt of a UIDL command.
 * 
 * If an argument was given and the POP3 server issues a positive
 * response with a line containing information for that message.
 * This line is called a "unique-id listing" for that message.
 * 
 * If no argument was given and the POP3 server issues a positive
 * response, then the response given is multi-line.  After the
 * initial +OK, for each message in the maildrop, the POP3 server
 * responds with a line containing information for that message.
 * This line is called a "unique-id listing" for that message.
 * 
 * In order to simplify parsing, all POP3 servers are required to
 * use a certain format for unique-id listings.  A unique-id
 * listing consists of the message-number of the message,
 * followed by a single space and the unique-id of the message.
 * No information follows the unique-id in the unique-id listing.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 19:07:00 $
 * 
 */
public class UidlCommand implements Command, Pop3States {

    /**
     * @see org.jpxx.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        int state = session.getLastAction();
        ArrayList userMailbox = new ArrayList();
        if (state == TRANSACTION) {
            String argument = null;
            try {
                argument = session.getCommandLine().getArgument();
            } catch (ArgumentException ae) {
                argument = null;
            }

            if (argument == null) {
                userMailbox = (ArrayList) session.getOperation().get("USER_MAIL_BOX");
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(OK).append(" ");
                buffer.append("unique-id listing follows");
                session.writeResponse(buffer.toString());

                int count = 1;
                for (Iterator i = userMailbox.iterator(); i.hasNext(); count++) {
                    Mail m = (Mail) i.next();
                    if (!m.getState()) {
                        buffer = new StringBuffer(64);
                        buffer.append(count);
                        buffer.append(" ");
                        buffer.append(m.getName());
                        session.writeResponse(buffer.toString());
                    }
                }
                session.writeResponse(".");
            } else {
                int num = 0;
                try {
                    num = Integer.parseInt(argument);
                    Mail m = (Mail) userMailbox.get(num - 1);
                    if (m.getState()) {
                        StringBuffer buffer = new StringBuffer(64);
                        buffer.append(OK);
                        buffer.append(" ");
                        buffer.append(num);
                        buffer.append(" ");
                        buffer.append(m.getName());
                        session.writeResponse(buffer.toString());
                    } else {
                        StringBuffer buffer = new StringBuffer(64);
                        buffer.append(ERR);
                        buffer.append(" Message (");
                        buffer.append(num);
                        buffer.append(") already deleted.");
                        session.writeResponse(buffer.toString());
                    }
                } catch (IndexOutOfBoundsException npe) {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(ERR);
                    buffer.append(" Message (");
                    buffer.append(num);
                    buffer.append(") does not exist.");
                    session.writeResponse(buffer.toString());
                } catch (NumberFormatException nfe) {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(ERR);
                    buffer.append(" ");
                    buffer.append(argument);
                    buffer.append(" is not a valid number");
                    session.writeResponse(buffer.toString());
                }
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("UIDL not allowed here");
            session.writeResponse(buffer.toString());
        }
    }
}
