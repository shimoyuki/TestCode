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
package org.jpxx.mail.core.smtp.command;

import java.util.ArrayList;
import java.util.HashMap;

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.smtp.SmtpCommands;
import org.jpxx.mail.domain.DomainHandler;
import org.jpxx.mail.exception.ArgumentException;
import org.jpxx.mail.exception.InvalidEmailAddressException;
import org.jpxx.mail.user.UserHandler;
import org.jpxx.mail.util.EmailAddress;

/**
 * Handle RCPT Command.
 * 
 * <p>This command is used to identify an individual recipient of the mail 
 * data; multiple recipients are specified by multiple use of this 
 * command. The argument field contains a forward-path and may contain 
 * optional parameters. </p>
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/24 22:07:00 $
 * 
 */
public class RcptCommand implements Command, SmtpCommands {

    /**
     * @see org.jpxx.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        if (session.getLastAction() == MAIL || session.getLastAction() == RCPT) {
            String argument = null;
            try {
                argument = session.getCommandLine().getArgument();
            } catch (ArgumentException ae) {
                argument = null;
            }

            if (argument == null || argument.equals("")) {
                session.writeResponse("500 Syntax error");
            } else if (!argument.toUpperCase().startsWith("TO:")) {
                session.writeResponse("501 Syntax error in arguments");
            } else {
                String to = EmailAddress.grabMailAddress(argument);
                try {
                    EmailAddress ea = new EmailAddress(to);
                    DomainHandler dc = new DomainHandler();
                    /**
                     * Local user
                     */
                    if (dc.isExist(ea.getDomain())) {
                        UserHandler uh = new UserHandler();
                        if (!uh.checkUser(ea)) {
                            session.writeResponse("550 No such user");
                        } else {
                            session.setLastAction(RCPT);
                            session.writeResponse("250 OK");
                        }
                    } else {
                        /**
                         * User not in local server
                         */
                        session.setLastAction(RCPT);
                        session.writeResponse("250 OK");
                    }

                    HashMap map = session.getOperation();
                    ArrayList al = null;
                    if (map.get("RCPT_TO") == null) {
                        al = new ArrayList();
                    } else {
                        al = (ArrayList) map.get("RCPT_TO");
                    }
                    al.add(to);
                    session.addOperation("RCPT_TO", al);
                } catch (InvalidEmailAddressException e) {
                    session.writeResponse("553 Mailbox syntax incorrect");
                }
            }
        } else {
            session.writeResponse("503 RCPT Not allowed here.");
        }

    }
}
