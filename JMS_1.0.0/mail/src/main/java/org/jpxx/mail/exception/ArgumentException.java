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
package org.jpxx.mail.exception;

/**
 * <code>ArgumentException</code> is the subclass of <code>Exception</code>.
 * When the arguments are not found or have bad format, throws this exception.
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.mail.exception.ArgumentException.java $, $Date: 2009-4-29
 *          $
 * @since JMS 0.0.3
 */
public class ArgumentException extends Exception {

    private static final long serialVersionUID = 7681367362395495895L;
    private String            message          = null;

    public ArgumentException() {
        super();
    }

    public ArgumentException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Returns a short description of this throwable.
     * 
     * @return a string representation of this throwable.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(256);
        sb.append(message);
        sb.append("The command line includes command name and zero or more "
                  + "arguments, which must be separated by <SP>. ");
        sb.append(super.toString());
        return sb.toString();
    }
}
