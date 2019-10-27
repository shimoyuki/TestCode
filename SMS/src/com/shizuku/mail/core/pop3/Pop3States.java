package com.shizuku.mail.core.pop3;

/**
 * Pop3 protocol Pop3States.
 */
public interface Pop3States {

    /**
     * The AUTHORIZATION Pop3States.
     * The client must now identify and authenticate itself to the POP3 server.
     * Two possible mechanisms for doing this are described in this document,
     * the USER and PASS command combination and the APOP command.
     */
    public final static int    AUTHENTICATION      = 1;
    public final static int    AUTHENTICATION_QUIT = 2;
    /**
     * Once the client has successfully identified itself to the POP3 server
     * and the POP3 server has locked and opened the appropriate maildrop,
     * the POP3 session is now in the TRANSACTION state.
     */
    public final static int    TRANSACTION         = 3;
    /**
     * The UPDATE Pop3States
     */
    public final static int    UPDATE              = 4;
    /**
     * There are currently two status
     * indicators: positive ("+OK") and negative ("-ERR"). 
     * Servers MUST send the "+OK" and "-ERR" in upper case.
     */
    public final static String OK                  = "+OK";
    public final static String ERR                 = "-ERR";
}
