package com.evance.smtp;

import java.io.IOException;
import java.net.*;


public class SmtpServer extends Thread{
	
		/**
		 * @param args
		 */
//		public static void main(String[] args) {
//			// TODO Auto-generated method stub
//			MailServer ms = new MailServer();
//			ms.start();
//		}
		
		public void run(){
			try {
				ServerSocket server = new ServerSocket(Constants.PORT_SMTP);
				while(true){
					Socket socket = server.accept();
					SMTPSession session = new SMTPSession(socket) ;
					session.start() ;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
