package com.evance.smtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class SMTPSession  extends Thread {
	
	/**
	 * @author Evance.Mei(梅晓强)
	 * @since 2009.1.1-2009.1.2
	 * 
	 */

	private static final String CMD_EHLO = "EHLO";
	private static final String CMD_HELO = "HELO";
	private static final String CMD_MAIL = "MAIL";
	private static final String CMD_RCPT = "RCPT";
	private static final String CMD_DATA = "DATA";
	private static final String CMD_DOT = ".";
	private static final String CMD_AUTH = "AUTH";
	private final static String CMD_QUIT = "QUIT";
	static String str_uName;
	static String str_uPassword;
	static String str_IPAddress;
	static String str_fromName;
	static String str_toName;
	static boolean HELOf = false;
	static boolean EHLOf = false;
	static boolean AUTHf = false;
	static boolean RCPTf = false;
	
	private String str_data;
	private Socket socket ;
	private BufferedReader br ;
	private PrintWriter pw ;
	private String str;//use for building a folder which name is str;
	private File file;//use for building a folder
	private File fileText;
	
	public SMTPSession(Socket socket){
		this.socket = socket ;
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
//			e.printStackTrace();
			System.out.println(e.toString());
			writeMessage(e.toString());
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
	/*判断执行整个邮件发送的指令*/
	private boolean parseCommand(String command){        
        boolean returnValue = true;        
        if (command == null) {
            return false;
        }
//        if ((state.get(MESG_FAILED) == null) && (getLogger().isDebugEnabled())) {
//            getLogger().debug("Command received: " + command);
//        }
        String argument = null;
//        String argument1 = null;
        int spaceIndex = command.indexOf(" ");
        if (spaceIndex > 0) {
            argument = command.substring(spaceIndex + 1);
            command = command.substring(0, spaceIndex);
        }
        
//        System.out.println(spaceIndex);
        System.out.println(command+"CCCCCCCC");
        System.out.println(argument+"AAAAAAA");
        
        if (command.equalsIgnoreCase(CMD_HELO)) {
            doHELO(argument);
        } 
        else if (command.equalsIgnoreCase(CMD_EHLO)) {
            doEHLO(argument);
        }
        else if (command.equalsIgnoreCase(CMD_AUTH)&&(EHLOf||!HELOf)) {
            doAuth(argument);
        }
        else if ((command.equalsIgnoreCase(CMD_MAIL)&&AUTHf)||
        		(command.equalsIgnoreCase(CMD_MAIL)&&HELOf)) {
            doMail(argument);
        }
        else if ((command.equalsIgnoreCase(CMD_RCPT)&&AUTHf)||
        		(command.equalsIgnoreCase(CMD_RCPT)&&HELOf)) {
            doRcpt(argument);
        }
        else if ((command.equalsIgnoreCase(CMD_RCPT)&&RCPTf)&&
        		(command.equalsIgnoreCase(CMD_DATA)&&HELOf)||AUTHf) {
            doData(argument);
        }else if(command.equalsIgnoreCase(CMD_QUIT)){
        	doQuit(argument);
        }
        return returnValue;
	}
	/*执行QUIT
	 *并写回信息221 bye 
	 * 
	 */
	
	private void doQuit(String argument) {
		// TODO Auto-generated method stub
		writeMessage("221 bye");
		try {
			socket.close();
			br.close();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*执行DATA，先判断是否存在一个和接受人用户名一样的文件夹。
 * 若不存在则新建一个和接受人用户名一样的文件夹，然后在此文件夹下建
 * 立一个以当前时间为文件名的TXT文件来记录邮件内容。最后写回信息
 * 250 OK
 * 
 */
	private void doData(String argument) {
		// TODO Auto-generated method stub
		writeMessage("354 Send from Rising mail proxy");
			file = new File("D:/mail/"+str);
			if(!file.exists()){
				file.mkdirs();
			}
			
			try {
				String s = "D:/mail/"+str+"/"+new Date().getTime()+".txt";
				System.out.println(s);
				fileText = new File(s);
				if(!fileText.exists())
					fileText.createNewFile();
				PrintStream ps = new PrintStream(fileText);
				String str = null;
				while(!(str = br.readLine()).equalsIgnoreCase(".")){
					ps.println(str);
//					System.out.println("********************" + str);
				}
				ps.flush();
				ps.close();
				writeMessage("250 OK");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	/*
	 * RCPT 主要是严格按照协议，判断是否存在“<”,“>”,“@”,“:”还要
	 * 判断这些符号的先后顺序。虽然在客户端已经严格规范了这些符号
	 * 的顺序，但是最好还是加上这点。写回250 mail OK
	 * @param argument
	 */

	private void doRcpt(String argument) {
		// TODO Auto-generated method stub
		StringBuffer responseBuffer = new StringBuffer();
			int firstIndex = argument.indexOf("<");
			int lastIndex = argument.indexOf(">");
			int mIndex = argument.indexOf(":");
			int flagAIndex = argument.indexOf("@");
			if(firstIndex>0&&lastIndex>0&&mIndex>0&&flagAIndex>0&&lastIndex>firstIndex
					&&firstIndex<flagAIndex){
				str = argument.substring(firstIndex+1, flagAIndex);
					writeMessage("250 mail ok!");
				}
	}
	
//	private void doRcpt(String argument) {
//		// TODO Auto-generated method stub
//		RCPTf = true;
//		StringBuffer responseBuffer = new StringBuffer();
//			int firstIndex = argument.indexOf("<");
//			int lastIndex = argument.indexOf(">");
//			int mIndex = argument.indexOf(":");
//			int flagAIndex = argument.indexOf("@");
//			if(firstIndex>0&&lastIndex>0&&mIndex>0&&flagAIndex>0&&lastIndex>firstIndex
//					&&firstIndex<flagAIndex){
//				str_toName = argument;
//				str = argument.substring(firstIndex+1, flagAIndex);
//				writeMessage("250 mail ok");
//				if(argument.substring(flagAIndex+1, lastIndex).equals("localhost")){
//					writeMessage("250 mail ok!");
//				}else{
//					writeMessage("250 mail ok!");
//					writeMessage("354 Send from Rising mail proxy");
//					file = new File("D:/transmit/");
//					if(!file.exists()){
//						file.mkdirs();
//					}
//					
//					try {
//						String s = "D:/transmit/"+new Date().getTime()+".txt";
//						System.out.println(s);
//						fileText = new File(s);
//						if(!fileText.exists())
//							fileText.createNewFile();
//						PrintStream ps = new PrintStream(fileText);
//						String str = null;
//						br.readLine();
//						while(!(str = br.readLine()).equalsIgnoreCase(".")){
//							ps.println(str);
//						}
//						ps.flush();
//						ps.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					new TransmitSession().start();
//				}
//				}
//	}
	
	/*
	 *和RCPT一样，主要是要判断发件人的格式是否合法，并写回250 mail ok!   
	 */

	private void doMail(String argument) {
		// TODO Auto-generated method stub
		StringBuffer responseBuffer = new StringBuffer();
			int flagFirstIndex = argument.indexOf("<");
			int mIndex = argument.indexOf(":");
			int flagAIndex = argument.indexOf("@");
			int flagLastIndex = argument.indexOf(">");
			str_fromName = argument;
			if(flagFirstIndex>0&&flagLastIndex>0&&mIndex>0&&mIndex>0){
				writeMessage("250 mail ok!");
			}	
	}
	
	
	
	/*doAuth
	 * 判断用户名和密码是否合法。用Base64编码解码后和数据库中的用户名
	 * 密码进行比较。
	 * */
	private boolean doAuth(String argument) {
		// TODO Auto-generated method stub
		AUTHf = true;
		if(argument.equalsIgnoreCase("LOGIN")){
			writeMessage("334 dXNlcm5hbWU6");
			try {
				str_uName = readCommandLine();
				str_uName = new String(Base64.decode(str_uName));
				writeMessage("334 UGFzc3dvcmQ6");
				str_uPassword = readCommandLine();
				str_uPassword = new String(Base64.decode(str_uPassword));
				GetUserInfo gui = new GetUserInfo();
				if(gui.getUserInfor()){
					writeMessage("235 Authentication successful");
					}
					
				} catch (IOException e) {
			// TODO Auto-generated catch block
					e.printStackTrace();
					}
				}
		return AUTHf = true;
		}

	private boolean doEHLO(String argument){
		EHLOf = true;
			writeMessage("250-MAIL");
			writeMessage("250-PIPELINING");
			writeMessage("250-AUTH LOGIN PLAIN");
			writeMessage("250-AUTH=LOGIN PLAIN");
			writeMessage("250 8BITMIME");
			return EHLOf;
	}
	
	private boolean doHELO(String argument) {
		HELOf = true;
		StringBuffer responseBuffer = new  StringBuffer();
        if (argument == null) {
        	writeMessage("501 Domain address required: "+CMD_HELO);
        } else {
        	try {
				writeMessage("250"+InetAddress.getLocalHost());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    	
    	return HELOf;
    }
	
	/*
	 * 写回消息函数
	 */
	private void writeMessage(String message){
		pw.println(message);
		pw.flush();	
	}
	/*
	 * 读取函数，返回读到当前行的字符串
	 */
	final String readCommandLine() throws IOException {
        for (;;) {
            String commandLine = br.readLine();
            str_data = commandLine;
            System.out.println(commandLine);
            if (commandLine != null) {
                commandLine = commandLine.trim();
            }
            return commandLine;
        }
    }
	
	/*
	 * 每次链接，都进行队用户的问候
	 */
	public boolean sendWelcomeMessage(){
		StringBuffer sb = new StringBuffer() ;
		try {
		sb.append(Constants.RESPONSE_CONN_SUCC)
		  .append(" ")
          .append(InetAddress.getLocalHost())
          .append(" ")
          .append("(")
          .append(Constants.VERSION_MAILSERVER)
          .append(")")
          .append(" 欢迎使用 ")
          .append(Constants.SERVER_NAME);	
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		writeMessage(sb.toString()) ;
		return true;
	}

}
