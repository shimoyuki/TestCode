package com.shizuku.mail.core.pop3;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.shizuku.mail.Constants;
import com.shizuku.mail.core.AbstractProcessor;
import com.shizuku.mail.core.CommandLine;
import com.shizuku.mail.core.CommandsHandler;
import com.shizuku.mail.core.ServerState;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.pop3.command.Pop3CommandHandler;
import com.shizuku.mail.domain.DomainHandler;
import com.shizuku.mail.util.SMSUtils;

/**
 * Pop3 AbstractProcessor
 */
public class Pop3Processor extends AbstractProcessor implements Pop3States, ServerState {

    /**
     * Creates an instance of Logger and initializes it. 
     * It is to write log for <code>Pop3Processor</code>.
     */
    private Logger log = Logger.getLogger(Pop3Processor.class);

    /**
     * Handle a client connection
     * @param socket
     */
    public Pop3Processor(Socket socket) {
        super(socket);
    }

    @Override
    public void run() {
        Session session = new Pop3Session();
        session.setClientSocket(clientSocket);
        int lastCommand = READY;
        try {
            clientSocket.setSoTimeout(Constants.DEFAULT_TIMEOUT);
            StringBuffer IDBuffer = new StringBuffer(128);
            IDBuffer.append("<");
            IDBuffer.append(SMSUtils.getID());
            IDBuffer.append("@");
            IDBuffer.append(new DomainHandler().getDefaultDomain());
            IDBuffer.append(">");

            StringBuffer buffer = new StringBuffer(256);
            buffer.append(OK);
            buffer.append(" ");
            buffer.append(Constants.NAME);
            buffer.append(" ");
            buffer.append("POP3 Server");
            buffer.append(" ");
            buffer.append("Ready");
            buffer.append(" ");
            buffer.append(IDBuffer.toString());

            session.addOperation("SHARE_ID", IDBuffer.toString());

            session.writeResponse(buffer.toString());
            session.setLastAction(READY);
            CommandsHandler handler = Pop3CommandHandler.getSingletonInstance();

            while (lastCommand != UPDATE && lastCommand != AUTHENTICATION_QUIT) {
                lastCommand = doCommand(handler, session);
            }
        } catch (SocketException se) {
            log.error(se);
        } finally {
            MailRepository mr = (MailRepository) session.getOperation().get("MAIL_REPOSITORY");
            if (mr != null) {
                mr.unlockMailRepository();
            }
            session.close();
        }
    }

    @Override
    protected int doCommand(CommandsHandler handler, Session session) {
        try {
            CommandLine commandLine = session.getCommandLine(session.readCommandLine());
            try {
                String name = commandLine.getName();
                handler.doCommand(name, session);
                return session.getLastAction();
            } catch (Exception e) {
                return AUTHENTICATION_QUIT;
            }
        } catch (IOException e) {
            return AUTHENTICATION_QUIT;
        }
    }
}