package com.shizuku.mail.core.smtp.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;

/**
 * Handle other invalid command.
 * Handler method called upon receipt of an unrecognized command.
 * Returns an error response and logs the command.
 */
public class UnknownCommand implements Command {

    /**
     * @see com.shizuku.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client 
     */
    public void onCommand(Session session) {
        session.writeResponse("502 Command not implemented");
    }
}
