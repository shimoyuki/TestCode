package com.shizuku.mail.core.pop3.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.Pop3States;

/**
 * Handle NOOP command
 */
public class NoopCommand implements Command, Pop3States {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of server and client
     */
    public void onCommand(Session session) {
        int state = session.getLastAction();
        if (state == TRANSACTION) {
            session.writeResponse(OK);
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("Server not in TRANSACTION state");
            session.writeResponse(ERR);
        }
    }
}
