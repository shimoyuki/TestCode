package com.shizuku.mail.core.pop3;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.shizuku.mail.SysConfig;
import com.shizuku.mail.core.AbstractServer;

/**
 * Manage All Pop3 Server
 */
public class Pop3Server extends AbstractServer {

    /**
     * Creates an instance of Logger and initializes it. 
     * It is to write log for <code>Pop3Server</code>.
     */
    private Logger log = Logger.getLogger(Pop3Server.class);

    public Pop3Server() {
        super();
        if (this.ports == null) {
            SysConfig sc = new SysConfig();
            this.ports = sc.getPop3Port();
        }
    }

    /**
     * Start all Pop3 servers. 
     * @see com.shizuku.mail.Service.Server#start()
     */
    public void start() {
        List<ServerSocket> list = new ArrayList<ServerSocket>();

        for (int i = 0; i < ports.length; i++) {
            try {
                Pop3ServerHandler handler = new Pop3ServerHandler(ports[i]);
                handler.start();
                list.add(handler.getServerSocket());
                log.info("Pop3 server start at port: " + ports[i]);
            } catch (IOException e) {
                log.error("Pop3 server can't listen at port: " + ports[i]);
            }
        }
        service.addService("POP3_SERVICES", list);
    }

    /**
     * @see com.shizuku.mail.Service.Server#stop()
     */
    @SuppressWarnings("unchecked")
    public void stop() {
        List services = (ArrayList) service.getService().get("POP3_SERVICES");
        if (services != null) {
            for (Iterator<ServerSocket> i = services.iterator(); i.hasNext();) {
                ServerSocket ss = i.next();
                try {
                    if (!ss.isClosed()) {
                        ss.close();
                        log.error("Stop Pop3 Server: " + ss.getLocalPort());
                    }
                } catch (Exception e) {
                    log.error("Close Pop3 Server Error!" + e.toString());
                }
            }
        }
        clearServcice();
    }
}
