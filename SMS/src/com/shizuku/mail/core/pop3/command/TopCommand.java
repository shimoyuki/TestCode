package com.shizuku.mail.core.pop3.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.MailRepository;
import com.shizuku.mail.core.pop3.Pop3States;
import com.shizuku.mail.core.pop3.message.Mail;
import com.shizuku.mail.exception.ArgumentException;

/**
 * Handle TOP command.
 * a message-number (required) which may NOT refer to to a
 * message marked as deleted, and a non-negative number
 * of lines (required).<p>
 * 
 * If the POP3 server issues a positive response, then the
 * response given is multi-line. After the initial +OK, the
 * POP3 server sends the headers of the message, the blank
 * line separating the headers from the body, and then the
 * number of lines of the indicated message’s body, being
 * careful to byte-stuff the termination character (as with
 * all multi-line responses).<p>
 * 
 * 
 * 
 * 
 */
public class TopCommand implements Command, Pop3States {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The session of client and server
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        int state = session.getLastAction();

        String argument1 = null;
        String argument2 = null;
        try {
            argument1 = session.getCommandLine().getArgument(0);
            argument2 = session.getCommandLine().getArgument(1);
            
        } catch (ArgumentException ae) {
        }
        if (state == TRANSACTION) {
            int num = 0;
            int lines = 0;
            try {
                num = Integer.parseInt(argument1);
                lines = Integer.parseInt(argument2);
            } catch (NumberFormatException nfe) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Usage: TOP [mail number] [Line number]");
                session.writeResponse(buffer.toString());
                return;
            }

            try {
                ArrayList userMailbox = (ArrayList) session.getOperation().get("USER_MAIL_BOX");
                Mail m = (Mail) userMailbox.get(num - 1);
                MailRepository mr = (MailRepository) session.getOperation().get("MAIL_REPOSITORY");
                if (!m.getState()) {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(OK);
                    buffer.append(" ");
                    buffer.append("Message follows");
                    session.writeResponse(buffer.toString());
                    try {
                        mr.write(
                            new PrintWriter(session.getClinetSocket().getOutputStream(), true), m,
                            lines);
                    } finally {
                        session.writeResponse(".");
                    }
                } else {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(ERR);
                    buffer.append(" ");
                    buffer.append("Message (");
                    buffer.append(num);
                    buffer.append(") already deleted.");
                    session.writeResponse(buffer.toString());
                }
            } catch (IOException ioe) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Error while retrieving message.");
                session.writeResponse(buffer.toString());
            } catch (IndexOutOfBoundsException iob) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Message (");
                buffer.append(num);
                buffer.append(") does not exist.");
                session.writeResponse(buffer.toString());
            } catch (Exception e) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Error while retrieving message.");
                session.writeResponse(buffer.toString());
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("TOP Not allowed Here.");
            session.writeResponse(buffer.toString());
        }
    }
}
