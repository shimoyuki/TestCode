package com.shizuku.mail.core.smtp;

public interface SmtpCommands {

    public final static int EHLO = 1;
    public final static int HELO = 2;
    public final static int AUTH = 3;
    public final static int MAIL = 4;
    public final static int RCPT = 5;
    public final static int DATA = 6;
    public final static int RSET = 7;
    public final static int VRFY = 8;
    public final static int EXPN = 9;
    public final static int HELP = 10;
    public final static int NOOP = 11;
    public final static int QUIT = 12;
}
