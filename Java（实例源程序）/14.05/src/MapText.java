import java.util.*;

public class MapText { // ������MapText
	public static void main(String[] args) { // ������
		Map<String,String> map = new HashMap<>(); // ��HashMapʵ�ֵ�Map����
		Emp emp = new Emp("001", "����");
		Emp emp2 = new Emp("005", "����"); // ����Emp����
		Emp emp3 = new Emp("004", "��һ");
		map.put(emp.getE_id(), emp.getE_name());
		map.put(emp2.getE_id(), emp2.getE_name()); // ��������ӵ�������
		map.put(emp3.getE_id(), emp3.getE_name());
		Set<String> set = map.keySet(); // ��ȡMap�����е�key���󼯺�
		Iterator<String> it = set.iterator();
		System.out.println("HashMap��ʵ�ֵ�Map���ϣ�����");
		while (it.hasNext()) {
			String str = (String) it.next();
			String name = (String) map.get(str); // ����Map����
			System.out.println(str + " " + name);
		}
		TreeMap<String,String> treemap = new TreeMap<>(); // ����TreeMap���϶���
		treemap.putAll(map); // �򼯺���Ӷ���
		Iterator<String> iter = treemap.keySet().iterator();
		System.out.println("TreeMap��ʵ�ֵ�Map���ϣ�����������");
		while (iter.hasNext()) { // ����TreeMap���϶���
			String str = (String) iter.next(); // ��ȡ�����е�����key����
			String name = (String) treemap.get(str); // ��ȡ�����е�����valuesֵ
			System.out.println(str + " " + name);
		}
	}
}
