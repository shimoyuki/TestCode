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
package org.jpxx.mail.core.smtp.command;

import java.io.IOException;
import java.util.ArrayList;

import org.jpxx.mail.core.Command;
import org.jpxx.mail.core.Session;
import org.jpxx.mail.core.smtp.MailHandler;
import org.jpxx.mail.core.smtp.SmtpCommands;

/**
 * Handle DATA command. 
 * The receiver normally sends a 354 response to DATA, and then treats 
 * the lines (strings ending in <CRLF> sequences, following the command 
 * as mail data from the sender. This command causes the mail data to be 
 * appended to the mail data buffer. The mail data may contain any of the 
 * 128 ASCII character codes, although experience has indicated that 
 * use of control characters other than SP, HT, CR, and LF may cause problems 
 * and SHOULD be avoided when possible.
 * 
 * The mail data is terminated by a line containing only a period, that 
 * is, the character sequence "<CRLF>.<CRLF>" (see section 4.5.2). This 
 * is the end of mail data indication. Note that the first <CRLF> of 
 * this terminating sequence is also the <CRLF> that ends the final line 
 * of the data (message text) or, if there was no data, ends the DATA 
 * command itself. An extra <CRLF> MUST NOT be added, as that would 
 * cause an empty line to be added to the message. The only exception 
 * to this rule would arise if the message body were passed to the 
 * originating SMTP-sender with a final "line" that did not end in 
 * <CRLF>; in that case, the originating SMTP system MUST either reject 
 * the message as invalid or add <CRLF> in order to have the receiving 
 * SMTP server recognize the "end of data" condition.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/24 19:20:00 $
 * 
 */
public class DataCommand implements Command, SmtpCommands {

    /**
     * @see org.jpxx.mail.core.Command.CommandHandler#onCommand(Session session)
     * @param session session The Session of Server and client
     */
    @SuppressWarnings("unchecked")
    public void onCommand(Session session) {
        if (session.getLastAction() == RCPT) {
            try {
                session.setLastAction(DATA);
                session.writeResponse("354 Start mail input; end with <CRLF>.<CRLF>");
                String user = session.getOperation().get("MAIL_FROM").toString();
                ArrayList to = (ArrayList) session.getOperation().get("RCPT_TO");
                String line = session.readLine();
                MailHandler mh = new MailHandler(user, to);

                while (!line.equals(".")) {
                    mh.writeLine(line);
                    line = session.readLine();
                }
                mh.closeMessage();
                session.writeResponse("250 OK");
                session.addOperation("MAIL_HANDLER", mh);
            } catch (IOException e) {
                //Ignore
            }
        } else {
            session.writeResponse("503 DATA Not allowed here.");
        }
    }
}
