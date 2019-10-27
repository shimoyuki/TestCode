package com.shizuku.mail.core.pop3.command;

import java.util.HashMap;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.CommandsHandler;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.Pop3States;

/**
 * Pop3 CommandHandler Handler.
 * This Class parses POP3 commands.
 * Actual processing of the command (possibly including additional back and
 * forth communication with the client) is delegated to one of a number of
 * command specific handler methods.  The primary purpose of this class is
 * to parse the raw command string to determine exactly which handler should
 * be called. 
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
