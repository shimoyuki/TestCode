public class Tautog { // ������
	public static void main(String[] args) { // ������
		int arr2[][] = { { 4, 3 }, { 1, 2 } }; // �����ά����
		System.out.println("�����е�Ԫ���ǣ�"); // ��ʾ��Ϣ
		for (int x[] : arr2) { // ���ѭ������Ϊһά����
			for (int e : x) { // ѭ������ÿһ������Ԫ��
				if (e == x.length) { // �жϱ����Ƕ�ά�����е����һ��Ԫ��
					System.out.print(e); // �����ά��������һ��Ԫ��
				} else
					// ������Ƕ�ά�����е����һ��Ԫ��
					System.out.print(e + "��"); // �����Ϣ
			}
		}
	}
}
