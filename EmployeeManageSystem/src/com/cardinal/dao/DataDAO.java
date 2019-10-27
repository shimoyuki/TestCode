package com.cardinal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.cardinal.model.Employee;


public class DataDAO {
	protected static String dbClassName = "com.mysql.jdbc.Driver";
	protected static String dbUrl = "jdbc:mysql://localhost:3306/PMS";
	protected static String dbUser = "root";
	protected static String dbPwd = "kagari8243";
	protected static String second = null;
	public static Connection conn = null;
	static {
			try {
				if (conn == null) {
					Class.forName(dbClassName).newInstance();
					conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
					System.out.println("数据库连接成功。");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"连接数据库失败，请将JDBC驱动包复制到lib文件夹中。");
				System.exit(-1);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, 
						"数据库连接失败，请确认数据库已启动。");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, 
						"发生未知数据库连接错误，请联系技术人员解决。");
				e.printStackTrace();
			}
		
	}
	
	public static ResultSet lookForResultSet(String table, String param, String value, String valueType) {
		String sql = "select * from "+table+" where "+param+"=?";
		//String sql = "select * from "+table+" where "+param+"='"+value+"'";
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
				JOptionPane.showMessageDialog(null,
						"查询参数类型不存在！");
			}
			System.out.println(ps);
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
			System.out.println(ps);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static int save(Employee emp){
		if (conn == null)
			return -1;
		int result = 0;
		int id = emp.getId(), depId = emp.getDepId(), eduId = emp.getEduId();
		String name = emp.getName(), post = emp.getPost();
		float salary = emp.getSalary();
		String sql = "insert into person values(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setInt(3, depId);
			ps.setInt(4, eduId);
			ps.setString(5, post);
			ps.setFloat(6, salary);
			System.out.println(ps);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}
	
	public static int update(Employee emp){
		if (conn == null)
			return -1;
		int result = 0;
		int id = emp.getId(), depId = emp.getDepId(), eduId = emp.getEduId();
		String name = emp.getName(), post = emp.getPost();
		float salary = emp.getSalary();
		String sql = "update person set name=?,edu_id=?,post=?,salary=? where dep_id=? and id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(6, id);
			ps.setString(1, name);
			ps.setInt(5, depId);
			ps.setInt(2, eduId);
			ps.setString(3, post);
			ps.setFloat(4, salary);
			System.out.println(ps);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<Employee> getEmpInfoById(int depId, int id){
		ResultSet rs = null;
		String sql ="select * from person where dep_id=? and id=?";
		List<Employee> le = new ArrayList<Employee>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, depId);
			ps.setInt(2, id);
			System.out.println(ps);
			rs = ps.executeQuery();
			while(rs.next()){
				Employee e = new Employee();
				e.setId(rs.getInt("id"));
				e.setName(rs.getString("name"));
				e.setDepId(rs.getInt("dep_id"));
				e.setEduId(rs.getInt("edu_id"));
				e.setPost(rs.getString("post"));
				e.setSalary(rs.getFloat("salary"));
				le.add(e);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return le;
	}
	
	public static List<Employee> getEmpInfoByName(String name){
		ResultSet rs = DataDAO.lookForResultSet("Person", "name", name, "String");
		List<Employee> le = new ArrayList<Employee>();
		try {
			while(rs.next()){
				Employee e = new Employee();
				e.setId(rs.getInt("id"));
				e.setName(rs.getString("name"));
				e.setDepId(rs.getInt("dep_id"));
				e.setEduId(rs.getInt("edu_id"));
				e.setPost(rs.getString("post"));
				e.setSalary(rs.getFloat("salary"));
				le.add(e);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return le;
	}
	
	public static List<Employee> getEmpInfo(){
		ResultSet rs = null;
		String sql ="select * from person";
		List<Employee> le = new ArrayList<Employee>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println(ps);
			rs = ps.executeQuery();
			while(rs.next()){
				Employee e = new Employee();
				e.setId(rs.getInt("id"));
				e.setName(rs.getString("name"));
				e.setDepId(rs.getInt("dep_id"));
				e.setEduId(rs.getInt("edu_id"));
				e.setPost(rs.getString("post"));
				e.setSalary(rs.getFloat("salary"));
				le.add(e);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return le;
	}
	
	public static void main(String[] args) {
		/*ResultSet rs = DataDAO.lookForResultSet("Person", "name", "n1", "String");
		List<Employee> le = DataDAO.getEmpInfoById(Integer.parseInt("1"), Integer.parseInt("1"));
		for (int i = 0; i < le.size(); i++) {
			System.out.println(le.get(i));
		}
		try {
			while(rs.next()){
				System.out.println(rs.getString("post"));
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
		//DataDAO.save(new Employee(5, "n", 1, 1, "p", 1000));
		//DataDAO.update(new Employee(5, "n5", 1, 3, "p5", 2000));
		//int i = JOptionPane.showConfirmDialog(null, "该编号的员工已存在,是否覆盖?","提示",  JOptionPane.YES_NO_CANCEL_OPTION);
	}

}