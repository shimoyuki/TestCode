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

import org.jpxx.mail.core.AbstractSession;
import org.jpxx.mail.core.CommandLine;
import org.jpxx.mail.exception.ArgumentException;

/**
 * The conversation of pop3 Server and client 
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 17:50:00 $
 */
public class Pop3Session extends AbstractSession {

    public Pop3Session() {
        super();
    }

    /**
     * @see org.jpxx.mail.Service.Session#getCommandLine(String strLine)
     * @return An object of CommandLine
     */
    public CommandLine getCommandLine(String strLine) {
        try {
            return new Pop3CommandLine(strLine);
        } catch (ArgumentException ae) {
            return null;
        }
    }
}
