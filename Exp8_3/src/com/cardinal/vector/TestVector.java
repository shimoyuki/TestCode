package com.cardinal.vector;
import static java.lang.System.out;
import java.util.*;

public class TestVector {
	private Vector<Student> vs = new Vector<>();
	private Set<Integer> index = new HashSet<>();
	
	public void addStudent(Student s){
		if(index.size()==0){
			this.index.add(s.getId());
			this.vs.add(s);
			out.println("学号为"+s.getId()+"的同学已添加");
		}else{
			boolean flag = true;
			Iterator<Integer> it = index.iterator();
			while(it.hasNext()){
				if(it.next()==s.getId()){
					out.println("学号为"+s.getId()+"的同学已存在。");
					flag = false;
					break;
				}
			}
			if(flag){
				this.index.add(s.getId());
				this.vs.add(s);
				out.println("学号为"+s.getId()+"的同学已添加");
			}
		}
	}
	
	public void delStudentByID(int id){
		Iterator<Integer> iti = index.iterator();
		while(iti.hasNext()){
			if(iti.next() == id){
				iti.remove();
				break;
			}
		}
		Iterator<Student> it = vs.iterator();
		while(it.hasNext()){
			if(it.next().getId() == id){
				it.remove();
				out.println("学号为"+id+"的同学已删除。");
				break;
			}
		}
	}
	
	public void delStudentByName(String name){
		Iterator<Student> it = vs.iterator();
		int count = 0;
		while(it.hasNext()){
			Student s = it.next();
			if(s.getName().equals(name)){
				Iterator<Integer> iti = index.iterator();
				while(iti.hasNext()){
					if(iti.next() == s.getId()){
						iti.remove();
						break;
					}
				}
				it.remove();
				count++;
				out.println("姓名为"+name+"的同学已删除"+count+"个。");
			}
		}
		if(count==1){
			
		}
	}
	
	public int getQuantities(){
		return this.index.size();
	}
	
	public void listStudents(){
		Iterator<Student> it = vs.iterator();
		out.println("学生清单：");
		while(it.hasNext()){
			out.println(it.next());
		}
		out.println();
	}
	
	public static void main(String[] args) {
		TestVector tv = new TestVector();
		tv.addStudent(new Student("nagato", 1, null));
		tv.addStudent(new Student("mutsu", 1, null));
		tv.addStudent(new Student("mutsu", 2, null));
		tv.addStudent(new Student("mutsu", 3, null));
		tv.addStudent(new Student("hyuga", 4, null));
		tv.listStudents();
		tv.delStudentByID(1);
		tv.delStudentByName("mutsu");
		tv.listStudents();
		out.println("学生人数："+tv.getQuantities());

	}

}

class Student{
	private String name = "";
	private static int number = 0;
	private int id = -1;
	private String info = "";
	
	public Student(String name, int id, String info) {
		super();
		this.name = name;
		this.info = info;
		this.id = id;
		number++;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public static int getNumber() {
		return number;
	}

	public static void setNumber(int number) {
		Student.number = number;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Student){
			Student s = (Student)obj;
			if(s.id==this.id){
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "学号："+this.id+"\t姓名:"+this.name+"\t(备注："+this.info+")";
	}
	
	
}
