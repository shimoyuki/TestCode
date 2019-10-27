package com.shizuku.mail.core.pop3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.shizuku.mail.SysConfig;
import com.shizuku.mail.core.pop3.message.Mail;
import com.shizuku.mail.core.pop3.message.MailImpl;
import com.shizuku.mail.core.pop3.message.MessageException;
import com.shizuku.mail.util.EmailAddress;

/**
 * Managing an e-mail Repository.
 */
public class MailRepository {

    private Pop3User        user         = Pop3UserMonitor.getSingletonInstance();
    private EmailAddress    emailAddress = null;
    private SysConfig       sc           = new SysConfig();
    private ArrayList<Mail> mailList     = null;

    /**
     * 
     * @param emailAddress
     */
    public MailRepository(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Mark the mail state.
     * @param num the mail id
     * @param isDelete If isDelete==true, it will be deleted when quit.
     * @throws java.lang.IndexOutOfBoundsException
     */
    public void setDelete(int num, boolean isDelete) throws IndexOutOfBoundsException {
        ArrayList<Mail> list = this.mailList;
        if (num < 1 || num > list.size()) {
            throw new IndexOutOfBoundsException();
        }

        Mail m = list.get(num - 1);
        m.setState(isDelete);
        list.set(num - 1, m);
        this.setMailList(list);
    }

    /**
     * Remove all the mails which were marked to delete.
     *  
     * @throws java.io.IOException
     */
    public void remove() throws IOException {
        String userDir = sc.getHomeDir(emailAddress.getUserName(), emailAddress.getDomain());
        List<Mail> list = list();
        for (int i = 0; i < list.size(); i++) {
            Mail m = list.get(i);
            if (m.getState()) {
                String path = userDir + m.getName();
                File f = new File(path);
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }

    /**
     * The POP3 server sends the entire message.
     * 
     * @param pw The object of PrintWriter
     * @param m This is the mail that will be sent to client.
     * @throws java.io.IOException
     * @throws com.shizuku.mail.Message.MessageException
     */
    public void write(PrintWriter pw, Mail m) throws IOException, MessageException {
        List<String> contents = m.getMimeMessage().getMailMessageContent(
            new FileInputStream(m.getPath()));
        for (int i = 0; i < contents.size(); i++) {
            pw.println(contents.get(i).toString());
            pw.flush();
        }
    }

    /**Write <b>n</b> line message of the mail. 
     * Used by the command - TOP [number] [line].
     * Note that if the number of lines requested by the POP3
     * client is greater than the number of lines in the
     * body, then the POP3 server sends the entire message.
     * 
     * @param pw The object of PrintWriter
     * @param m an Mail object. This is the mail that will be sent to client.
     * @param n line message of the mail
     * @throws java.io.IOException
     * @throws com.shizuku.mail.Message.MessageException
     */
    public void write(PrintWriter pw, Mail m, int n) throws IOException, MessageException {
        List<String> contents = m.getMimeMessage().getMailMessageContent(
            new FileInputStream(m.getPath()), n);
        for (int i = 0; i < contents.size(); i++) {
            pw.println(contents.get(i).toString());
            pw.flush();
        }
    }

    /**
     * Get all mail files of current user
     * @return a list of mail files
     */
    public List<Mail> list() {
        if (mailList == null) {
            mailList = this.getMailList();
        }
        return mailList;
    }

    /**
     * Lock this box so no one else can use it. 
     * This way you don't have two people getting and deleting mail from 
     * the same user at the same time.
     */
    public void lockMailRepository() {
        user.lockUser(emailAddress);
    }

    /**
     * Unlock this box so it can be used again.
     */
    public void unlockMailRepository() {
        user.unlockUser(emailAddress);
    }

    /**
     * Is user is lock now. Return current user's state.
     * @return Current user's state. When user is login, lock it. This user
     * can't login again before logout.
     */
    public boolean isLock() {
        return user.isOnline(emailAddress);
    }

    /**
     * Set current user's mail list.
     * @param mailList current user's mails list
     */
    public void setMailList(ArrayList<Mail> mailList) {
        this.mailList = mailList;
    }

    /**
     * List all mails of current user's.
     * @return An list of current user's. The Objects of 
     * com.shizuku.mail.Pop3.MailRepository.Mail are saving in this list.
     */
    private ArrayList<Mail> getMailList() {
        String userDir = sc.getHomeDir(emailAddress.getUserName(), emailAddress.getDomain());
        File dir = new File(userDir);
        // Read all mail files
        File files[] = dir.listFiles();
        mailList = new ArrayList<Mail>();
        for (int i = 0; i < files.length; i++) {
            try {
                Mail m = new MailImpl(files[i].getAbsolutePath());
                mailList.add(m);
            } catch (Exception e) {
                // Ignore
            }
        }
        return mailList;
    }
}
