package com.shizuku.mail.core.pop3.command;

import java.util.ArrayList;
import java.util.Iterator;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.Pop3States;
import com.shizuku.mail.core.pop3.message.Mail;
import com.shizuku.mail.exception.ArgumentException;

/**
 * Handle UIDL command. Handler method called upon receipt of a UIDL command.
 * 
 * If an argument was given and the POP3 server issues a positive
 * response with a line containing information for that message.
 * This line is called a "unique-id listing" for that message.
 * 
 * If no argument was given and the POP3 server issues a positive
 * response, then the response given is multi-line.  After the
 * initial +OK, for each message in the maildrop, the POP3 server
 * responds with a line containing information for that message.
 * This line is called a "unique-id listing" for that message.
 * 
 * In order to simplify parsing, all POP3 servers are required to
 * use a certain format for unique-id listings.  A unique-id
 * listing consists of the message-number of the message,
 * followed by a single space and the unique-id of the message.
 * No information follows the unique-id in the unique-id listing.
 * 
 * 
 * 
 * 
 */
public class UidlCommand implements Command, Pop3States {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        int state = session.getLastAction();
        ArrayList userMailbox = new ArrayList();
        if (state == TRANSACTION) {
            String argument = null;
            try {
                argument = session.getCommandLine().getArgument();
            } catch (ArgumentException ae) {
                argument = null;
            }

            if (argument == null) {
                userMailbox = (ArrayList) session.getOperation().get("USER_MAIL_BOX");
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(OK).append(" ");
                buffer.append("unique-id listing follows");
                session.writeResponse(buffer.toString());

                int count = 1;
                for (Iterator i = userMailbox.iterator(); i.hasNext(); count++) {
                    Mail m = (Mail) i.next();
                    if (!m.getState()) {
                        buffer = new StringBuffer(64);
                        buffer.append(count);
                        buffer.append(" ");
                        buffer.append(m.getName());
                        session.writeResponse(buffer.toString());
                    }
                }
                session.writeResponse(".");
            } else {
                int num = 0;
                try {
                    num = Integer.parseInt(argument);
                    Mail m = (Mail) userMailbox.get(num - 1);
                    if (m.getState()) {
                        StringBuffer buffer = new StringBuffer(64);
                        buffer.append(OK);
                        buffer.append(" ");
                        buffer.append(num);
                        buffer.append(" ");
                        buffer.append(m.getName());
                        session.writeResponse(buffer.toString());
                    } else {
                        StringBuffer buffer = new StringBuffer(64);
                        buffer.append(ERR);
                        buffer.append(" Message (");
                        buffer.append(num);
                        buffer.append(") already deleted.");
                        session.writeResponse(buffer.toString());
                    }
                } catch (IndexOutOfBoundsException npe) {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(ERR);
                    buffer.append(" Message (");
                    buffer.append(num);
                    buffer.append(") does not exist.");
                    session.writeResponse(buffer.toString());
                } catch (NumberFormatException nfe) {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(ERR);
                    buffer.append(" ");
                    buffer.append(argument);
                    buffer.append(" is not a valid number");
                    session.writeResponse(buffer.toString());
                }
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("UIDL not allowed here");
            session.writeResponse(buffer.toString());
        }
    }
}
