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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author Jun Li lijun@jpxx.org (http://www.jpxx.org)
 * @version 1.0.0 $ org.jpxx.configuration.XmlConfig.java $, $Date: 2009-4-29 $
 * 
 */
public class XmlConfig extends AbstractConfig {

    private final static String CONFIG_LIST = "ConfigList";
    private final static String CONFIG      = "Config";
    private final static String KEY         = "key";
    private final static String VALUE       = "value";
    private final static String CHAR_SET    = "UTF-8";
    private Document            doc;

    public XmlConfig(String path) {
        super(path);

        File file = new File(path);

        try {
            SAXBuilder builder = new SAXBuilder(false);
            doc = builder.build(file);
        } catch (JDOMException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.info(file.getAbsolutePath() + " can't be found! System will create it!");
            create();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpxx.configuration.Config#find(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public boolean find(String key) {
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren(CONFIG);
        for (Iterator<Element> i = list.iterator(); i.hasNext();) {
            Element e = (Element) i.next();
            if (e.getChild(KEY).getText().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpxx.configuration.Config#get(java.lang.String)
     */
    public String get(String key) {
        CDATA c = getCDATA(key);
        return c == null ? null : c.getTextTrim();
    }

    @SuppressWarnings("unchecked")
    private CDATA getCDATA(String key) {
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren(CONFIG);
        for (Iterator<Element> i = list.iterator(); i.hasNext();) {
            Element e = i.next();
            if (e.getChild(KEY).getText().equals(key)) {
                CDATA c = new CDATA(e.getChild(VALUE).getText());
                return c;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpxx.configuration.Config#put(java.lang.String,
     * java.lang.String)
     */
    public String put(String key, String value) {
        if (this.find(key)) {
            return this.edit(key, value);
        }

        Element root = doc.getRootElement();
        Element _name = new Element(KEY);
        _name.setText(key);
        Element _value = new Element(VALUE);
        CDATA c = new CDATA(value);
        _value.setContent(c);

        Element config = new Element(CONFIG);
        config.addContent(_name);
        config.addContent(_value);

        root.addContent(config);
        return this.save() ? value : null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jpxx.configuration.Config#remove(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public boolean remove(String key) {
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren(CONFIG);
        for (Iterator<Element> i = list.iterator(); i.hasNext();) {
            Element e = (Element) i.next();
            if (e.getChild(KEY).getText().equals(key)) {
                root.removeContent(e);
                this.save();
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private String edit(String key, String value) {
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren(CONFIG);
        for (Iterator<Element> i = list.iterator(); i.hasNext();) {
            Element e = i.next();

            if (e.getChild(KEY).getText().equals(key)) {
                String temp = e.getChild(VALUE).getText();
                e.getChild(VALUE).setText(value);
                return this.save() ? temp : null;
            }
        }
        return null;
    }

    private boolean save() {
        XMLOutputter outputter = new XMLOutputter();
        try {
            FileWriter writer = new FileWriter(path);
            Format format = Format.getPrettyFormat();
            format.setEncoding(CHAR_SET);
            format.setIndent("        ");

            outputter.setFormat(format);
            outputter.output(doc, writer);
            writer.close();

            return true;
        } catch (IOException e) {
            log.error("File writes error");
            return false;
        }
    }

    /**
     * Creates Xml config file
     */
    private void create() {

        File f = new File(path);

        // If config file exist, return.
        if (f.exists()) {
            return;
        }

        Element root = new Element(CONFIG_LIST);
        doc = new Document();
        doc.setContent(root);

        String filePath = f.getAbsolutePath();
        if (filePath.lastIndexOf(File.separator) != -1) {
            String dirPath = filePath.replace(filePath.substring(filePath
                .lastIndexOf(File.separator) + 1), "");

            try {
                f = new File(dirPath);
                if (!f.exists()) {
                    f.mkdirs();
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e);
            }
        }

        XMLOutputter outputter = new XMLOutputter();
        try {
            FileWriter writer = new FileWriter(filePath);
            Format format = Format.getPrettyFormat();
            format.setEncoding(CHAR_SET);
            outputter.setFormat(format);
            outputter.output(doc, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IO error. " + e);
        }
    }
}
