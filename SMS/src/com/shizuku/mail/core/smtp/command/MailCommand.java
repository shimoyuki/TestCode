package com.shizuku.mail.core.smtp.command;

import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.CommandLine;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.SmtpCommands;
import com.shizuku.mail.exception.ArgumentException;
import com.shizuku.mail.exception.InvalidEmailAddressException;
import com.shizuku.mail.util.EmailAddress;

/**
 * Handle MAIL command.
 * 
 * <p>
 * This command is used to initiate a mail transaction in which the mail data is
 * delivered to an SMTP server which may, in turn, deliver it to one or more
 * mailboxes or pass it on to another system (possibly using SMTP). The argument
 * field contains a reverse-path and may contain optional parameters. In
 * general, the MAIL command may be sent only when no mail transaction is in
 * progress.
 * </p>
 * 
 * <p>
 * The reverse-path consists of the sender mailbox. Historically, that mailbox
 * might optionally have been preceded by a list of hosts, but that behavior is
 * now deprecated (see appendix C). In some types of reporting messages for
 * which a reply is likely to cause a mail loop (for example, mail delivery and
 * nondelivery notifications), the reverse-path may be null.
 * </p>
 * 
 * <p>
 * This command clears the reverse-path buffer, the forward-path buffer, and the
 * mail data buffer; and inserts the reverse-path information from this command
 * into the reverse-path buffer. If service extensions were negotiated, the MAIL
 * command may also carry parameters associated with a particular service
 * extension. Syntax: "MAIL FROM:" ("&lt;&gt;" / Reverse-Path) [SP
 * Mail-parameters] CRLF
 * </p>
 * 
 * 
 * 
 * 
 * 
 */
public class MailCommand implements Command, SmtpCommands {

    /**
     * @see com.shizuku.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session
     *            The Session of Server and client
     */
    public void onCommand(Session session) {
        int action = session.getLastAction();

        if (action != AUTH && action != EHLO && action != HELO) {
            session.writeResponse("503 Need EHLO/HELO first.");
            return;
        }

        EmailAddress emailAddress = getEmailAddress(session);
        if (emailAddress == null) {
            return;
        }
        /**
         * if (action == AUTH) { session.setLastAction(MAIL);
         * session.writeResponse("250 OK"); session.addOperation("MAIL_FROM",
         * emailAddress.toString()); } else { String ips[] = null; try { ips =
         * DNS.getMXHostIP(emailAddress.getDomain()); } catch (Exception e) {
         * ips = null; }
         * 
         * if (ips == null) { session.writeResponse(
         * "501 Contains a domain with no valid MX records"); return; } else {
         * String ip = session.getClinetSocket().getInetAddress().
         * getHostAddress(); try { if (SMSUtils.contains(ips, ip)) {
         * session.setLastAction(MAIL); session.writeResponse("250 OK");
         * session.addOperation("MAIL_FROM", emailAddress.toString()); return; }
         * } catch (Exception e) { } }
         * session.writeResponse("553 Authentication is required."); }
         */
        session.setLastAction(MAIL);
        session.writeResponse("250 OK");
        session.addOperation("MAIL_FROM", emailAddress.toString());
    }

    /**
     * 
     * @param session
     * @return
     */
    private EmailAddress getEmailAddress(Session session) {
        String argument = null;
        try {
            CommandLine cl = session.getCommandLine();
            argument = cl.getNextArgument();

            if (!argument.toUpperCase().startsWith("FROM:")) {
                session.writeResponse("501 Syntax error, MAIL FROM:<user@domain>");
                return null;
            }

            if (argument.lastIndexOf("<") == -1 || argument.lastIndexOf(">") == -1) {
                argument = cl.getNextArgument();
            }
        } catch (ArgumentException ae) {
            argument = null;
        }

        if (argument == null) {
            session.writeResponse("500 Syntax error");
            return null;
        } else {
            String from = EmailAddress.grabMailAddress(argument);

            EmailAddress emailAddress = null;
            try {
                emailAddress = new EmailAddress(from);
            } catch (InvalidEmailAddressException e) {
                session.writeResponse("553 Mailbox syntax incorrect");
                return null;
            }
            return emailAddress;
        }
    }
}
