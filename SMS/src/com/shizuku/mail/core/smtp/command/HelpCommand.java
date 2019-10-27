package com.shizuku.mail.core.smtp.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.SmtpCommands;

/**
 * This command causes the receiver to send helpful information to the sender of
 * the HELP command. The command may take an argument (e.g., any command name)
 * and return more specific information as a response.
 * 
 * 
 * 
 * 
 */
public class HelpCommand implements Command, SmtpCommands {

    /**
     * The HELP, NOOP, EXPN, VRFY, and RSET commands can be used at any time
     * during a session, or without previously initializing a session.
     * 
     * @see com.shizuku.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session
     *            The Session of Server and client
     */
    public void onCommand(Session session) {
        String responseString = "220 For help please visit http://www.jpxx.org";
        session.writeResponse(responseString);
    }
}
