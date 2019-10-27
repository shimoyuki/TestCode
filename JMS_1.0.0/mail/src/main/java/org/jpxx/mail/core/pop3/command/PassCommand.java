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

import java.util.List;

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.ServerState;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.pop3.MailRepository;
import org.jpxx.mail.core.pop3.Pop3States;
import org.jpxx.mail.core.pop3.message.Mail;
import org.jpxx.mail.domain.DomainHandler;
import org.jpxx.mail.exception.ArgumentException;
import org.jpxx.mail.exception.InvalidEmailAddressException;
import org.jpxx.mail.user.UserHandler;
import org.jpxx.mail.util.EmailAddress;

/**
 * Handle PASS command
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 18:23:00 $
 * 
 */
public class PassCommand implements Command, Pop3States, ServerState {

    /**
     * @see org.jpxx.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        String user = null;
        int state = session.getLastAction();
        String argument = null;
        try {
            argument = session.getCommandLine().getArgument();
        } catch (ArgumentException ae) {
            argument = null;
        }

        if (state == AUTHENTICATION && argument != null) {
            EmailAddress ea = null;

            try {
                // Read user from last operation
                user = session.getOperation().get("USER").toString();
                // Check is a email address o not
                if (!EmailAddress.isEmail(user)) {
                    ea = new EmailAddress(user + "@" + new DomainHandler().getDefaultDomain());
                } else {
                    ea = new EmailAddress(user);
                }
            } catch (InvalidEmailAddressException e) {
                session.setLastAction(READY);
                session.writeResponse(ERR);
                return;
            }
            // Get password
            String password = argument;

            MailRepository mr = new MailRepository(ea);

            if (mr.isLock()) {
                session.writeResponse(ERR + " Mailbox is locked");
                session.setLastAction(READY);
            } else if (new UserHandler().check(ea, password)) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(OK);
                buffer.append(" ");
                buffer.append("Welcome");
                buffer.append(" ");
                buffer.append(user);
                session.setLastAction(TRANSACTION);
                session.writeResponse(buffer.toString());

                List<Mail> userMailbox = mr.list();
                // Save user's operation to hashmap
                session.addOperation("MAIL_REPOSITORY", mr);
                session.addOperation("USER_MAIL_BOX", userMailbox);
                mr.lockMailRepository();

            } else {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Authentication failed.");
                session.setLastAction(READY);
                session.writeResponse(buffer.toString());
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("PASS not allowed here");
            session.writeResponse(buffer.toString());
        }
    }
}
