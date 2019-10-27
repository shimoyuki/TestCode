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
package org.jpxx.mail.core.smtp;

/**
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.mail.core.smtp.SmtpCommands.java $, $Date:
 *          2009-4-29 $
 * 
 */
public interface SmtpCommands {

    public final static int EHLO = 1;
    public final static int HELO = 2;
    public final static int AUTH = 3;
    public final static int MAIL = 4;
    public final static int RCPT = 5;
    public final static int DATA = 6;
    public final static int RSET = 7;
    public final static int VRFY = 8;
    public final static int EXPN = 9;
    public final static int HELP = 10;
    public final static int NOOP = 11;
    public final static int QUIT = 12;
}
