package com.shizuku.mail.core.pop3.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.ServerState;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.Pop3States;
import com.shizuku.mail.exception.ArgumentException;

/**
 * Handle USER command

 */
public class UserCommand implements Command, Pop3States, ServerState {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        int state = session.getLastAction();
        String argument = null;
        try {
            argument = session.getCommandLine().getArgument();
        } catch (ArgumentException ae) {
            argument = null;
        }

        if (state == READY && argument != null) {
            session.addOperation("USER", argument);
            session.setLastAction(AUTHENTICATION);
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(OK);
            buffer.append(" ");
            buffer.append("now for password");
            session.writeResponse(buffer.toString());
        } else if (argument == null) {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("Parameter error.");
            session.writeResponse(buffer.toString());
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("Server not ready");
            session.writeResponse(buffer.toString());
        }
    }
}
