package com.shizuku.mail.core.pop3.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.ServerState;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.Pop3States;

/**
 * Handle other invalid command.
 * Handler method called upon receipt of an unrecognized command.
 * Returns an error response and logs the command.
 */
public class UnknownCommand implements Command, Pop3States, ServerState {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        StringBuffer buffer = new StringBuffer(64);
        buffer.append(ERR);
        buffer.append(" ");
        buffer.append("Unknown Command.");
        session.writeResponse(buffer.toString());

        int state = session.getLastAction();

        if (state == AUTHENTICATION) {
            session.setLastAction(READY);
        }
    }
}
