package com.shizuku.mail.core;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * This is an interface to define the communication of server and client.
 */
public interface Session {

    /**
     * Returns client socket.
     * 
     * @return client socket
     */
    public Socket getClinetSocket();

    /**
     * Sets client socket
     * 
     * @param socket
     */
    public void setClientSocket(Socket socket);

    /**
     * Read a line from the request of client
     * 
     * @return a line of String.(Command line)
     * @throws java.io.IOException
     */
    public String readCommandLine() throws IOException;

    /**
     * Read an origin line
     * 
     * @return a line of data string
     * @throws java.io.IOException
     * 
     */
    public String readLine() throws IOException;

    /**
     * Server response
     * 
     * @param response
     */
    public void writeResponse(String response);

    /**
     * Get an object of a line string
     * 
     * @param strLine
     * @return an object of CommandLine
     */
    public CommandLine getCommandLine(String strLine);

    /**
     * Get last read (line)
     * 
     * @return an object of CommandLine
     */
    public CommandLine getCommandLine();

    /**
     * Store Operation to a hash map Get the operation
     * 
     * @return hash map
     */
    public HashMap<String, Object> getOperation();

    /**
     * Add operation to map
     * 
     * @param name
     * @param value
     */
    public void addOperation(String name, Object value);

    /**
     * Clear the operation int map
     */
    public void clear();

    /**
     * Get Last Action value(Integer)
     * 
     * @return last action
     */
    public int getLastAction();

    /**
     * Set Last command value(Integer)
     * 
     * @param lastAction
     *            the last command number
     */
    public void setLastAction(int lastAction);

    /**
     * Close current session. Client is quit. Release the resource.
     */
    public void close();
}
