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
 * This command causes the receiver to send helpful information to the sender of
 * the HELP command. The command may take an argument (e.g., any command name)
 * and return more specific information as a response.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.3 $, $Date: 2008/05/23 $
 * @since JMS 0.0.3
 */
public class HelpCommand implements Command, SmtpCommands {

    /**
     * The HELP, NOOP, EXPN, VRFY, and RSET commands can be used at any time
     * during a session, or without previously initializing a session.
     * 
     * @see org.jpxx.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session
     *            The Session of Server and client
     */
    public void onCommand(Session session) {
        String responseString = "220 For help please visit http://www.jpxx.org";
        session.writeResponse(responseString);
    }
}
