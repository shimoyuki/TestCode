/****************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.     *
 *                                                              *
 * Copyright 2009 Jun Li( The SOFTWARE ENGINEERING COLLEGE OF   *
 * SiChuan University). All rights reserved.                    *
 *                                                              *
 * Licensed to the JMS under one  or more contributor license   *
 * agreements.  See the LICENCE file  distributed with this     *
 * work for additional information regarding copyright          *
 * ownership.  The JMS licenses this file  you may not use this *
 * file except in compliance  with the License.                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package org.jpxx.mail.core.pop3;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jpxx.mail.SysConfig;
import org.jpxx.mail.core.AbstractServer;

/**
 * Manage All Pop3 Server
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 13:47:00 $
 * 
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
     * @see org.jpxx.mail.Service.Server#start()
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
     * @see org.jpxx.mail.Service.Server#stop()
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
