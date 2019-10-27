package com.evance.mailserver;

import com.evance.pop3.Pop3Server;
import com.evance.smtp.SmtpServer;

public class MailServer extends Thread{

	/**
	 * @param args
	 */
	public void run(){
			Pop3Server ps = new  Pop3Server();
			SmtpServer ss = new SmtpServer();
			ps.start();
			System.out.println("POP3Server is running!!!!!");
			ss.start();
			System.out.println("SMTPServer is running!!!!!");
	}
	
	public static void main(String[] args) {
		// TODO 自动生成方法存根
		MailServer ms = new MailServer();
		ms.start();
	}

}
