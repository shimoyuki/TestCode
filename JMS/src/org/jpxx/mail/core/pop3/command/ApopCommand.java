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
 * Handle APOP command.
 * Normally, each POP3 session starts with a USER/PASS
 * exchange. This results in a server/user-id specific
 * password being sent in the clear on the network. For
 * intermittent use of POP3, this may not introduce a sizable
 * risk. However, many POP3 client implementations connect to
 * the POP3 server on a regular basis -- to check for new
 * mail. Further the interval of session initiation may be on
 * the order of five minutes. Hence, the risk of password
 * capture is greatly enhanced.
 * <br><br>
 * About more details please read rfc1939[Page 15]
 * <br>
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/29 8:15:00 $
 * 
 */
public class ApopCommand implements Command, Pop3States, ServerState {

    /**
     * @see org.jpxx.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The session of client and server
     */
    public void onCommand(Session session) {
        int state = session.getLastAction();
        if (state == READY) {
            String user = null;
            String pass = null;

            try {
                user = session.getCommandLine().getArgument(0);
                pass = session.getCommandLine().getArgument(1);
            } catch (ArgumentException ae) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Usage: APOP [name] [digest]");
                session.writeResponse(buffer.toString());
                return;
            }

            EmailAddress ea = null;
            try {
                // Check is a email address o not
                if (!EmailAddress.isEmail(user)) {
                    ea = new EmailAddress(user + "@" + new DomainHandler().getDefaultDomain());
                } else {
                    ea = new EmailAddress(user);
                }
            } catch (InvalidEmailAddressException e) {
                session.writeResponse(ERR);
                return;
            }

            String shareID = session.getOperation().get("SHARE_ID").toString();
            MailRepository mr = new MailRepository(ea);
            if (mr.isLock()) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Mailbox is locked");
                session.writeResponse(buffer.toString());
            } else if (new UserHandler().check(ea, pass, shareID)) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(OK);
                buffer.append(" Welcome ");
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
                buffer.append("Authentication failed");
                session.writeResponse(buffer.toString());
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("APOP not allowed here");
            session.writeResponse(buffer.toString());
        }
    }
}
