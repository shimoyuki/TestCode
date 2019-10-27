package com.shizuku.mail.core.pop3.command;

import java.util.List;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.ServerState;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.MailRepository;
import com.shizuku.mail.core.pop3.Pop3States;
import com.shizuku.mail.core.pop3.message.Mail;
import com.shizuku.mail.domain.DomainHandler;
import com.shizuku.mail.exception.ArgumentException;
import com.shizuku.mail.exception.InvalidEmailAddressException;
import com.shizuku.mail.user.UserHandler;
import com.shizuku.mail.util.EmailAddress;

/**
 * Handle PASS command
 */
public class PassCommand implements Command, Pop3States, ServerState {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    public void onCommand(Session session) {
        String user = null;
        int state = session.getLastAction();
        String argument = null;
        try {
            argument = session.getCommandLine().getArgument();
        } catch (ArgumentException ae) {
            argument = null;
        }

        if (state == AUTHENTICATION && argument != null) {
            EmailAddress ea = null;

            try {
                // Read user from last operation
                user = session.getOperation().get("USER").toString();
                // Check is a email address o not
                if (!EmailAddress.isEmail(user)) {
                    ea = new EmailAddress(user + "@" + new DomainHandler().getDefaultDomain());
                } else {
                    ea = new EmailAddress(user);
                }
            } catch (InvalidEmailAddressException e) {
                session.setLastAction(READY);
                session.writeResponse(ERR);
                return;
            }
            // Get password
            String password = argument;

            MailRepository mr = new MailRepository(ea);

            if (mr.isLock()) {
                session.writeResponse(ERR + " Mailbox is locked");
                session.setLastAction(READY);
            } else if (new UserHandler().check(ea, password)) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(OK);
                buffer.append(" ");
                buffer.append("Welcome");
                buffer.append(" ");
                buffer.append(user);
                session.setLastAction(TRANSACTION);
                session.writeResponse(buffer.toString());

                List<Mail> userMailbox = mr.list();
                // Save user's operation to hashmap
                session.addOperation("MAIL_REPOSITORY", mr);
                session.addOperation("USER_MAIL_BOX", userMailbox);
                mr.lockMailRepository();

            } else {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Authentication failed.");
                session.setLastAction(READY);
                session.writeResponse(buffer.toString());
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("PASS not allowed here");
            session.writeResponse(buffer.toString());
        }
    }
}
