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
 * Handle LIST command
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 18:42:00 $
 * 
 */
public class ListCommand implements Command, Pop3States {

    /**
     * @see org.jpxx.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of server and client
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        int state = session.getLastAction();
        if (state == TRANSACTION) {
            String argument = null;
            try {
                argument = session.getCommandLine().getArgument();
            } catch (ArgumentException ae) {
                argument = null;
            }

            ArrayList userMailbox = (ArrayList) session.getOperation().get("USER_MAIL_BOX");

            if (argument == null) {
                long size = 0;
                int count = 0;
                try {
                    for (Iterator i = userMailbox.iterator(); i.hasNext();) {
                        Mail m = (Mail) i.next();
                        if (!m.getState()) {
                            size += m.getSize();
                            count++;
                        }
                    }

                    StringBuffer buffer = new StringBuffer(32);
                    buffer.append(OK);
                    buffer.append(" ");
                    buffer.append(count);
                    buffer.append(" ");
                    buffer.append(size);

                    session.writeResponse(buffer.toString());
                    count = 1;
                    for (Iterator i = userMailbox.iterator(); i.hasNext(); count++) {
                        Mail m = (Mail) i.next();
                        if (!m.getState()) {
                            buffer = new StringBuffer(32);
                            buffer.append(count);
                            buffer.append(" ");
                            buffer.append(m.getSize());
                            session.writeResponse(buffer.toString());
                        }
                    }
                    session.writeResponse(".");
                } catch (Exception e) {
                    StringBuffer buffer = new StringBuffer(128);
                    buffer.append(ERR);
                    buffer.append(" ");
                    buffer.append(e.getMessage());
                    session.writeResponse(buffer.toString());
                }
            } else {
                int num = 1;
                try {
                    num = Integer.parseInt(argument);
                    Mail m = (Mail) userMailbox.get(num - 1);
                    if (!m.getState()) {
                        StringBuffer buffer = new StringBuffer(64);
                        buffer.append(OK);
                        buffer.append(" ");
                        buffer.append(num);
                        buffer.append(" ");
                        buffer.append(m.getSize());
                        session.writeResponse(buffer.toString());
                    } else {
                        StringBuffer buffer = new StringBuffer(64);
                        buffer.append(ERR);
                        buffer.append(" ");
                        buffer.append("Message (");
                        buffer.append(num);
                        buffer.append(") already deleted.");
                        session.writeResponse(buffer.toString());
                    }
                } catch (IndexOutOfBoundsException npe) {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(ERR);
                    buffer.append(" ");
                    buffer.append("Message (");
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
                } catch (Exception e) {
                    StringBuffer buffer = new StringBuffer(128);
                    buffer.append(ERR);
                    buffer.append(" ");
                    buffer.append(e.getMessage());
                    session.writeResponse(buffer.toString());
                }
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("LIST not allowed here");
            session.writeResponse(buffer.toString());
        }
    }
}
