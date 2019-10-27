package com.shizuku.mail.core.smtp.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.SmtpCommands;

/**
 * Handle QUIT command
 * <p>An SMTP connection is terminated when the client sends a QUIT
 * command. The server responds with a positive reply code, after which
 * it closes the connection.</p>
 */
public class QuitCommand implements Command, SmtpCommands {

    /**
     * @see com.shizuku.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        session.setLastAction(QUIT);
        session.writeResponse("221 Good Bye");
        session.close();
    }
}
