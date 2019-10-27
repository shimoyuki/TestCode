package com.cardinal.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class TestSerialize {
	File f = new File("object.txt");
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	Map<Integer, User> userMap = new HashMap<Integer, User>();

	public TestSerialize() {
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			f.delete();
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			oos = new ObjectOutputStream(new FileOutputStream(f,true));
			ois = new ObjectInputStream(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	public void save(){
		try {
			userMap.put(1, new User("ayano",001,20));
			userMap.put(2, new User("shintaro",002,20));
			userMap.put(3, new User("momo",003,18));
			oos.writeObject(userMap);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void display(){
		try {
			Object o = ois.readObject();
			if(o instanceof Map){
			Map<Integer, User> m = (Map<Integer, User>)o;
			System.out.println(m.get(1));
			System.out.println(m.get(2));
			System.out.println(m.get(3));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestSerialize ts = new TestSerialize();
		ts.save();
		ts.display();
	}

}


class User implements Serializable{
	private String name;
	private int id;
	private int age;

	public User(String name, int id, int age) {
		super();
		this.name = name;
		this.id = id;
		this.age = age;
	}

	public User() {
		super();
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "id:"+id+" name:"+name+" age:"+age;
	}

}