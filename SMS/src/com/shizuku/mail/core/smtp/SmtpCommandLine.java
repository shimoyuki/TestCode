package com.shizuku.mail.core.smtp;

import com.shizuku.mail.core.AbstractCommandLine;
import com.shizuku.mail.exception.ArgumentException;

/**
 * Do with a Smtp command String line. 
 */
public class SmtpCommandLine extends AbstractCommandLine {

    /**
     * 
     * @param line a string line. Command name and arguments are separated by 
     * <b>&lt;SP&gt;<b>.
     * @throws com.shizuku.mail.Exception.ArgumentException
     */
    public SmtpCommandLine(String line) throws ArgumentException {
        super(line);
    }

    /**
     * 
     * @see com.shizuku.mail.Service.CommandLine#getArgumentCount() 
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
