package com.shizuku.mail.test;

import org.xbill.DNS.TextParseException;

import com.shizuku.mail.MailServer;
import com.shizuku.mail.SysConfig;
import com.shizuku.mail.dns.DNS;
import com.shizuku.mail.domain.DomainHandler;
import com.shizuku.mail.user.UserHandler;

public class SendMailTest {

    public void testSMTP() {

    }

    public static void main(String args[]) {
        DomainHandler dc = new DomainHandler();
        dc.addDomain("shizuku.com");
    	UserHandler uh = new UserHandler();
        uh.addUser("from", "from", "shizuku.com");
        uh.addUser("to", "to", "shizuku.com");
        MailServer ms = MailServer.getSingletonInstance();
        ms.startServer();
        //new SysConfig().setMemory("db",  "jdbc:mysql://localhost:3306/SMS", "root", "kagari8243");
        SendMail.send("from@shizuku.com","from","from@shizuku.com","to@shizuku.com","127.0.0.1","Test Mail", "Content.");
        //SendMail.send("from@shizuku.com", "from", "from@shizuku.com", "shimoyuki@sina.com", "127.0.0.1", "A mail from Shizuku Mail Server", "Test content");
        ms.stopServer();
    }
}
