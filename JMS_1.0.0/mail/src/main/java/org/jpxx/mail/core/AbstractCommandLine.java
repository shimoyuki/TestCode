/****************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.     *
 *                                                              *
 * Copyright 2008 Jun Li(SiChuan University, the School of      *
 * Software Engineering). All rights reserved.                  *
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
package org.jpxx.mail.core;

import org.jpxx.mail.exception.ArgumentException;

/**
 * <tt>AbstractCommandLine</tt> is an abstract class, which implements some 
 * functions of the interface <tt>CommandLine</tt>.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.3 $, $Date: 2008/05/20 $
 * @since JMS 0.0.3
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
     * @throws org.jpxx.mail.Exception.ArgumentException
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
     * @since JMS 0.0.1
     * @param num 0 - i
     * @return the argument
     * @throws org.jpxx.mail.Exception.ArgumentException
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
     * @since JMS 0.0.3
     * @return the argument. If no argument, the null object will be returned. 
     * @throws org.jpxx.mail.Exception.ArgumentException
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
