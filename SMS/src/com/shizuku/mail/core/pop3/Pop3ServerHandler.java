package com.shizuku.mail.core.pop3;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import com.shizuku.mail.core.AbstractServerHandler;

/**
 * Handles Pop3 Server
 */
public class Pop3ServerHandler extends AbstractServerHandler {

    public Pop3ServerHandler(int port) throws IOException {
        super(port);
    }

    @Override
    public void run() {
        try {
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
                    processor = new Pop3Processor(clientSocket);
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