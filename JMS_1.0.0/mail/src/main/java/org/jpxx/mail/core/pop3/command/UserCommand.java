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
import org.jpxx.mail.exception.ArgumentException;

/**
 * Handle USER command
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 18:22:00 $
 * 
 */
public class UserCommand implements Command, Pop3States, ServerState {

    /**
     * @see org.jpxx.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        int state = session.getLastAction();
        String argument = null;
        try {
            argument = session.getCommandLine().getArgument();
        } catch (ArgumentException ae) {
            argument = null;
        }

        if (state == READY && argument != null) {
            session.addOperation("USER", argument);
            session.setLastAction(AUTHENTICATION);
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(OK);
            buffer.append(" ");
            buffer.append("now for password");
            session.writeResponse(buffer.toString());
        } else if (argument == null) {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("Parameter error.");
            session.writeResponse(buffer.toString());
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("Server not ready");
            session.writeResponse(buffer.toString());
        }
    }
}
