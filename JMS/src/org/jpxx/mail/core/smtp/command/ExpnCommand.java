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
 *
 * This command asks the receiver to confirm that the argument
 * identifies a mailing list, and if so, to return the membership of
 * that list. If the command is successful, a reply is returned
 * containing information as described in section 3.5. This reply will
 * have multiple lines except in the trivial case of a one-member list.
 * This command has no effect on the reverse-path buffer, the forwardpath
 * buffer, or the mail data buffer and may be issued at any time.
 * 
 * Syntax:
 * "EXPN" SP String CRLF
 *  
 * @author Jun Li
 * @version $Revision: 0.0.3 $, $Date: 2008/05/23 $
 * @since JMS 0.0.3
 */
public class ExpnCommand implements Command, SmtpCommands {

    /**
     * The EXPN, HELP, NOOP, VRFY, and RSET commands can be used at any time
     * during a session, or without previously initializing a session.
     * 
     * @see org.jpxx.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        /**
         * EXPN is not safe.
         */
        String responseString = "502 EXPN command is disabled";
        session.writeResponse(responseString);
    }
}
