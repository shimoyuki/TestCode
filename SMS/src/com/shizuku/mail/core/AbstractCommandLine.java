package com.shizuku.mail.core;

import com.shizuku.mail.exception.ArgumentException;

/**
 * <tt>AbstractCommandLine</tt> is an abstract class, which implements some 
 * functions of the interface <tt>CommandLine</tt>.
 */
public abstract class AbstractCommandLine implements CommandLine {

    /**
     * A command line(String)
     */
    protected String line    = null;
    /**
     * Argument counter
     */
    private int      counter = 0;
    /**
     * Arguments
     */
    private String   args[];

    /**
     * 
     * @param line a command line
     * @throws com.shizuku.mail.Exception.ArgumentException
     */
    public AbstractCommandLine(String line) throws ArgumentException {
        if (line == null) {
            throw new ArgumentException("Command line is NULL.");
        } else if (line.trim().equals("")) {
            throw new ArgumentException("No command.");
        } else {
            line = line.trim();
            line = line.replace("  ", " ");
            args = line.split(" ");
            this.line = line;
        }
    }

    /**
     * @see CommandLine#getName()
     * @return the command name of current line
     */
    public String getName() {
        int spaceIndex = line.indexOf(" ");
        if (spaceIndex == -1) {
            return line.toUpperCase();
        } else {
            return line.substring(0, spaceIndex).toUpperCase();
        }
    }

    /**
     * @see CommandLine#getArgument()
     * @return the command argument of current line.
     */
    public String getArgument() throws ArgumentException {
        int spaceIndex = line.indexOf(" ");
        if (spaceIndex == -1) {
            throw new ArgumentException("No argument.");
        } else {
            return line.substring(spaceIndex + 1);
        }
    }

    /**
     * @see CommandLine#getArgument(int num)
     * 
     * @param num 0 - i
     * @return the argument
     * @throws com.shizuku.mail.Exception.ArgumentException
     */
    public String getArgument(int num) throws ArgumentException {
        int length = args.length;
        if (length == 1) {
            throw new ArgumentException("No argument.");
        } else if (num > length - 2) {
            throw new ArgumentException("Out of boundary.");
        } else {
            return args[num + 1].trim();
        }
    }

    /**
     * @see CommandLine#getNextArgument()
     * 
     * 
     * @return the argument. If no argument, the null object will be returned. 
     * @throws com.shizuku.mail.Exception.ArgumentException
     */
    public String getNextArgument() throws ArgumentException {
        int length = args.length;
        if (length == 1 || counter > length - 1) {
            return null;
        } else {
            this.counter++;
            return args[counter].trim();
        }
    }

    /**
     * @see CommandLine#getLine()
     * @return The line of String
     */
    public String getLine() {
        return this.line;
    }
}
