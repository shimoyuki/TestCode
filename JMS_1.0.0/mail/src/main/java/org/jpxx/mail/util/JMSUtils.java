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
package org.jpxx.mail.util;

import java.util.UUID;

/**
 * <tt>JMSUtils</tt> defines some useful methods.
 * 
 * @author Jun Li
 * @version $Revision: 1.0.0 $, $Date: 2009/05/30 $
 * @since JMS 0.0.3
 */
public class JMSUtils {
    /**
     * Check a string is contained in the array or not.
     * @param array a string array
     * @param text a string
     * @return If text in the array then return true, otherwise return false.
     */
    public static boolean contains(String array[], String text) {
        if (array == null) {
            return false;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(text)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a unique ID. It will be used in POP3 protocol. (APOP command)
     * @return a unique ID
     */
    public static String getID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 
     * @return
     */
    public static String getClasspath() {
        String path = new JMSUtils().getClass().getResource("/").getPath();
        path = path.replace('\\', '/');
        if (!path.endsWith("/")) {
            path += "/";
        }
        return path;
    }
}
