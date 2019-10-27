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

/**
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/30 09:35:00 $
 */
public interface ServerState {

    /**
     * Ready state. When Server say "hello", Server is ready now.
     */
    public final static int READY = 0;
}
