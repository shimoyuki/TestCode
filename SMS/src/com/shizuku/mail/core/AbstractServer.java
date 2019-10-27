package com.shizuku.mail.core;

import org.apache.log4j.Logger;

public abstract class AbstractServer implements Server {

    /**
     * Creates an instance of Logger and initializes it. It is to write log for
     * <code>AbstractServer</code>.
     */
    private Logger          log = Logger.getLogger(AbstractServer.class);
    protected ServerService service;
    protected int[]         ports;

    public AbstractServer() {
        /**
         * Initials ServerService
         */
        service = new MailServerService();
    }

    /**
     * @see Server#restart()
     * 
     */
    public void restart() {
        log.info("Now Stop Server ......");
        stop();
        log.info("Now Start Server ......");
        start();
        log.info("Restart Server OK.");
    }

    /**
     * Clear services
     * 
     * 
     */
    protected void clearServcice() {
        service.getService().clear();
    }

    public int[] getPorts() {
        return this.ports;
    }

    public void setPorts(int[] ports) {
        this.ports = ports;
    }
}
