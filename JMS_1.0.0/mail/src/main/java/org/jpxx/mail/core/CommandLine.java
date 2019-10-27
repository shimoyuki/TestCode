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
package org.jpxx.mail.core;

import org.jpxx.mail.exception.ArgumentException;

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
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version $ org.jpxx.mail.core.CommandLine.java $, $Date: 2009-4-29 $
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
     * @throws org.jpxx.mail.exception.ArgumentException
     */
    public String getArgument() throws ArgumentException;

    /**
     * Returns the argument <i>num</i>. If num is not efficient, return null
     * object.
     * 
     * @param num
     *            given argument number
     * @return the given command argument
     * @throws org.jpxx.mail.exception.ArgumentException
     */
    public String getArgument(int num) throws ArgumentException;

    /**
     * Returns the next argument.
     * 
     * @return the given command argument
     * @throws org.jpxx.mail.exception.ArgumentException
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
