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
import org.jpxx.mail.core.smtp.MailHandler;
import org.jpxx.mail.core.smtp.SmtpCommands;

/**
 * Handle RSET command.<p>
 * 
 * This command specifies that the current mail transaction will be 
 * aborted. Any stored sender, recipients, and mail data MUST be 
 * discarded, and all buffers and state tables cleared. The receiver 
 * MUST send a "250 OK" reply to a RSET command with no arguments. A 
 * reset command may be issued by the client at any time. It is 
 * effectively equivalent to a NOOP (i.e., if has no effect) if issued 
 * immediately after EHLO, before EHLO is issued in the session, after 
 * an end-of-data indicator has been sent and acknowledged, or 
 * immediately before a QUIT. An SMTP server MUST NOT close the 
 * connection as the result of receiving a RSET; that action is reserved 
 * for QUIT.<p>
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/24 23:22:00 $
 * 
 */
public class RsetCommand implements Command, SmtpCommands {

    /**
     * The RSET, NOOP, EXPN, VRFY, and HELP commands can be used at any time
     * during a session, or without previously initializing a session.
     * 
     * @see org.jpxx.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        Object o = session.getOperation().get("MAIL_HANDLER");
        if (o != null) {
            MailHandler mh = (MailHandler) o;
            mh.abortMessage();
        }
        session.setLastAction(HELO);
        session.clear();
        session.writeResponse("250 OK");
    }
}
