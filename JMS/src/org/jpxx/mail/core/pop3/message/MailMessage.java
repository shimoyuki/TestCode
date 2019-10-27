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
package org.jpxx.mail.core.pop3.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * This class represents a simple MIME style email message. 
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/27 19:21:00 $
 * 
 */
public class MailMessage {

    private MailMessageHeaders mmh = null;

    public MailMessage() {
    }

    /**
     * @param is the message input stream
     * @return this message's header 
     * @throws org.jpxx.mail.Message.MessageException
     * @throws java.io.IOException
     */
    public MailMessageHeaders getMailMessageHeaders(InputStream is) throws MessageException,
                                                                   IOException {
        if (mmh == null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            mmh = new MailMessageHeaders(br);
            is.close();
        }
        return mmh;
    }

    /**
     * Read message content 
     * @param is the message input stream
     * @return the message content 
     * @throws java.io.IOException
     */
    public List<String> getMailMessageContent(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> lines = new ArrayList<String>();
        String line = br.readLine();
        while (line != null) {
            lines.add(line);
            line = br.readLine();
        }
        is.close();
        return lines;
    }

    /**
     * is the message input stream
     * 
     * @param is
     * @param i
     * @return the message content
     * @throws org.jpxx.mail.Message.MessageException
     * @throws java.io.IOException
     */
    public List<String> getMailMessageContent(InputStream is, int i) throws MessageException,
                                                                    IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        mmh = new MailMessageHeaders(br);

        List<String> lines = new ArrayList<String>();
        for (Enumeration<String> e = mmh.getAllHeaderLines(); e.hasMoreElements();) {
            lines.add(e.nextElement());
        }

        lines.add("");

        String line = br.readLine();
        while (line != null && i > 0) {
            lines.add(line);
            line = br.readLine();
            i--;
        }
        is.close();
        return lines;
    }
}
