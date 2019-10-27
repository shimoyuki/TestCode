package com.shizuku.mail.core.smtp.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.ServerState;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.SmtpCommands;

/**
 * Handle NOOP command
 * 
 * 
 * 
 * 
 */
public class NoopCommand implements Command, SmtpCommands, ServerState {

    /**
     * The NOOP, HELP, EXPN, VRFY, and RSET commands can be used at any time
     * during a session, or without previously initializing a session.
     * 
     * @see com.shizuku.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        session.writeResponse("250 OK");
    }
}
