package com.evance.pop3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.evance.smtp.Constants;
import com.evance.smtp.SMTPSession;

public class Pop3Server extends Thread{
	public void run(){
		try {
			ServerSocket server = new ServerSocket(Constants.PORT_POP3);
			while(true){
				Socket socket = server.accept();
				POP3Session session = new POP3Session(socket) ;
				session.start() ;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Pop3Server ps = new  Pop3Server();
		ps.start();
	}
}
