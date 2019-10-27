package com.shizuku.mail.core;

import java.util.HashMap;

/**
 * This interface define some other service operations. All sevices store in a
 * hash map.
 */
public interface ServerService {

    /**
     * Returns current services of server.
     * 
     * @return a hash map of services.
     */
    public HashMap<String, Object> getService();

    /**
     * Add a new services to hash map. If it already exists, overlay the old
     * service.
     * 
     * @param name
     *            service name.
     * @param service
     *            The service Object.
     */
    public void addService(String name, Object service);
}
