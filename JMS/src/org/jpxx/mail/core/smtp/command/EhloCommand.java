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
import java.util.List;

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.smtp.SmtpCommands;
import org.jpxx.mail.domain.DomainHandler;
import org.jpxx.mail.exception.ArgumentException;

/**
 * Handle EHLO command.
 * 
 * <p>A client SMTP supporting SMTP service extensions should start an SMTP
 * session by issuing the EHLO command instead of the HELO command. If
 * the SMTP server supports the SMTP service extensions it will give a
 * successful response, a failure response or an error response.
 * If the SMTP server does not support any  SMTP service extensions 
 * it will generate an error response.</p>
 * 
 * <p>Please read rfc1869 for the details.</p>
 * 
 * @see org.jpxx.mail.Smtp.Command.HeloCommand
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/28 22:07:00 $
 * 
 */
public class EhloCommand implements Command, SmtpCommands {

    /**
     * Handler method called upon receipt of a EHLO command.
     * Responds with a greeting and informs the client whether
     * client authentication is required.
     * 
     * @see org.jpxx.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session Server and client Session
     */
    public void onCommand(Session session) {
        /**
         * An EHLO command MAY be issued by a client later in the session. If
         * it is issued after the session begins, the SMTP server MUST clear all
         * buffers and reset the state exactly as if a RSET command had been
         * issued. In other words, the sequence of RSET followed immediately by
         * EHLO is redundant, but not harmful other than in the performance cost
         * of executing unnecessary commands.
         */
        session.clear();

        String argument = null;
        try {
            argument = session.getCommandLine().getArgument();
        } catch (ArgumentException ae) {
            argument = null;
        }

        if (argument == null) {
            session.writeResponse("501 Syntax error, Usage: EHLO hostname");
        } else {
            List<String> extensionList = new ArrayList<String>();
            DomainHandler d = new DomainHandler();

            extensionList.add("250-" + d.getDefaultDomain());
            extensionList.add("250-AUTH LOGIN PLAIN");
            extensionList.add("250-AUTH=LOGIN PLAIN");
            extensionList.add("250 8BITMIME");
           // extensionList.add("250 " + d.getDefaultDomain());

            for (int i = 0; i < extensionList.size(); i++) {
                session.writeResponse(extensionList.get(i).toString());
            }
        }
        session.setLastAction(EHLO);
    }
}
