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
 * If the POP3 server issues a positive response, then the
 * response given is multi-line. After the initial +OK, the
 * POP3 server sends the message corresponding to the given
 * message-number, being careful to byte-stuff the termination
 * character (as with all multi-line responses).
 */
public class RetrCommand implements Command, Pop3States {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session  The session of client and server
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        String responseString = null;
        int state = session.getLastAction();
        if (state == TRANSACTION) {
            String argument = null;
            try {
                argument = session.getCommandLine().getArgument();
            } catch (ArgumentException ae) {
                argument = null;
            }

            int num = 0;
            try {
                num = Integer.parseInt(argument.trim());
            } catch (Exception e) {
                responseString = ERR + " Usage: RETR [mail number]";
                session.writeResponse(responseString);
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
                    /**
                     * Write mail content to client.
                     */
                    try {
                        mr.write(
                            new PrintWriter(session.getClinetSocket().getOutputStream(), true), m);
                    } finally {
                        session.writeResponse(".");
                    }
                } else {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(ERR);
                    buffer.append(" Message (");
                    buffer.append(num);
                    buffer.append(") already deleted.");
                    session.writeResponse(buffer.toString());
                }
            } catch (IndexOutOfBoundsException iob) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Message (");
                buffer.append(num);
                buffer.append(") does not exist.");
                session.writeResponse(buffer.toString());
            } catch (IOException ioe) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Error while retrieving message.");
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
            buffer.append("RETR Not allowed here.");
            session.writeResponse(buffer.toString());
        }
    }
}
