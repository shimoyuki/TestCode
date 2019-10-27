package com.shizuku.mail.core.pop3.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * This class represents a simple MIME style email message. 
 */
public class MailMessage {

    private MailMessageHeaders mmh = null;

    public MailMessage() {
    }

    /**
     * @param is the message input stream
     * @return this message's header 
     * @throws com.shizuku.mail.Message.MessageException
     * @throws java.io.IOException
     */
    public MailMessageHeaders getMailMessageHeaders(InputStream is) throws MessageException,
                                                                   IOException {
        if (mmh == null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            mmh = new MailMessageHeaders(br);
            is.close();
        }
        return mmh;
    }

    /**
     * Read message content 
     * @param is the message input stream
     * @return the message content 
     * @throws java.io.IOException
     */
    public List<String> getMailMessageContent(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> lines = new ArrayList<String>();
        String line = br.readLine();
        while (line != null) {
            lines.add(line);
            line = br.readLine();
        }
        is.close();
        return lines;
    }

    /**
     * is the message input stream
     * 
     * @param is
     * @param i
     * @return the message content
     * @throws com.shizuku.mail.Message.MessageException
     * @throws java.io.IOException
     */
    public List<String> getMailMessageContent(InputStream is, int i) throws MessageException,
                                                                    IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        mmh = new MailMessageHeaders(br);

        List<String> lines = new ArrayList<String>();
        for (Enumeration<String> e = mmh.getAllHeaderLines(); e.hasMoreElements();) {
            lines.add(e.nextElement());
        }

        lines.add("");

        String line = br.readLine();
        while (line != null && i > 0) {
            lines.add(line);
            line = br.readLine();
            i--;
        }
        is.close();
        return lines;
    }
}
