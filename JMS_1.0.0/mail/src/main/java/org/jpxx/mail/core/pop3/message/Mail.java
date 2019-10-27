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
package org.jpxx.mail.core.pop3.message;

/**
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/29 13:00:00 $
 * 
 */
public interface Mail {

    /**
     * Get mail name
     * @return current mail's name
     */
    public String getName();

    /**
     * Set mail name
     * @param name mail's name
     */
    public void setName(String name);

    /**
     * Get mail's size
     * @return mail's size
     */
    public long getSize();

    /**
     * Set mail's size
     * @param size mail's size
     */
    public void setSize(long size);

    /**
     * Returns current mail's state. If true, delete; else, normally.
     * @return current mail's state.
     */
    public boolean getState();

    /**
     * Set current mail's state
     * @param DELETE If DELETE == true, delete; else, normally
     */
    public void setState(boolean DELETE);

    /**
     * Current mail absolute path
     * @return Mail absolute path
     */
    public String getPath();

    /**
     * Set current mail absolute path
     * @param path Mail absolute path
     */
    public void setPath(String path);

    /**
     * Returns the MailMessage stored in this message
     *
     * @return the MailMessage that this Mail object wraps
     * @throws org.jpxx.mail.Message.MessageException
     */
    public MailMessage getMimeMessage() throws MessageException;
}
