import java.sql.*;

public class Conn { // ������Conn
	Connection con; // ����Connection����
	
	public Connection getConnection() {// ��������ֵΪConnection�ķ���
		try {// �������ݿ�������
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			System.out.println("���ݿ��������سɹ�");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {// ͨ���������ݿ��URL��ȡ���ݿ����Ӷ���
			con = DriverManager.getConnection("jdbc:jtds:sqlserver"
					+ "://localhost:1433/db_jdbc", "sa", "");
			System.out.println("���ݿ����ӳɹ�");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con; // ������Ҫ�󷵻�һ��Connection����
	}
	
	public static void main(String[] args) { // ������
		Conn c = new Conn(); // �����������
		c.getConnection(); // �����������ݿⷽ��
	}
}
