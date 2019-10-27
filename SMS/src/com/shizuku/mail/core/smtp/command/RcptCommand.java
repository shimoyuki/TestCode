package com.shizuku.mail.core.smtp.command;

import java.util.ArrayList;
import java.util.HashMap;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.SmtpCommands;
import com.shizuku.mail.domain.DomainHandler;
import com.shizuku.mail.exception.ArgumentException;
import com.shizuku.mail.exception.InvalidEmailAddressException;
import com.shizuku.mail.user.UserHandler;
import com.shizuku.mail.util.EmailAddress;

/**
 * Handle RCPT Command.
 * 
 * <p>This command is used to identify an individual recipient of the mail 
 * data; multiple recipients are specified by multiple use of this 
 * command. The argument field contains a forward-path and may contain 
 * optional parameters. </p>
 */
public class RcptCommand implements Command, SmtpCommands {

    /**
     * @see com.shizuku.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session The Session of Server and client
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        if (session.getLastAction() == MAIL || session.getLastAction() == RCPT) {
            String argument = null;
            try {
                argument = session.getCommandLine().getArgument();
            } catch (ArgumentException ae) {
                argument = null;
            }

            if (argument == null || argument.equals("")) {
                session.writeResponse("500 Syntax error");
            } else if (!argument.toUpperCase().startsWith("TO:")) {
                session.writeResponse("501 Syntax error, RCPT TO:<user@domain>");
            } else {
                String to = EmailAddress.grabMailAddress(argument);
                try {
                    EmailAddress ea = new EmailAddress(to);
                    DomainHandler dc = new DomainHandler();
                    /**
                     * Local user
                     */
                    if (dc.isExist(ea.getDomain())) {
                        UserHandler uh = new UserHandler();
                        if (!uh.checkUser(ea)) {
                            session.writeResponse("550 No such user");
                        } else {
                            session.setLastAction(RCPT);
                            session.writeResponse("250 OK");
                        }
                    } else {
                        /**
                         * User not in local server
                         */
                        session.setLastAction(RCPT);
                        session.writeResponse("250 OK");
                    }

                    HashMap map = session.getOperation();
                    ArrayList al = null;
                    if (map.get("RCPT_TO") == null) {
                        al = new ArrayList();
                    } else {
                        al = (ArrayList) map.get("RCPT_TO");
                    }
                    al.add(to);
                    session.addOperation("RCPT_TO", al);
                } catch (InvalidEmailAddressException e) {
                    session.writeResponse("553 Mailbox syntax incorrect");
                }
            }
        } else {
            session.writeResponse("503 RCPT Not allowed here.");
        }

    }
}
