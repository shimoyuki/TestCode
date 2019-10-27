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
package org.jpxx.mail.core;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * This is an interface to define the communication of server and client.
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version $ org.jpxx.mail.core.Session.java $, $Date: 2009-4-29 $
 * 
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
     * @since JMS 0.0.3
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
