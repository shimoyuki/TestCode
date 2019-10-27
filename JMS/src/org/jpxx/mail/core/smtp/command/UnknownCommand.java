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

/**
 * Handle other invalid command.
 * Handler method called upon receipt of an unrecognized command.
 * Returns an error response and logs the command.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/29 20:08:00 $
 * 
 */
public class UnknownCommand implements Command {

    /**
     * @see org.jpxx.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client 
     */
    public void onCommand(Session session) {
        session.writeResponse("502 Command not implemented");
    }
}
