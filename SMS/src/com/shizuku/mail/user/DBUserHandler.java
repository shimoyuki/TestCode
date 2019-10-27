package com.shizuku.mail.user;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.shizuku.mail.Constants;
import com.shizuku.mail.db.DBHelper;
import com.shizuku.mail.domain.DomainHandler;
import com.shizuku.mail.exception.InvalidEmailAddressException;
import com.shizuku.mail.util.EmailAddress;
import com.shizuku.mail.util.FileUtils;
import com.shizuku.mail.util.MD5;

public class DBUserHandler extends AbstractMailUserHandler {

	private Logger log = null;
	private DomainHandler dc = null;

	public DBUserHandler() {
		log = Logger.getLogger(DBUserHandler.class);
		dc = new DomainHandler();
	}

	@Override
	public boolean check(EmailAddress emailAddress, String password, String id) {
		String domain = emailAddress.getDomain(), username = emailAddress
				.getUserName();

		System.out.println(username+";"+password+";"+id);
		try {
			ResultSet rs = queryUser(domain, username);
			
			while (rs.next()) {
				String pwd = rs.getString("u_Pwd");
				if (id == null || id.trim().equals("")) {
					return MD5.encode(password).equals(pwd);
				}
				String encryptedPwd = MD5.encode(id + pwd);
				if (password.equals(encryptedPwd)) {
					return true;
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return false;
	}

	public ResultSet queryUser(String domain, String username)
			throws SQLException {
		String sql = "select * from S_user where u_Account=? and u_Domain=?";

		PreparedStatement ps = DBHelper.conn.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, domain);
		log.info(ps);
		ResultSet rs = ps.executeQuery();

		return rs;
	}
	
	public ResultSet queryUser(String domain){
		return DBHelper.lookForResultSet("S_user", "u_Domain", domain, "String");
	}

	@Override
	public boolean checkUser(String user, String domain) {

		try {
			ResultSet rs = queryUser(domain, user);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return false;
	}

	@Override
	public boolean checkUser(EmailAddress emailAddress) {
		return checkUser(emailAddress.getUserName(), emailAddress.getDomain());
	}

	@Override
	public boolean deleteUser(String userName, String domain) {
		String sql = "delete from S_user where u_Account=? and u_Domain=?";

		try {
			PreparedStatement ps = DBHelper.conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, domain);
			log.info(ps);
			if (ps.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return false;
	}

	@Override
	public List<String> list(String domain) {
		ResultSet rs = queryUser(domain);
		List<String> users = new ArrayList<>();
		
		try {
			while (rs.next()) {
				users.add(rs.getString("u_Account"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return users;
	}

	@Override
	public User getUser(String userName, String domain) {
		try {
			ResultSet rs = queryUser(domain, userName);
			while(rs.next()){
				return new User(	rs.getString("u_Account"), rs.getString("u_Pwd"), rs.getString("u_Domain"), 
						rs.getInt("u_Level"), rs.getLong("u_MemoSize"), rs.getString("u_Tel"), 
						rs.getString("u_Gender"), rs.getString("u_headpic"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public boolean addUser(String userName, String password) {
		String domain = dc.getDefaultDomain();
		if (domain == null) {
			log.error("Please add a domain to server!");
			return false;
		}
		return addUser(userName, password, domain);
	}

	@Override
	public boolean addUser(String userName, String password, String domain) {
		return addUser(new User(userName, password, domain));
	}
	
	public boolean addUser(User user){
		if (!dc.isExist(user.getDomain()) || this.checkUser(user.getUserName(), user.getDomain())) {
			log.error("Domain is not exist or user has aready benn exist.");
			return false;
		}
		String sql = "insert into S_user values(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = DBHelper.conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2,user.getPassword());
			ps.setString(3,user.getDomain());
			ps.setInt(4, user.getLevel());
			ps.setLong(5, user.getSize());
			ps.setString(6,user.getTel());
			ps.setString(7,user.getGender());
			ps.setString(8,user.getPciture());
			log.info(ps);
			if (ps.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean modifyUser(String userName, String password) {
		String domain = dc.getDefaultDomain();
		if (domain == null) {
			log.error("Please add a domain to server!");
			return false;
		}
		return modifyUser(userName, password, domain);
	}

	@Override
	public boolean modifyUser(String userName, String password, String domain) {
		return modifyUser(new User(userName, password,domain));
	}
	
	public boolean modifyUser(User user){
		if (!dc.isExist(user.getDomain()) || !this.checkUser(user.getUserName(), user.getDomain())) {
			log.error("Domain or user is not exist.");
			return false;
		}
		String sql = "update S_user set u_Pwd=?,u_Level=?,u_MemoSize=?,u_Tel=?,u_Gender=?,u_headpic=? where u_Account=? and u_Domain=?;";
		try {
			PreparedStatement ps = DBHelper.conn.prepareStatement(sql);
			
			ps.setString(1,user.getPassword());
			ps.setInt(2, user.getLevel());
			ps.setLong(3, user.getSize());
			ps.setString(4,user.getTel());
			ps.setString(5,user.getGender());
			ps.setString(6,user.getPciture());
			ps.setString(7, user.getUserName());
			ps.setString(8,user.getDomain());
			log.info(ps);
			if (ps.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return false;
	}

	/*public static void main(String[] args) {
		try {
			System.out.println(new DBUserHandler().checkUser(new EmailAddress(
					"cardinal@admin.shizuku.com")));
			System.out.println(new DBUserHandler().deleteUser(new EmailAddress(
					"test@shizuku.com")));
		} catch (InvalidEmailAddressException e) {
			// TODO 鑷姩鐢熸垚鐨� catch 鍧�
			e.printStackTrace();
		}
		List<String> users = new DBUserHandler().list("shizuku.com");
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i));
		}
		System.out.println(new DBUserHandler().getUser("cardinal", "admin.shizuku.com"));
		new DBUserHandler().addUser("test", "test");
		new DBUserHandler().modifyUser("test1", "test11111");
		try {
			System.out.println(new DBUserHandler().check(new EmailAddress("cardinal@admin.shizuku.com"), "cardinal",null));
		} catch (InvalidEmailAddressException e) {
			// TODO 鑷姩鐢熸垚鐨� catch 鍧�
			e.printStackTrace();
		}
	}*/

}
