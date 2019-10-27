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

import java.util.HashMap;

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.CommandsHandler;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.pop3.Pop3States;

/**
 * Pop3 CommandHandler Handler.
 * This Class parses POP3 commands.
 * Actual processing of the command (possibly including additional back and
 * forth communication with the client) is delegated to one of a number of
 * command specific handler methods.  The primary purpose of this class is
 * to parse the raw command string to determine exactly which handler should
 * be called. 
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 19:55:00 $
 * 
 */
public class Pop3CommandHandler implements CommandsHandler, Pop3States {

    /**
     * Single instance
     */
    private static Pop3CommandHandler singletonInstance = null;
    /**
     * Store the operation Class
     */
    private HashMap<String, String>   map               = null;

    private Pop3CommandHandler() {
        map = new HashMap<String, String>();
        map.put("APOP", ApopCommand.class.getName());
        map.put("DELE", DeleCommand.class.getName());
        map.put("LIST", ListCommand.class.getName());
        map.put("NOOP", NoopCommand.class.getName());
        map.put("PASS", PassCommand.class.getName());
        map.put("QUIT", QuitCommand.class.getName());
        map.put("RETR", RetrCommand.class.getName());
        map.put("RSET", RsetCommand.class.getName());
        map.put("STAT", StatCommand.class.getName());
        map.put("TOP", TopCommand.class.getName());
        map.put("UIDL", UidlCommand.class.getName());
        map.put("USER", UserCommand.class.getName());
    }

    /**
     * 
     * @return single instance of SmtpCommandHandler
     */
    public static Pop3CommandHandler getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new Pop3CommandHandler();
        }
        return singletonInstance;
    }

    /**
     * Do with Pop3 CommandHandler
     * 
     * @param name CommandHandler name
     * @param session Server and Client Session
     */
    public void doCommand(String name, Session session) {
        if (name == null) {
            session.writeResponse(ERR);
            // Must quit
            session.setLastAction(UPDATE);
            return;
        }
        // Get the operation class name, include package name
        Object className = map.get(name);
        if (className == null) {
            className = UnknownCommand.class.getName();
        }

        try {
            // Look for the class
            Command command = (Command) Class.forName(className.toString()).newInstance();
            // Do this command
            command.onCommand(session);
        } catch (Exception e) {
            session.writeResponse(ERR);
        }
    }
}
