package com.evance.pop3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.evance.smtp.Base64;
import com.evance.smtp.Constants;

public class POP3Session extends Thread {
	
	private static final String CMD_USER = "USER";
	private static final String CMD_PASS = "PASS";
	private static final String CMD_STAT = "STAT";
	private static final String CMD_LIST = "LIST";
	private static final String CMD_RETR = "RETR";
	private static final String CMD_DELE = "DELE";
	private static final String CMD_QUIT = "QUIT";
	private static final String CMD_UIDL = "UIDL";
	
	static String str_uName;
	static String str_uPassword;
	
	private Socket socket ;
	private BufferedReader br ;
	private PrintWriter pw ;
	private ArrayList list;

	
	public POP3Session(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}
	
	public void run(){
		try {
			socket.setSoTimeout(Constants.SOCKET_TIMEOUT);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream()) ;
			
			//every connection, send welcome message ...
			sendWelcomeMessage();		
			
			String line = null;
			while(parseCommand(readCommandLine())){
				;//				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
//			writeMessage(e.toString());
		}
		finally{
			try {
				br.close();
				pw.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 执行接受邮件的整个流程
	 * */
	private boolean parseCommand(String command){        
        boolean returnValue = true;        
        if (command == null) {
            return false;
        }
        String argument = null;
        int spaceIndex = command.indexOf(" ");
        if (spaceIndex > 0) {
            argument = command.substring(spaceIndex + 1);
            command = command.substring(0, spaceIndex);
        }
        
//        System.out.println(spaceIndex);
        System.out.println(command+"ccc");
        System.out.println(argument+"aaa");
        
        if (command.equalsIgnoreCase(CMD_USER)) {
        	doUSER(argument);
        } 
        else if (command.equalsIgnoreCase(CMD_PASS)) {
            doPASS(argument);
        }
        else if (command.equalsIgnoreCase(CMD_STAT)) {
            doSTAT(argument);
        }
        else if(command.equalsIgnoreCase(CMD_UIDL)){
        	doUIDL(argument);
        }
        else if (command.equalsIgnoreCase(CMD_LIST)) {
            doLIST(argument);
        }
        else if (command.equalsIgnoreCase(CMD_RETR)) {
            doRETR(argument);
        }
        else if (command.equalsIgnoreCase(CMD_DELE)) {
            doDELE(argument);
        }else if(command.equalsIgnoreCase(CMD_QUIT)){
        	doQUIT(argument);
        }
        return returnValue;
	}
	
	

	private void doQUIT(String argument) {
		// TODO 自动生成方法存根
		writeMessage("+OK core mail");	
	}

	private void doDELE(String argument) {
		// TODO 自动生成方法存根
		int n = Integer.parseInt(argument);
		if(n>-1){
			writeMessage("+OK core mail");
			File file = new File("D:/mail/"+str_uName);
			File[] files = file.listFiles();
			files[n].delete();
		}
	}

	private void doRETR(String argument) {
		// TODO 自动生成方法存根else{
			int n = Integer.parseInt(argument);
			System.out.println(n);
			File file = new File("D:/mail/"+str_uName);
			File[] files = file.listFiles();
			writeMessage("+OK"+" "+files[n-1].length()+" "+"octets");
			try {
//				FileInputStream fis = new FileInputStream(files[n]);
				FileReader fr = new FileReader(files[n-1]);
				BufferedReader br = new BufferedReader(fr);
				String str = null;
//				int x = 0;
				while((str = br.readLine())!=null){
					writeMessage(str);
//					System.out.println(str);
//					System.out.println(x);
//					x++;
				}
				writeMessage(".");
				fr.close();
			} catch (FileNotFoundException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
	}

	private void doLIST(String argument) {
		// TODO 自动生成方法存根
		list = new ArrayList();
		File file = new File("D:/mail/"+str_uName);
		File[] files = file.listFiles();
		long sum =0;
		for(int i =0;i<files.length;i++){
			list.add(files[i].getName());
			sum+=files[i].length();
			
		}
		writeMessage("+OK"+" "+list.size()+" "+sum);
		for(int i=0;i<files.length;i++){
			writeMessage((i+1)+" "+files[i].length());
		}
		writeMessage(".");
		
	}
	
	private void doUIDL(String argument) {
		// TODO Auto-generated method stub
		int n ;
		if(argument != null){
			n = Integer.parseInt(argument);
		}else
			n = 0;
		if(n>0){
			System.out.println(n);
			File file = new File("D:/mail/"+str_uName);
			File[] files = file.listFiles();
			writeMessage("+OK"+" "+files[n-1].getName());
		}else
			writeMessage("-ERR"+" "+"NO MESSAGE");
	}

	private void doSTAT(String argument) {
		list = new ArrayList();
		File file = new File("D:/mail/"+str_uName);
		File[] files = file.listFiles();
		long sum =0;
		if(files!=null){
			for(int i =0;i<files.length;i++){
				list.add(files[i].getName());
				sum+=files[i].length();
				}
			}
		writeMessage("+OK"+" "+list.size()+" "+sum);
		
	}

	private void doPASS(String argument) {
		// TODO Auto-generated method stub
		GetUserInfo gui = new GetUserInfo();
			str_uPassword = argument;
//			str_uPassword = new String(Base64.decode(str_uPassword));
			if(gui.getUserInfor()){
				list = new ArrayList();
				File file = new File("D:/mail/"+str_uName);
				if(!file.exists()){
					file.mkdirs();
				}
				File[] files = file.listFiles();
				long sum =0;
				for(int i =0;i<files.length;i++){
					list.add(files[i].getName());
					sum+=files[i].length();
					
				}
				writeMessage("+OK"+" "+list.size()+" "+"message(s)"+"["+sum+"byte(s)"+"]");
				}
	}

	private void doUSER(String argument) {
		// TODO Auto-generated method stub
			str_uName = argument;
//			str_uName = new String(Base64.decode(str_uName));
			//System.out.println(argument);
			writeMessage("+OK core mail");
	}

	/**
	 * 
	 * @param message
	 */
	private void writeMessage(String message){
		pw.println(message);
		pw.flush();	
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	final String readCommandLine() throws IOException {
        for (;;) {
            String commandLine = br.readLine();
            if (commandLine != null) {
                commandLine = commandLine.trim();
            }
            return commandLine;
        }
    }
	
	
	/**
	 * every connection, send welcome message ...
	 * @return
	 */
	public boolean sendWelcomeMessage(){
		StringBuffer sb = new StringBuffer() ;
		try {
		sb.append("+OK")
		  .append(" ")
          .append(InetAddress.getLocalHost())
          .append(" ")
          .append("WELCOME")
          .append("(")
          .append(Constants.VERSION_MAILSERVER)
          .append(")")
          .append(" ")
          .append(Constants.SERVER_NAME);	
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		writeMessage(sb.toString()) ;
		return true;
	}
}
