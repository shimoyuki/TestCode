package com.shizuku.mail.core;

import java.net.Socket;

/**
 * SMS Processor. The old name of this class is <code>Processor</code>.
 * 
 */
public abstract class AbstractProcessor extends Thread {

    /**
     * The TCP/IP socket over which the POP3 interaction is occurring
     * Client socket
     */
    protected Socket clientSocket;

    public AbstractProcessor(Socket socket) {
        clientSocket = socket;
    }

    /**
     * Do with the Command
     * 
     * @param handler
     * @param session
     * @return the last action
     */
    protected abstract int doCommand(CommandsHandler handler, Session session);
}