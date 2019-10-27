package com.shizuku.mail.core.pop3;

import com.shizuku.mail.core.AbstractCommandLine;
import com.shizuku.mail.exception.ArgumentException;

/**
 * Do with a line of Pop3 command String.
 */
public class Pop3CommandLine extends AbstractCommandLine {

    /**
     * Construct function
     * @param line a string line
     */
    public Pop3CommandLine(String line) throws ArgumentException {
        super(line);
    }

    /**
     * POP commandline at most has two arguments. 
     * 
     * @see com.shizuku.mail.Service.CommandLine#getArgumentCount() 
     * 
     * @return the number of arguments.
     */
    public int getArgumentCount() {
        if (line == null || line.lastIndexOf(" ") == -1) {
            return 0;
        }
        String args[] = line.split(" ");
        int length = args.length;
        if (length <= 1) {
            return 0;
        } else if (length <= 3) {
            return length - 1;
        } else {
            return 2;
        }
    }
}
