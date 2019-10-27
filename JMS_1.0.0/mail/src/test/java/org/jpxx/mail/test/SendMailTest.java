package org.jpxx.mail.test;

import org.jpxx.mail.MailServer;
import org.jpxx.mail.domain.DomainHandler;
import org.jpxx.mail.user.UserHandler;

public class SendMailTest {

    public void testSMTP() {

    }

    public static void main(String args[]) {
        DomainHandler dc = new DomainHandler();
        dc.addDomain("127.0.0.1");
        UserHandler uh = new UserHandler();
        uh.addUser("system", "system", "127.0.0.1");
        MailServer.getSingletonInstance().startServer();

        SendMail.send("john.lee@test.com", "Test", "Test by John Lee.");
    }
}
