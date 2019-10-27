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
 * The Header class stores a name/value pair to represent headers.
 *
 * @author Jun Li
 * @version $Revision: 0.0.1 $, $Date: 2008/04/29 10:03:00 $
 */
public class Header {

    /**
     * The name of the header.
     */
    protected String name;
    /**
     * The value of the header.
     */
    protected String value;

    /**
     * Construct a Header object.
     *
     * @param name	name of the header
     * @param value	value of the header
     */
    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name of this header.
     *
     * @return 		name of the header
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of this header.
     *
     * @return 		value of the header
     */
    public String getValue() {
        return value;
    }
}
