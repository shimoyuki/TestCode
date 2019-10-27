import java.sql.*;

public class Gradation { // ������
	static Connection con; // ����Connection����
	static Statement sql; // ����Statement����
	static ResultSet res; // ����ResultSet����
	
	public Connection getConnection() { // �������ݿⷽ��

		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://localhost:1433/"
							+ "db_jdbc", "sa", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con; // ����Connection����
	}
	
	public static void main(String[] args) { // ������
		Gradation c = new Gradation(); // �����������
		con = c.getConnection(); // �����ݿ⽨������
		try {
			sql = con.createStatement(); // ʵ����Statement����
			// ִ��SQL��䣬���ؽ����
			res = sql.executeQuery("select * from tb_stu");
			while (res.next()) { // �����ǰ��䲻�����һ�������ѭ��
				String id = res.getString("id"); // ��ȡ������"id"���ֶ�ֵ
				// ��ȡ������"name"���ֶ�ֵ
				String name = res.getString("name");
				// ��ȡ������"sex"���ֶ�ֵ
				String sex = res.getString("sex");
				// ��ȡ������"birthday"���ֶ�ֵ
				String birthday = res.getString("birthday");
				System.out.print("��ţ�" + id); // ����ֵ���
				System.out.print(" ����:" + name);
				System.out.print(" �Ա�:" + sex);
				System.out.println(" ���գ�" + birthday);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
