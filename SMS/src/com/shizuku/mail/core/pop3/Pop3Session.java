package com.shizuku.mail.core.pop3;

import com.shizuku.mail.core.AbstractSession;
import com.shizuku.mail.core.CommandLine;
import com.shizuku.mail.exception.ArgumentException;

/**
 * The conversation of pop3 Server and client 
 */
public class Pop3Session extends AbstractSession {

    public Pop3Session() {
        super();
    }

    /**
     * @see com.shizuku.mail.Service.Session#getCommandLine(String strLine)
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
