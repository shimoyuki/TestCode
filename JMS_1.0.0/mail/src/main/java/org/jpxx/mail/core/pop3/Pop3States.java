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

/**
 * Pop3 protocol Pop3States.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/25 16:58:00 $
 * 
 */
public interface Pop3States {

    /**
     * The AUTHORIZATION Pop3States.
     * The client must now identify and authenticate itself to the POP3 server.
     * Two possible mechanisms for doing this are described in this document,
     * the USER and PASS command combination and the APOP command.
     */
    public final static int    AUTHENTICATION      = 1;
    public final static int    AUTHENTICATION_QUIT = 2;
    /**
     * Once the client has successfully identified itself to the POP3 server
     * and the POP3 server has locked and opened the appropriate maildrop,
     * the POP3 session is now in the TRANSACTION state.
     */
    public final static int    TRANSACTION         = 3;
    /**
     * The UPDATE Pop3States
     */
    public final static int    UPDATE              = 4;
    /**
     * There are currently two status
     * indicators: positive ("+OK") and negative ("-ERR"). 
     * Servers MUST send the "+OK" and "-ERR" in upper case.
     */
    public final static String OK                  = "+OK";
    public final static String ERR                 = "-ERR";
}
