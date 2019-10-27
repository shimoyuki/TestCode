package com.shizuku.mail.core.smtp.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.MailHandler;
import com.shizuku.mail.core.smtp.SmtpCommands;

/**
 * Handle RSET command.<p>
 * 
 * This command specifies that the current mail transaction will be 
 * aborted. Any stored sender, recipients, and mail data MUST be 
 * discarded, and all buffers and state tables cleared. The receiver 
 * MUST send a "250 OK" reply to a RSET command with no arguments. A 
 * reset command may be issued by the client at any time. It is 
 * effectively equivalent to a NOOP (i.e., if has no effect) if issued 
 * immediately after EHLO, before EHLO is issued in the session, after 
 * an end-of-data indicator has been sent and acknowledged, or 
 * immediately before a QUIT. An SMTP server MUST NOT close the 
 * connection as the result of receiving a RSET; that action is reserved 
 * for QUIT.<p>
 * 
 * 
 * 
 * 
 */
public class RsetCommand implements Command, SmtpCommands {

    /**
     * The RSET, NOOP, EXPN, VRFY, and HELP commands can be used at any time
     * during a session, or without previously initializing a session.
     * 
     * @see com.shizuku.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        Object o = session.getOperation().get("MAIL_HANDLER");
        if (o != null) {
            MailHandler mh = (MailHandler) o;
            mh.abortMessage();
        }
        session.setLastAction(HELO);
        session.clear();
        session.writeResponse("250 OK");
    }
}
