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

import java.util.HashMap;

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.CommandsHandler;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.smtp.SmtpCommands;

/**
 * CommandHandler Handler.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.3 $, $Date: 2008/05/20 $
 * @since JMS 0.0.1
 * 
 */
public class SmtpCommandsHandler implements CommandsHandler, SmtpCommands {

    private static SmtpCommandsHandler     singletonInstance = null;
    private static HashMap<String, String> map               = null;

    private SmtpCommandsHandler() {
        map = new HashMap<String, String>();
        map.put("HELO", HeloCommand.class.getName());
        map.put("EHLO", EhloCommand.class.getName());
        map.put("AUTH", AuthCommand.class.getName());
        map.put("MAIL", MailCommand.class.getName());
        map.put("RCPT", RcptCommand.class.getName());
        map.put("DATA", DataCommand.class.getName());
        map.put("NOOP", NoopCommand.class.getName());
        map.put("RSET", RsetCommand.class.getName());
        map.put("QUIT", QuitCommand.class.getName());
        /**
         * Not necessary
         */
        map.put("VRFY", VrfyCommand.class.getName());
        map.put("EXPN", ExpnCommand.class.getName());
        map.put("HELP", HelpCommand.class.getName());
    }

    /**
     * 
     * @since JMS 0.0.1
     * @return single instance of SmtpCommandsHandler
     */
    public static SmtpCommandsHandler getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new SmtpCommandsHandler();
        }
        return singletonInstance;
    }

    /**
     * Do with Smtp CommandHandler
     * 
     * @since JMS 0.0.1
     * @param name
     * @param session
     */
    public void doCommand(String name, Session session) {
        if (name == null) {
            session.setLastAction(QUIT);
            session.writeResponse("421 Closing transmission channel");
            return;
        }

        // Get the operation class name, include package name
        Object className = map.get(name);

        try {
            Command command = (Command) Class.forName(className.toString()).newInstance();
            command.onCommand(session);
        } catch (Exception e) {
            Command command = new UnknownCommand();
            command.onCommand(session);
        }
    }
}
