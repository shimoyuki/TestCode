package com.shizuku.mail.core;

import java.util.HashMap;

public class MailServerService implements ServerService {

    private HashMap<String, Object> map = null;

    public MailServerService() {
        /**
         * Initials an empty <tt>HashMap</tt> with the specified initial
         * capacity and load factor.
         */
        map = new HashMap<String, Object>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shizuku.mail.core.ServerService#addService(java.lang.String,
     * java.lang.Object)
     */
    public void addService(String name, Object service) {
        map.put(name, service);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shizuku.mail.core.ServerService#getService()
     */
    public HashMap<String, Object> getService() {
        return this.map;
    }

}
