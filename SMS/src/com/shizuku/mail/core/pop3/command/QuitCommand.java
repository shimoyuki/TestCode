package com.shizuku.mail.core.pop3.command;

import com.shizuku.mail.Constants;
import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.ServerState;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.MailRepository;
import com.shizuku.mail.core.pop3.Pop3States;

/**
 * Handle UPDATE command. Handler method called upon receipt of a UPDATE command.
 * 
 * The POP3 server removes all messages marked as deleted
 * from the maildrop and replies as to the status of this
 * operation.  If there is an error, such as a resource
 * shortage, encountered while removing messages, the
 * maildrop may result in having some or none of the messages
 * marked as deleted be removed.  In no case may the server
 * remove any messages not marked as deleted.
 * 
 * Whether the removal was successful or not, the server
 * then releases any exclusive-access lock on the maildrop
 * and closes the TCP connection.
 * 
 * 
 * 
 * 
 */
public class QuitCommand implements Command, Pop3States, ServerState {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        int state = session.getLastAction();
        String responseString = null;
        /**
         * 
         */
        if (state == READY || state == AUTHENTICATION) {
            responseString = OK + " " + Constants.NAME + " signing off.";
            session.writeResponse(responseString);
            // Exit
            session.setLastAction(AUTHENTICATION_QUIT);
            return;
        }

        /**
         * Update
         */
        try {
            MailRepository mr = (MailRepository) session.getOperation().get("MAIL_REPOSITORY");
            if (mr != null) {
                mr.remove();
            }
            responseString = OK + " " + Constants.NAME + " signing off.";
            session.writeResponse(responseString);
        } catch (Exception ex) {
            responseString = ERR + " Some deleted messages were not removed";
            session.writeResponse(responseString);
        } finally {
            session.setLastAction(UPDATE);
        }
    }
}
