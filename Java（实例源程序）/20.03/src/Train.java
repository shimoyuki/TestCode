import java.sql.*;

public class Train { // ������Train
	static Connection con; // ����Connection����
	static Statement sql; // ����Statement����
	static ResultSet res; // ����ResultSet����
	
	public Connection getConnection() { // �����ݿ����ӷ���
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://localhost:1433" + "/db_jdbc",
					"sa", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con; // ����Connection����
	}
	
	public static void main(String[] args) { // ������
		Train c = new Train(); // �����������
		con = c.getConnection(); // ��ȡ�����ݿ������
		try { // try��䲶׽�쳣
			sql = con.createStatement(); // ʵ����Statement����
			res = sql
					.executeQuery("select * from tb_stu where name like '��%'");// ִ��SQL���
			while (res.next()) { // �����ǰ��¼���ǽ�����е����һ��������ѭ����
				String id = res.getString(1); // ��ȡid�ֶ�ֵ
				String name = res.getString("name"); // ��ȡname�ֶ�ֵ
				String sex = res.getString("sex"); // ��ȡsex�ֶ�ֵ
				String birthday = res.getString("birthday"); // ��ȡbirthday�ֶ�ֵ
				System.out.print("��ţ�" + id); // �����Ϣ
				System.out.print(" ������" + name);
				System.out.print(" �Ա�:" + sex);
				System.out.println(" ���գ�" + birthday);
			}
		} catch (Exception e) { // �����쳣
			e.printStackTrace(); // ����쳣��Ϣ
		}
	}
}
