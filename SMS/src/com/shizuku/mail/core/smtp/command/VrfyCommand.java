package com.shizuku.mail.core.smtp.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.SmtpCommands;

/**
 * <p>
 * This command asks the receiver to confirm that the argument identifies a user
 * or mailbox. If it is a user name, information is returned. This command has
 * no effect on the reverse-path buffer, the forwardpath buffer, or the mail
 * data buffer.
 * </p>
 * 
 * <pre>
 * Syntax:
 * &quot;VRFY&quot; SP String CRLF
 * </pre>
 * 
 *  
 * 
 *
 * 
 */
public class VrfyCommand implements Command, SmtpCommands {

    /**
     * The VRFY, EXPN, HELP, NOOP and RSET commands can be used at any time
     * during a session, or without previously initializing a session.
     * 
     * @see com.shizuku.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session
     *            The Session of Server and client
     */
    public void onCommand(Session session) {
        /**
         * VRFY is not safe.
         */
        String responseString = "502 VRFY command is disabled";
        session.writeResponse(responseString);
    }
}
