package com.shizuku.mail.core.pop3.command;

import java.util.ArrayList;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.MailRepository;
import com.shizuku.mail.core.pop3.Pop3States;
import com.shizuku.mail.core.pop3.message.Mail;

/**
 * Handle RSET command.
 * May only be given in the TRANSACTION state.
 * If any messages have been marked as deleted by the POP3
 * server, they are unmarked. The POP3 server then replies
 * with a positive response.
 * 
 * 
 * 
 */
public class RsetCommand implements Command, Pop3States {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The session of client and server
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        int state = session.getLastAction();
        if (state == TRANSACTION) {
            MailRepository mr = (MailRepository) session.getOperation().get("MAIL_REPOSITORY");

            ArrayList userMailbox = (ArrayList) session.getOperation().get("USER_MAIL_BOX");

            ArrayList userMailboxRset = new ArrayList();

            int count = 0;
            int size = 0;
            /**
             * If any messages have been marked as deleted by the POP3
             * server, they are unmarked.
             */
            for (int i = 0; i < userMailbox.size(); i++) {
                Mail mail = (Mail) userMailbox.get(i);
                if (mail.getState()) {
                    mail.setState(false);
                }
                userMailboxRset.add(mail);
                ++count;
                size += mail.getSize();
            }
            mr.setMailList(userMailboxRset);
            // Reset user mail box
            session.addOperation("USER_MAIL_BOX", userMailboxRset);

            StringBuffer buffer = new StringBuffer(64);
            buffer.append(OK);
            buffer.append(" ");
            buffer.append("Maildrop has");
            buffer.append(" ");
            buffer.append(count);
            buffer.append(" ");
            buffer.append("messages (");
            buffer.append(size);
            buffer.append(" ");
            buffer.append("octets)");
            session.writeResponse(buffer.toString());
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("RSET Not allowed Here.");
            session.writeResponse(buffer.toString());
        }
    }
}
