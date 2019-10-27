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

/**
 * 
 * This interface define some config operations
 * 
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org) *
 * @version 1.0.0 $ org.jpxx.configuration.Config.java $, $Date: 2009-4-29 $
 * 
 */
public interface Config {

    /**
     * Returns the value to which the specified key is mapped.
     * 
     * @param key
     *            a specific key
     * @return the value to which the key is mapped; <code>null</code> if the
     *         key is not mapped to any value.
     */
    public String get(String key);

    /**
     * 
     * Maps the specified <code>key</code> to the specified <code>value</code>.
     * Neither the key nor the value can be <code>null</code>.
     * 
     * @param key
     *            a specific key
     * @param value
     * @return the previous value of the specified key
     */
    public String put(String key, String value);

    /**
     * Removes the key (and its corresponding value).
     * 
     * @param key
     *            a specific key
     * @return true if delete successfully.
     */
    public boolean remove(String key);

    /**
     * Returns true if this key is mapped.
     * 
     * @param key
     *            a specific key
     * @return true if the key is existing, otherwise return false.
     */
    public boolean find(String key);
}
