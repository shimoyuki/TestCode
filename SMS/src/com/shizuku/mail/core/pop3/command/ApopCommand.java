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
 * Handle APOP command.
 * Normally, each POP3 session starts with a USER/PASS
 * exchange. This results in a server/user-id specific
 * password being sent in the clear on the network. For
 * intermittent use of POP3, this may not introduce a sizable
 * risk. However, many POP3 client implementations connect to
 * the POP3 server on a regular basis -- to check for new
 * mail. Further the interval of session initiation may be on
 * the order of five minutes. Hence, the risk of password
 * capture is greatly enhanced.
 * <br><br>
 * About more details please read rfc1939[Page 15]
 * <br>
 * 
 * 
 * 
 */
public class ApopCommand implements Command, Pop3States, ServerState {

    /**
     * @see com.shizuku.mail.Service.CommandHandler#onCommand(Session session)
     * @param session The session of client and server
     */
    public void onCommand(Session session) {
        int state = session.getLastAction();
        if (state == READY) {
            String user = null;
            String pass = null;

            try {
                user = session.getCommandLine().getArgument(0);
                pass = session.getCommandLine().getArgument(1);
            } catch (ArgumentException ae) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Usage: APOP [name] [digest]");
                session.writeResponse(buffer.toString());
                return;
            }

            EmailAddress ea = null;
            try {
                // Check is a email address o not
                if (!EmailAddress.isEmail(user)) {
                    ea = new EmailAddress(user + "@" + new DomainHandler().getDefaultDomain());
                } else {
                    ea = new EmailAddress(user);
                }
            } catch (InvalidEmailAddressException e) {
                session.writeResponse(ERR);
                return;
            }

            String shareID = session.getOperation().get("SHARE_ID").toString();
            MailRepository mr = new MailRepository(ea);
            if (mr.isLock()) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(ERR);
                buffer.append(" ");
                buffer.append("Mailbox is locked");
                session.writeResponse(buffer.toString());
            } else if (new UserHandler().check(ea, pass, shareID)) {
                StringBuffer buffer = new StringBuffer(64);
                buffer.append(OK);
                buffer.append(" Welcome ");
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
                buffer.append("Authentication failed");
                session.writeResponse(buffer.toString());
            }
        } else {
            StringBuffer buffer = new StringBuffer(64);
            buffer.append(ERR);
            buffer.append(" ");
            buffer.append("APOP not allowed here");
            session.writeResponse(buffer.toString());
        }
    }
}
