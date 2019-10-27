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
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.smtp.SmtpCommands;

/**
 * <p>
 * This command asks the receiver to confirm that the argument identifies a user
 * or mailbox. If it is a user name, information is returned. This command has
 * no effect on the reverse-path buffer, the forwardpath buffer, or the mail
 * data buffer.
 * </p>
 * 
 * <pre>
 * Syntax:
 * &quot;VRFY&quot; SP String CRLF
 * </pre>
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.mail.core.smtp.command.VrfyCommand.java $, $Date:
 *          2009-4-29 $
 * @since 0.0.3
 */
public class VrfyCommand implements Command, SmtpCommands {

    /**
     * The VRFY, EXPN, HELP, NOOP and RSET commands can be used at any time
     * during a session, or without previously initializing a session.
     * 
     * @see org.jpxx.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session
     *            The Session of Server and client
     */
    public void onCommand(Session session) {
        /**
         * VRFY is not safe.
         */
        String responseString = "502 VRFY command is disabled";
        session.writeResponse(responseString);
    }
}
