package com.shizuku.mail.core.pop3.message;


public interface Mail {

    /**
     * Get mail name
     * @return current mail's name
     */
    public String getName();

    /**
     * Set mail name
     * @param name mail's name
     */
    public void setName(String name);

    /**
     * Get mail's size
     * @return mail's size
     */
    public long getSize();

    /**
     * Set mail's size
     * @param size mail's size
     */
    public void setSize(long size);

    /**
     * Returns current mail's state. If true, delete; else, normally.
     * @return current mail's state.
     */
    public boolean getState();

    /**
     * Set current mail's state
     * @param DELETE If DELETE == true, delete; else, normally
     */
    public void setState(boolean DELETE);

    /**
     * Current mail absolute path
     * @return Mail absolute path
     */
    public String getPath();

    /**
     * Set current mail absolute path
     * @param path Mail absolute path
     */
    public void setPath(String path);

    /**
     * Returns the MailMessage stored in this message
     *
     * @return the MailMessage that this Mail object wraps
     * @throws com.shizuku.mail.Message.MessageException
     */
    public MailMessage getMimeMessage() throws MessageException;
}
