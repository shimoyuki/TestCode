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

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.ServerState;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.smtp.SmtpCommands;
import org.jpxx.mail.exception.ArgumentException;

/**
 * Handle HELO command.
 * 
 * <p>
 * The command is used to identify the SMTP client to the SMTP server. The
 * argument field contains the fully-qualified domain name of the SMTP client if
 * one is available. In situations in which the SMTP client system does not have
 * a meaningful domain name (e.g., when its address is dynamically allocated and
 * no reverse mapping record is available), the client SHOULD send an address
 * literal , optionally followed by information that will help to identify the
 * client system. y The SMTP server identifies itself to the SMTP client in the
 * connection greeting reply and in the response to this command.
 * <p>
 * 
 * @author Jun Li
 * @version $Revision: 0.0.3 $, $Date: 2008/04/24 $
 * @since JMS 0.0.1
 * 
 */
public class HeloCommand implements Command, SmtpCommands, ServerState {

    /**
     * Handler method called upon receipt of a HELO command. Responds with a
     * greeting and informs the client whether client authentication is
     * required.
     * 
     * @see org.jpxx.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session
     *            The Session of Server and client
     */
    public void onCommand(Session session) {
        /**
         * Clear all buffers and reset the state exactly as if a RSET command
         * had been issued.
         */
        session.clear();

        String argument = null;
        try {
            argument = session.getCommandLine().getArgument();
        } catch (ArgumentException ae) {
            argument = null;
        }

        if (argument == null) {
            session.writeResponse("501 Syntax error, Usage: HELO hostname");
        } else {
            session.writeResponse("250 OK");
            session.setLastAction(HELO);
        }

    }
}
