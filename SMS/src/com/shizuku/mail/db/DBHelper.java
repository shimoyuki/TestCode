package com.shizuku.mail.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.shizuku.mail.SysConfig;

public class DBHelper {
	
	private final static String DB_CLASS_NAME = "com.mysql.jdbc.Driver";
	private final static String DB_DEFAULT_URL = "jdbc:mysql://localhost:3306/SMS";
	private final static String DB_DEFAULT_USER = "root";
	private final static String DB_DEFAULT_PWD = "kagari8243";
	public static Logger log = null;
	public static SysConfig sc = null;
	public static Connection conn = null;
	
	static {
		log = Logger.getLogger(DBHelper.class);
		if(sc == null){
			sc = new SysConfig();
		}
			try {
				if (conn == null && sc.get("Memory").equals("db")) {
					Class.forName(DB_CLASS_NAME).newInstance();
					if(sc.get("DbUrl") != null){
						conn = DriverManager.getConnection(sc.get("DbUrl"), sc.get("DbUser"), sc.get("DbPwd"));
					}
					else{
						conn = DriverManager.getConnection(DB_DEFAULT_URL , DB_DEFAULT_USER, DB_DEFAULT_PWD);
					}
					log.info("Succeed to connect with the database.");
				}
			} catch (ClassNotFoundException e) {
				log.error("Connect failed,Please copy the JDBC driver to the direction 'lib'.");
				e.printStackTrace();
			} catch (SQLException e) {
				log.error("Connect failed,Please confirm your database is started up.");
				e.printStackTrace();
			} catch (Exception e) {
				log.error("Unkonwn error.");
				e.printStackTrace();
			}
		
	}

	public static ResultSet lookForResultSet(String table, String param, String value, String valueType) {
		String sql = "select * from "+table+" where "+param+"=?";
		//String sql = "select * from `"+table+"` where `"+param+"`='"+value+"'";
		if (conn == null)
			return null;
		ResultSet rs = null;
		try {
			PreparedStatement ps = null;
			ps = conn.prepareStatement(sql);
			if(valueType.equals("int")){
				ps.setInt(1, Integer.parseInt(value));
			}else if(valueType.equals("float")){
				ps.setFloat(1, Float.parseFloat(value));
			}else if(valueType.equals("String")){
				ps.setString(1, value);
			}else{
				log.error("Parameter type error.");
			}
			log.info(ps);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static List lookForList(String table, String param, String value, String valueType) {
		List<List> list = new ArrayList<List>();
		ResultSet rs = lookForResultSet(table, param, value, valueType);
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				List<String> row = new ArrayList<String>();
				for (int i = 1; i <= colCount; i++) {
					String str = rs.getString(i);
					if (str != null && !str.isEmpty())
						str = str.trim();
					row.add(str);
				}
				list.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static ResultSet query(String sql){
		if (conn == null)
			return null;
		ResultSet rs = null;
		try {
			PreparedStatement ps = null;
			ps = conn.prepareStatement(sql);
			System.out.println(ps);
			log.info(ps);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	public static int insert(String sql) {
		return update(sql);
	}
	
	public static int delete(String sql) {
		return update(sql);
	}
	
	public static int update(String sql) {
		if (conn == null)
			return -1;
		int result = 0;
		try {
			PreparedStatement ps = null;
			ps = conn.prepareStatement(sql);
			log.info(ps);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
/*	public static void main(String[] args) {
		String sql = "select * from S_user";
		//ResultSet rs = DBHelper.query(sql);
		ResultSet rs = DBHelper.lookForResultSet("S_user", "u_Level", "1", "int");
		try {
			while(rs.next()){
				System.out.println(rs.getString("u_Account"));
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}*/
}
