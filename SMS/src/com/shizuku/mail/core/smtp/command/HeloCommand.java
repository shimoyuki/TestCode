package com.shizuku.mail.core.smtp.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.ServerState;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.SmtpCommands;
import com.shizuku.mail.exception.ArgumentException;

/**
 * Handle HELO command.
 * 
 * <p>
 * The command is used to identify the SMTP client to the SMTP server. The
 * argument field contains the fully-qualified domain name of the SMTP client if
 * one is available. In situations in which the SMTP client system does not have
 * a meaningful domain name (e.g., when its address is dynamically allocated and
 * no reverse mapping record is available), the client SHOULD send an address
 * literal , optionally followed by information that will help to identify the
 * client system. y The SMTP server identifies itself to the SMTP client in the
 * connection greeting reply and in the response to this command.
 * <p>
 * 
 * 
 * 
 * 
 * 
 */
public class HeloCommand implements Command, SmtpCommands, ServerState {

    /**
     * Handler method called upon receipt of a HELO command. Responds with a
     * greeting and informs the client whether client authentication is
     * required.
     * 
     * @see com.shizuku.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session
     *            The Session of Server and client
     */
    public void onCommand(Session session) {
        /**
         * Clear all buffers and reset the state exactly as if a RSET command
         * had been issued.
         */
        session.clear();

        String argument = null;
        try {
            argument = session.getCommandLine().getArgument();
        } catch (ArgumentException ae) {
            argument = null;
        }

        if (argument == null) {
            session.writeResponse("501 Syntax error, Usage: HELO hostname");
        } else {
            session.writeResponse("250 OK");
            session.setLastAction(HELO);
        }

    }
}
