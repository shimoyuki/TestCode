package com.evance.pop3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUserInfo {
	
	private static String url = "jdbc:mysql://localhost:3306/mail";//Establish the DataBase Connection
	/**
	 * @param args
	 */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private PreparedStatement ps;
	private Connection con;
	private String sql;
	private String str = null;
	
	public boolean getUserInfor(){
		boolean flag = false;
		try {
			con   = DriverManager.getConnection(url,"root","123");
			sql = "select uName,uPassword from mailInfo";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(rs.getString(1).equals(POP3Session.str_uName)&&rs.getString(2).equals(POP3Session.str_uPassword)){
					flag = true;
					System.out.println(rs.getString(1));
					System.out.println(rs.getString(2));
				}
			}
			
			//con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}
