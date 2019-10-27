package com.shizuku.mail.core.pop3.command;

import java.util.ArrayList;
import java.util.Iterator;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.Pop3States;
import com.shizuku.mail.core.pop3.message.Mail;

/**
 * Handle STAT command
 */
public class StatCommand implements Command, Pop3States {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The session of Server and client
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        int state = session.getLastAction();
        if (state == TRANSACTION) {
            ArrayList userMailbox = (ArrayList) session.getOperation().get("USER_MAIL_BOX");
            long size = 0;
            int count = 0;
            try {
                for (Iterator i = userMailbox.iterator(); i.hasNext();) {
                    Mail m = (Mail) i.next();
                    if (!m.getState()) {
                        size += m.getSize();
                        count++;
                    }
                }
                StringBuffer buffer = new StringBuffer(128);
                buffer.append(OK);
                buffer.append(" ");
                buffer.append(count);
                buffer.append(" message(s)");
                buffer.append(" [");
                buffer.append(size);
                buffer.append(" bytes]");
                session.writeResponse(buffer.toString());
            } catch (Exception e) {
                StringBuffer buffer = new StringBuffer(128);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append(e.getMessage());
                session.writeResponse(buffer.toString());
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("STAT Not allowed here");
            session.writeResponse(buffer.toString());
        }
    }
}
