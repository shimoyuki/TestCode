package com.shizuku.mail.core;

/**
 * Handle a command
 */
public interface Command {

    /**
     * Handle command
     * 
     * @param session
     *            The session between Server and Client.
     */
    public void onCommand(Session session);
}
