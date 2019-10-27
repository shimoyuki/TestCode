package com.shizuku.mail.core.smtp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import com.shizuku.mail.Constants;
import com.shizuku.mail.core.AbstractProcessor;
import com.shizuku.mail.core.CommandLine;
import com.shizuku.mail.core.CommandsHandler;
import com.shizuku.mail.core.ServerState;
import com.shizuku.mail.core.Session;
import com.shizuku.mail.core.smtp.command.SmtpCommandsHandler;

/**
 * Smtp Server Processor
 */
public class SmtpProcessor extends AbstractProcessor implements SmtpCommands, ServerState {

    /**
     * Handle a client connection
     * @param socket
     */
    public SmtpProcessor(Socket socket) {
        super(socket);
    }

    @Override
    public void run() {
        Session session = new SmtpSession();
        session.setClientSocket(clientSocket);
        int lastCommand = READY;
        try {
            clientSocket.setSoTimeout(Constants.DEFAULT_TIMEOUT);
            StringBuffer buffer = new StringBuffer(128);
            buffer.append("220");
            buffer.append(" ");
            buffer.append(Constants.NAME);
            buffer.append(" ");
            buffer.append("SMTP Server");
            buffer.append(" ");
            buffer.append("Ready");
            session.writeResponse(buffer.toString());

            CommandsHandler handler = SmtpCommandsHandler.getSingletonInstance();

            while (lastCommand != QUIT) {
                lastCommand = doCommand(handler, session);
            }
        } catch (SocketException se) {
        } finally {
            if (session.getOperation().get("MAIL_HANDLER") != null) {
                MailHandler mh = (MailHandler) session.getOperation().get("MAIL_HANDLER");
                // close connection and send mail
                mh.sendMessage(true);
            }
            session.close();
        }
    }

    /**
     * 
     * @param handler
     * @param session
     * @return
     */
    @Override
    protected int doCommand(CommandsHandler handler, Session session) {
        try {
            CommandLine commandLine = session.getCommandLine(session.readCommandLine());
            try {
                String name = commandLine.getName();
                handler.doCommand(name, session);
                return session.getLastAction();
            } catch (Exception e) {
                return QUIT;
            }
        } catch (IOException e) {
            return QUIT;
        }
    }
}
