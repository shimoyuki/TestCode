package com.shizuku.mail.core.smtp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.shizuku.mail.SysConfig;
import com.shizuku.mail.core.AbstractServer;

/**
 * <p><code>SmtpServer</code> is to manage all smtp server in a same machine with
 * different SMTP SERVER ports.<p>
 */
public class SmtpServer extends AbstractServer {

    /**
     * Creates an instance of Logger and initializes it. 
     * It is to write log for <code>SmtpServer</code>.
     */
    private Logger log = Logger.getLogger(SmtpServer.class);

    /**
     * Gets singleton instance of SmtpServer.
     * @return singleton instance of SmtpServer
     */
    public SmtpServer() {
        super();
        if (this.ports == null) {
            SysConfig sc = new SysConfig();
            this.ports = sc.getSmtpPort();
        }
    }

    /**
     * Starts all SMTP server.
     * @see com.shizuku.mail.Service.Server#start()
     */
    public void start() {
        List<ServerSocket> list = new ArrayList<ServerSocket>();

        for (int i = 0; i < ports.length; i++) {
            try {
                SmtpServerHandler handler = new SmtpServerHandler(ports[i]);
                handler.start();
                list.add(handler.getServerSocket());
                log.info("Smtp server start at port: " + ports[i]);
            } catch (IOException e) {
                log.error("Smtp server can't listen at port: " + ports[i]);
            }
        }
        service.addService("SMTP_SERVICES", list);
    }

    /**
     * Stops all SMTP server.
     * @see com.shizuku.mail.Service.Server#stop()
     */
    @SuppressWarnings("unchecked")
    public void stop() {
        List<ServerSocket> services = (ArrayList) service.getService().get("SMTP_SERVICES");
        if (services != null) {
            for (Iterator<ServerSocket> i = services.iterator(); i.hasNext();) {
                ServerSocket ss = i.next();
                try {
                    if (!ss.isClosed()) {
                        ss.close();
                        log.info("Stop Smtp Server: " + ss.getLocalPort());
                    }
                } catch (Exception e) {
                    log.error("Close Smtp Server Error!" + e.getMessage());
                }
            }
        }
        clearServcice();
    }

}
