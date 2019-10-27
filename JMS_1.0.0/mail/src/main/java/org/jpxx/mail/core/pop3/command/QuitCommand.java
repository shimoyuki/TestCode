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

import org.jpxx.mail.Constants;
import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.ServerState;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.pop3.MailRepository;
import org.jpxx.mail.core.pop3.Pop3States;

/**
 * Handle UPDATE command. Handler method called upon receipt of a UPDATE command.
 * 
 * The POP3 server removes all messages marked as deleted
 * from the maildrop and replies as to the status of this
 * operation.  If there is an error, such as a resource
 * shortage, encountered while removing messages, the
 * maildrop may result in having some or none of the messages
 * marked as deleted be removed.  In no case may the server
 * remove any messages not marked as deleted.
 * 
 * Whether the removal was successful or not, the server
 * then releases any exclusive-access lock on the maildrop
 * and closes the TCP connection.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 19:49:00 $
 * 
 */
public class QuitCommand implements Command, Pop3States, ServerState {

    /**
     * @see org.jpxx.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        int state = session.getLastAction();
        String responseString = null;
        /**
         * 
         */
        if (state == READY || state == AUTHENTICATION) {
            responseString = OK + " " + Constants.NAME + " signing off.";
            session.writeResponse(responseString);
            // Exit
            session.setLastAction(AUTHENTICATION_QUIT);
            return;
        }

        /**
         * Update
         */
        try {
            MailRepository mr = (MailRepository) session.getOperation().get("MAIL_REPOSITORY");
            if (mr != null) {
                mr.remove();
            }
            responseString = OK + " " + Constants.NAME + " signing off.";
            session.writeResponse(responseString);
        } catch (Exception ex) {
            responseString = ERR + " Some deleted messages were not removed";
            session.writeResponse(responseString);
        } finally {
            session.setLastAction(UPDATE);
        }
    }
}
