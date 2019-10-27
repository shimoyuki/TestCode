package com.shizuku.mail.core.smtp.command;

import java.util.HashMap;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.CommandsHandler;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.SmtpCommands;

/**
 * CommandHandler Handler. 
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
     * 
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
     * 
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
