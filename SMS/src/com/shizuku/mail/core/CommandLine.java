package com.shizuku.mail.core;

import com.shizuku.mail.exception.ArgumentException;

/**
 * <p>
 * This is an interface to do with a line of command String. The command line,
 * for example,
 * </p>
 * 
 * <p>
 * <i>MAIL FROM:&lt;test@jpxx.org&gt;<i>
 * </p>
 * 
 *  
 * 
 * 
 */
public interface CommandLine {

    /**
     * Returns command name. A command line includes one comand name and zero or
     * more arguments.
     * 
     * @return the command name of current line
     */
    public String getName();

    /**
     * Returns argument sting. Returns the left line except command name. It is
     * only for an argument.
     * 
     * @return the command argument of current line.
     * @throws com.shizuku.mail.exception.ArgumentException
     */
    public String getArgument() throws ArgumentException;

    /**
     * Returns the argument <i>num</i>. If num is not efficient, return null
     * object.
     * 
     * @param num
     *            given argument number
     * @return the given command argument
     * @throws com.shizuku.mail.exception.ArgumentException
     */
    public String getArgument(int num) throws ArgumentException;

    /**
     * Returns the next argument.
     * 
     * @return the given command argument
     * @throws com.shizuku.mail.exception.ArgumentException
     */
    public String getNextArgument() throws ArgumentException;

    /**
     * Returns the number of arguments.
     * 
     * @return the number of arguments.
     */
    public int getArgumentCount();

    /**
     * Returns the line of String
     * 
     * @return The line of String
     */
    public String getLine();
}
