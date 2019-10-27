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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * About mail operations.
 * 
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/26 19:11:00 $
 * 
 */
public class MailImpl implements Mail {

    /**
     * The mail path store in server
     */
    private String      path  = null;
    /**
     * Mail name
     */
    private String      name  = null;
    /**
     * Mail size
     */
    private long        size  = 0;
    /**
     * Current mail state
     */
    private boolean     state = false;
    private MailMessage mm    = null;

    /**
     * 
     * @param path Mail file path
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public MailImpl(String path) throws FileNotFoundException, IOException {
        this.path = path;
        File f = new File(path);
        if (!f.exists()) {
            this.name = null;
            this.size = 0;
            this.mm = null;
        } else {
            this.size = f.length();
            this.name = f.getName();
            this.mm = new MailMessage();
        }
    }

    /**
     * @see org.jpxx.mail.Message.Mail#getName()
     * @return mail name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @see org.jpxx.mail.Message.Mail#setName(String name)
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @see org.jpxx.mail.Message.Mail#getSize()
     * @return message size
     */
    public long getSize() {
        return this.size;
    }

    /**
     * @see org.jpxx.mail.Message.Mail#setSize(long size)
     * @param size
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @see org.jpxx.mail.Message.Mail#getState()
     * @return current mail's state.
     */
    public boolean getState() {
        return this.state;
    }

    /**
     * @see org.jpxx.mail.Message.Mail#setState(boolean DELETE)
     * @param DELETE If DELETE == true, delete it; else, normally
     */
    public void setState(boolean DELETE) {
        this.state = DELETE;
    }

    /**
     * @see org.jpxx.mail.Message.Mail#getPath()
     * @return Mail absolute path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @see org.jpxx.mail.Message.Mail#setPath(String path)
     * @param path Mail absolute path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @see org.jpxx.mail.Message.Mail#getMimeMessage()
     * @return an object of MailMessage
     * @throws org.jpxx.mail.Message.MessageException
     */
    public MailMessage getMimeMessage() throws MessageException {
        return this.mm;
    }
}
