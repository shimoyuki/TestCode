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
 * Catch the error int SMTP Protocol.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/25 12:32:00 $
 * 
 */
public class SmtpProtocolException extends Exception {

    private static final long serialVersionUID = -3911352827335942264L;

    /**
     * 
     * @param message
     */
    public SmtpProtocolException(String message) {
        super(message);
    }
}
