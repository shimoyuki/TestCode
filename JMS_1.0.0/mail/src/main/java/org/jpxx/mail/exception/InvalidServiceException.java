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
 * <code>InvalidServiceException</code> is the subclass of 
 * <code>Exception</code>. When a service is not found in current 
 * Services Hash Map, throws this exception.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/28 8:56:00 $
 * 
 */
public class InvalidServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param error
     */
    public InvalidServiceException(String error) {
        super(error);
    }
}
