package com.shizuku.mail.test;

import com.shizuku.mail.MailServer;

public class SMS {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        /**
         * Start SMS
         */
        MailServer.getSingletonInstance().startServer();
    }

}
