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

import org.jpxx.mail.core.AbstractCommandLine;
import org.jpxx.mail.exception.ArgumentException;

/**
 * Do with a Smtp command String line.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.3 $, $Date: 2008/05/20 $
 * @since JMS 0.0.1
 * 
 */
public class SmtpCommandLine extends AbstractCommandLine {

    /**
     * 
     * @param line a string line. Command name and arguments are separated by 
     * <b>&lt;SP&gt;<b>.
     * @throws org.jpxx.mail.Exception.ArgumentException
     */
    public SmtpCommandLine(String line) throws ArgumentException {
        super(line);
    }

    /**
     * @since JMS 0.0.1
     * @see org.jpxx.mail.Service.CommandLine#getArgumentCount() 
     * @return the number of arguments.
     */
    public int getArgumentCount() {
        if (line == null || line.lastIndexOf(" ") == -1) {
            return 0;
        } else {
            String args[] = line.split(" ");
            return args.length - 1;
        }
    }
}
