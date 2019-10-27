package com.shizuku.mail.core.smtp.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.SmtpCommands;

/**
 *
 * This command asks the receiver to confirm that the argument
 * identifies a mailing list, and if so, to return the membership of
 * that list. If the command is successful, a reply is returned
 * containing information as described in section 3.5. This reply will
 * have multiple lines except in the trivial case of a one-member list.
 * This command has no effect on the reverse-path buffer, the forwardpath
 * buffer, or the mail data buffer and may be issued at any time.
 * 
 * Syntax:
 * "EXPN" SP String CRLF
 *  
 * 
 * 
 * 
 */
public class ExpnCommand implements Command, SmtpCommands {

    /**
     * The EXPN, HELP, NOOP, VRFY, and RSET commands can be used at any time
     * during a session, or without previously initializing a session.
     * 
     * @see com.shizuku.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        /**
         * EXPN is not safe.
         */
        String responseString = "502 EXPN command is disabled";
        session.writeResponse(responseString);
    }
}
