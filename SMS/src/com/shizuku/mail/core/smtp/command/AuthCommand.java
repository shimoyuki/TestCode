package com.shizuku.mail.core.smtp.command;

import java.io.IOException;
import java.util.StringTokenizer;

import org.xbill.DNS.utils.base64;

import com.shizuku.mail.SysConfig;
import com.shizuku.mail.core.Command;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.SmtpCommands;
import com.shizuku.mail.domain.DomainHandler;
import com.shizuku.mail.exception.ArgumentException;
import com.shizuku.mail.exception.InvalidEmailAddressException;
import com.shizuku.mail.user.UserHandler;
import com.shizuku.mail.util.Base64;
import com.shizuku.mail.util.EmailAddress;

/**
 * Handle AUTH command.
 * 
 * <p>
 * After an AUTH command has successfully completed, no more AUTH commands may
 * be issued in the same session. After a successful AUTH command completes, a
 * server MUST reject any further AUTH commands with a 503 reply. The AUTH
 * command is not permitted during a mail transaction.
 * </p>
 * 
 * <p>
 * For more informaiton please read RFC 2554.
 * </p>
 * 
 * 
 * 
 * 
 * 
 */
public class AuthCommand implements Command, SmtpCommands {

    /**
     * The text string for the SMTP AUTH type LOGIN.
     */
    private final static String AUTH_TYPE_LOGIN = "LOGIN";
    /**
     * The text string for the SMTP AUTH type PLAIN.
     */
    private final static String AUTH_TYPE_PLAIN = "PLAIN";

    private int                 lastCommand     = EHLO;

    /**
     * @see Command#onCommand(Session session)
     * @param session
     *            The Session of Server and client
     */
    public void onCommand(Session session) {
        if (session.getLastAction() == HELO || session.getLastAction() == EHLO) {
            String argument = null;
            try {
                argument = session.getCommandLine().getArgument(0);
            } catch (ArgumentException ae) {
                session.writeResponse("500 Syntax error");
                return;
            }

            if (argument.equalsIgnoreCase(AUTH_TYPE_LOGIN)) {
                this.doAuthLogin(session);
                session.setLastAction(lastCommand);
            } else if (argument.equalsIgnoreCase(AUTH_TYPE_PLAIN)) {
                this.doAuthPlain(session);
                session.setLastAction(lastCommand);
            } else {
                /**
                 * If the requested authentication mechanism is not supported,
                 * the server rejects the AUTH command with a 504 reply.
                 */
                String responseString = "504 Unrecognized Authentication Type";
                session.writeResponse(responseString);
            }
        } else {
            session.writeResponse("503 Need EHLO/HELO first.");
        }
    }

    /**
     * Carries out the Login AUTH SASL exchange.
     * 
     * @param session
     *            The Session of server and client
     */
    private void doAuthLogin(Session session) {
        StringBuffer buffer = new StringBuffer(64);
        String userName = null;
        String password = null;

        /**
         * Rresponse a 334 reply with the text part containing a BASE64 encoded
         * string.
         */
        buffer.append("334");
        buffer.append(" ");
        buffer.append(Base64.encodeStr("Username:"));
        session.writeResponse(buffer.toString());

        try {
            String line = session.readCommandLine();
            userName = Base64.decodeStr(line);
            System.out.println(userName);
            //userName = line;
        } catch (Exception e) {
        	e.printStackTrace();
            userName = null;
        }

        buffer = new StringBuffer(64);
        buffer.append("334");
        buffer.append(" ");
        buffer.append(Base64.encodeStr("Password:"));
        session.writeResponse(buffer.toString());
        

        try {
            String line = session.readCommandLine();
            password = Base64.decodeStr(line);
            System.out.println(password);
            //password = line;
        } catch (Exception e) {
        	e.printStackTrace();
            password = null;
        }

        session.writeResponse(checkLogin(session, userName, password));
    }

    /**
     * Carries out the Plain AUTH SASL exchange. According to RFC 2595 the
     * client must send: [authorize-id] \0 authenticate-id \0 password.
     * 
     * @param session
     *            The Session of server and client
     */
    private void doAuthPlain(Session session) {
        String argument = null;
        try {
            argument = session.getCommandLine().getArgument(1);
        } catch (ArgumentException ae) {
            argument = null;
        }

        if (argument != null) {
            argument = Base64.decodeStr(argument);
        } else {
            session.writeResponse("334 OK. Continue authentication");
            try {
                argument = session.readCommandLine();
                argument = Base64.decodeStr(argument);
            } catch (IOException e) {
                argument = null;
            }
        }

        String userName = null;
        String password = null;

        if (argument != null) {
            StringTokenizer tokenizer = new StringTokenizer(argument, "\0");
            userName = tokenizer.nextToken();
            password = tokenizer.nextToken();
        }
        session.writeResponse(checkLogin(session, userName, password));
    }

    /**
     * 
     * @param userName
     * @param password
     * @return
     */
    private String checkLogin(Session session, String userName, String password) {
        String responseString = "";
        if (userName == null || password == null) {
            responseString = "501 Could not decode parameters for AUTH LOGIN";
            return responseString;
        }

        String replyMail = new SysConfig().getSystemReplyMail();
        String ip = session.getClinetSocket().getInetAddress().getHostAddress();
        if (userName.equalsIgnoreCase(replyMail) && ip.equals("127.0.0.1")) {
            responseString = "235 Authentication Successful";
            lastCommand = AUTH;
        } else {
            UserHandler handler = new UserHandler();
            EmailAddress emailAddress = null;
            if (userName.contains("@")) {
                try {
                    emailAddress = new EmailAddress(userName);
                    if (handler.check(emailAddress, password)) {
                        responseString = "235 Authentication Successful";
                        lastCommand = AUTH;
                    } else {
                        responseString = "535 Authentication Failed";
                    }
                } catch (InvalidEmailAddressException e) {
                    e.printStackTrace();
                    responseString = "501 parameters for AUTH LOGIN ERROR";
                }
            } else {
                DomainHandler d = new DomainHandler();
                try {
                    emailAddress = new EmailAddress(userName + "@" + d.getDefaultDomain());

                    if (handler.check(emailAddress, password)) {
                        responseString = "235 Authentication Successful";
                        lastCommand = AUTH;
                    } else {
                        responseString = "535 Authentication Failed";
                    }
                } catch (InvalidEmailAddressException e) {
                    responseString = "501 Unknow ERROR";
                }
            }
        }
        return responseString;
    }
}
