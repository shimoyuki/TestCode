package com.shizuku.mail.core;

/**
 * This is an interface of SMTP,POP3 Command Handler.
 * SMTP has 10 commands. Specification in RFC 2821.<br><br>
 * EHLO/HELO: These commands are used to identify the SMTP client to the SMTP 
 * server. The argument field contains the fully-qualified domain name of the 
 * SMTP client if one is available. The EHLO keyword value associated with "AUTH". 
 * <br>
 * MAIL: This command is used to initiate a mail transaction in which the mail 
 * data is delivered to an SMTP server which may, in turn, 
 * deliver it to one or more mailboxes or pass it on to another system 
 * (possibly using SMTP). 
 * The argument field contains a reverse-path and may contain optional parameters.
 * <br>
 * RCPT:This command is used to identify an individual recipient of the mail data; 
 * multiple recipients are specified by multiple use of this command. 
 * The argument field contains a forward-path and may contain optional parameters.
 * <br>
 * DATA: The receiver normally sends a 354 response to DATA, 
 * and then treats the lines (strings ending in <CRLF> sequences) 
 * following the command as mail data from the sender.
 * <br>
 * RSET: This command specifies that the current mail transaction will be aborted. 
 * Any stored sender, recipients, and mail data MUST be discarded, 
 * and all buffers and state tables cleared.
 * <br>
 * VRFY: This command asks the receiver to confirm that the argument 
 * identifies a user or mailbox. 
 * If it is a user name, information is returned.
 * <br>
 * EXPN: This command asks the receiver to confirm that the argument 
 * identifies a mailing list, and if so, to return the membership of that list. 
 * If the command is successful, a reply is returned containing information.
 * <br>
 * HELP: This command causes the server to send helpful information to the client. 
 * The command MAY take an argument (e.g., any command name) 
 * and return more specific information as a response.
 * <br>
 * NOOP: This command does not affect any parameters or previously 
 * entered commands. It specifies no action other than that the receiver 
 * send an OK reply.
 * <br>
 * QUIT: This command specifies that the receiver MUST send an OK reply, 
 * and then close the transmission channel.
 * <br>
 * <br>
 * SMTP Service Extension for Authentication in RFC 4954<br>
 * <i>AUTH<i>: http://tools.ietf.org/html/rfc4954
 * <br><br><br><br>
 * 
 * POP3 has 12 commands. Specification in RFC 2821.<br><br>
 * APOP
 * <br>
 * DELE
 * <br>
 * LIST
 * <br>
 * NOOP
 * <br>
 * PASS
 * <br>
 * QUIT
 * <br>
 * RETR
 * <br>
 * RSET
 * <br>
 * STAT
 * <br>
 * TOP
 * <br>
 * UIDL
 * <br>
 * USER
 * 
 * <br><br><br><br>
 * Resource:<br>
 * Simple Authentication and Security Layer (SASL): RFC 4422<br>
 * IMAP/POP AUTHorize Extension for Simple Challenge/Response: RFC 2195<br>
 * Simple Mail Transfer Protocol (SMTP): RFC 2821<br>
 * SMTP Service Extension for Authentication: RFC 4954<br>
 * SMTP and LMTP Transmission Types Registration (with ESMTPA): RFC 3848<br>
 * Message Submission for Mail: RFC 4409<br>
 * Post Office Protocol - Version 3 RFC 1939<br>
 * 
 * 
 * 
 * 
 */
public interface CommandsHandler {
    /**
     * Do with the client request commands.
     * 
     * @param name Command name.
     * @param session The session between server and client.
     */
    public void doCommand(String name, Session session);
}
