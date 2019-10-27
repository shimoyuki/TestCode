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
package org.jpxx.mail.exception;

/**
 * Signals that an email address is invalid .
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.mail.exception.InvalidEmailAddressException.java $,
 *          $Date: 2009-4-29 $
 * 
 */
public class InvalidEmailAddressException extends Exception {

    private static final long serialVersionUID = -7273172411454239531L;

    public InvalidEmailAddressException() {
        super();
    }

    public InvalidEmailAddressException(String message) {
        super(message);
    }
}
