package com.shizuku.mail.core;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.Logger;

/**
 * Server Handler
 * 
 * 
 */
public abstract class AbstractServerHandler extends Thread {

    /**
     * Creates an instance of Logger and initializes it. 
     * It is to write log for <code>AbstractServerHandler</code>.
     */
    protected Logger            log          = Logger.getLogger(AbstractServerHandler.class);
    protected ServerSocket      serverSocket = null;
    protected AbstractProcessor processor    = null;

    public AbstractServerHandler(int port) throws IOException {
        /**
         * listen to the given port
         */
        serverSocket = new ServerSocket(port);
    }

    /**
     * Get Pop3 Server ServerSocket
     * @return The object of ServerSocket in current port.
     */
    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }
}
