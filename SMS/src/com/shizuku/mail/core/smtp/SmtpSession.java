package com.shizuku.mail.core.smtp;

import com.shizuku.mail.core.AbstractSession;
import com.shizuku.mail.core.CommandLine;
import com.shizuku.mail.exception.ArgumentException;

/**
 * The conversation of smtp Server and client 
 */
public class SmtpSession extends AbstractSession {

    public SmtpSession() {
        super();
    }

    /**     
     * @see com.shizuku.mail.Service.Session#getCommandLine(String strLine)
     * @return An object of CommandLine
     */
    public CommandLine getCommandLine(String strLine) {
        try {
            return new SmtpCommandLine(strLine);
        } catch (ArgumentException ae) {
            return null;
        }
    }
}