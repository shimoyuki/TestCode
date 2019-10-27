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

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.pop3.MailRepository;
import org.jpxx.mail.core.pop3.Pop3States;
import org.jpxx.mail.core.pop3.message.Mail;

/**
 * Handle RSET command.
 * May only be given in the TRANSACTION state.
 * If any messages have been marked as deleted by the POP3
 * server, they are unmarked. The POP3 server then replies
 * with a positive response.
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 19:21:00 $
 * 
 */
public class RsetCommand implements Command, Pop3States {

    /**
     * @see org.jpxx.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The session of client and server
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        int state = session.getLastAction();
        if (state == TRANSACTION) {
            MailRepository mr = (MailRepository) session.getOperation().get("MAIL_REPOSITORY");

            ArrayList userMailbox = (ArrayList) session.getOperation().get("USER_MAIL_BOX");

            ArrayList userMailboxRset = new ArrayList();

            int count = 0;
            int size = 0;
            /**
             * If any messages have been marked as deleted by the POP3
             * server, they are unmarked.
             */
            for (int i = 0; i < userMailbox.size(); i++) {
                Mail mail = (Mail) userMailbox.get(i);
                if (mail.getState()) {
                    mail.setState(false);
                }
                userMailboxRset.add(mail);
                ++count;
                size += mail.getSize();
            }
            mr.setMailList(userMailboxRset);
            // Reset user mail box
            session.addOperation("USER_MAIL_BOX", userMailboxRset);

            StringBuffer buffer = new StringBuffer(64);
            buffer.append(OK);
            buffer.append(" ");
            buffer.append("Maildrop has");
            buffer.append(" ");
            buffer.append(count);
            buffer.append(" ");
            buffer.append("messages (");
            buffer.append(size);
            buffer.append(" ");
            buffer.append("octets)");
            session.writeResponse(buffer.toString());
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("RSET Not allowed Here.");
            session.writeResponse(buffer.toString());
        }
    }
}
