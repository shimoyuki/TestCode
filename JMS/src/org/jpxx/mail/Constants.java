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
package org.jpxx.mail;

import org.jpxx.mail.util.JMSUtils;

/**
 * <pre>
 * This class defines some constant variables, such as software name, version,
 * etc. Since version 0.0.3, it is assigned a new name to Constants. (The old
 * name is Constant)
 * </pre>
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.mail.Constants.java $, $Date: 2009-4-29 $
 * @since JMS 0.0.1
 */
public final class Constants {

    /**
     * Define the mail server name.
     */
    public final static String NAME             = "JMS";

    /**
     * Current version of JMS.
     */
    public final static String VERSION          = "0.0.3";

    /**
     * User home directory to store their mails.
     */
    public final static String HOME_DIR         = JMSUtils.getClasspath() + NAME
                                                  + "/MailStore/Domains/";

    /**
     * Temporary directory.
     */
    public final static String TEMP_DIR         = JMSUtils.getClasspath() + NAME + "/Temp/";

    /**
     * The Config File Path
     */
    public final static String CONFIG_FILE_PATH = JMSUtils.getClasspath() + NAME + "/Config/"
                                                  + "SystemConfig.xml";
    /**
     * The client connection Timeout(millisecond).
     */
    public final static int    DEFAULT_TIMEOUT  = 30000;
}
