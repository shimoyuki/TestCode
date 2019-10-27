package com.shizuku.mail.core;

/**
 * This interface defines some operations to manage SMS.
 */
public interface Server {

    /**
     * Starts server.
     */
    public void start();

    /**
     * Restarts Server
     */
    public void restart();

    /**
     * Stops server. It is used to stop SMTP/POP3 services.
     */
    public void stop();

    /**
     * Returns server's running ports
     * @return 
     */
    public int[] getPorts();

    /**
     * Sets server ports 
     * @param ports
     */
    public void setPorts(int[] ports);
}
