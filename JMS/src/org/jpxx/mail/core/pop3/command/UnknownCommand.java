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

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.ServerState;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.pop3.Pop3States;

/**
 * Handle other invalid command.
 * Handler method called upon receipt of an unrecognized command.
 * Returns an error response and logs the command.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 19:52:00 $
 * 
 */
public class UnknownCommand implements Command, Pop3States, ServerState {

    /**
     * @see org.jpxx.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        StringBuffer buffer = new StringBuffer(64);
        buffer.append(ERR);
        buffer.append(" ");
        buffer.append("Unknown Command.");
        session.writeResponse(buffer.toString());

        int state = session.getLastAction();

        if (state == AUTHENTICATION) {
            session.setLastAction(READY);
        }
    }
}
