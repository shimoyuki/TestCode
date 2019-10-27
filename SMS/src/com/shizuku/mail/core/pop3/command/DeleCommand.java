package com.shizuku.mail.core.pop3.command;

import java.util.ArrayList;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.MailRepository;
import com.shizuku.mail.core.pop3.Pop3States;
import com.shizuku.mail.core.pop3.message.Mail;
import com.shizuku.mail.exception.ArgumentException;

/**
 * Handle DELE command
 * 
 * May only be given in the TRANSACTION state.
 */
public class DeleCommand implements Command, Pop3States {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of server and client
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        int state = session.getLastAction();
        if (state == TRANSACTION) {
            String argument = null;
            try {
                argument = session.getCommandLine().getArgument();
            } catch (ArgumentException a) {
                argument = null;
            }

            int num = 1;
            try {
                num = Integer.parseInt(argument);
            } catch (Exception e) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Usage: DELE [mail number]");
                session.writeResponse(buffer.toString());
                return;
            }
            try {
                ArrayList userMailbox = (ArrayList) session.getOperation().get("USER_MAIL_BOX");
                Mail m = (Mail) userMailbox.get(num - 1);
                MailRepository mr = (MailRepository) session.getOperation().get("MAIL_REPOSITORY");
                if (m.getState()) {
                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(ERR);
                    buffer.append(" Message (");
                    buffer.append(num);
                    buffer.append(") already deleted.");
                    session.writeResponse(buffer.toString());
                } else {
                    mr.setDelete(num, true);
                    // Update The operation
                    session.addOperation("USER_MAIL_BOX", mr.list());
                    session.addOperation("MAIL_REPOSITORY", mr);

                    StringBuffer buffer = new StringBuffer(64);
                    buffer.append(OK);
                    buffer.append(" ");
                    buffer.append("Message deleted");
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
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("DELE not allowed here");
            session.writeResponse(buffer.toString());
        }
    }
}
