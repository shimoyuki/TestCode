package com.shizuku.mail.core.smtp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import com.shizuku.mail.core.AbstractServerHandler;

/**
 * Handles all Smtp Server
 */
class SmtpServerHandler extends AbstractServerHandler {

    public SmtpServerHandler(int port) throws IOException {
        super(port);
    }

    @Override
    public void run() {
        try {
            // a client's connection to the server
            Socket clientSocket = null;

            /**
             * Listen client
             */
            while (true) {
                try {
                    clientSocket = serverSocket.accept();
                    /**
                     * wait for a connection and when one comes in then
                     * tell process to go process it
                     */
                    processor = new SmtpProcessor(clientSocket);
                    processor.start();
                } catch (SocketException e) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
