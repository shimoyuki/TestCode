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
package org.jpxx.configuration;

import org.apache.log4j.Logger;

/**
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.configuration.AbstractConfig.java $, $Date:
 *          2009-4-29 $
 * 
 */
public abstract class AbstractConfig implements Config {

    protected String path;
    protected Logger log = Logger.getLogger(getClass());

    public AbstractConfig(String path) {
        this.path = path;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }
}