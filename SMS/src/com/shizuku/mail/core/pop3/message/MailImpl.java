package com.shizuku.mail.core.pop3.message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * About mail operations.
 */
public class MailImpl implements Mail {

    /**
     * The mail path store in server
     */
    private String      path  = null;
    /**
     * Mail name
     */
    private String      name  = null;
    /**
     * Mail size
     */
    private long        size  = 0;
    /**
     * Current mail state
     */
    private boolean     state = false;
    private MailMessage mm    = null;

    /**
     * 
     * @param path Mail file path
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public MailImpl(String path) throws FileNotFoundException, IOException {
        this.path = path;
        File f = new File(path);
        if (!f.exists()) {
            this.name = null;
            this.size = 0;
            this.mm = null;
        } else {
            this.size = f.length();
            this.name = f.getName();
            this.mm = new MailMessage();
        }
    }

    /**
     * @see com.shizuku.mail.Message.Mail#getName()
     * @return mail name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @see com.shizuku.mail.Message.Mail#setName(String name)
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @see com.shizuku.mail.Message.Mail#getSize()
     * @return message size
     */
    public long getSize() {
        return this.size;
    }

    /**
     * @see com.shizuku.mail.Message.Mail#setSize(long size)
     * @param size
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @see com.shizuku.mail.Message.Mail#getState()
     * @return current mail's state.
     */
    public boolean getState() {
        return this.state;
    }

    /**
     * @see com.shizuku.mail.Message.Mail#setState(boolean DELETE)
     * @param DELETE If DELETE == true, delete it; else, normally
     */
    public void setState(boolean DELETE) {
        this.state = DELETE;
    }

    /**
     * @see com.shizuku.mail.Message.Mail#getPath()
     * @return Mail absolute path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @see com.shizuku.mail.Message.Mail#setPath(String path)
     * @param path Mail absolute path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @see com.shizuku.mail.Message.Mail#getMimeMessage()
     * @return an object of MailMessage
     * @throws com.shizuku.mail.Message.MessageException
     */
    public MailMessage getMimeMessage() throws MessageException {
        return this.mm;
    }
}
