package com.cardinal.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.cardinal.model.Grade;

public class DataDAO {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String path;

	public DataDAO(String path) {
		super();
		this.path = path;
		File f = new File(path);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	public void openOIS(){
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public void closeOIS(){
		try {
			ois.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public List<Grade> queryByName(String name){
		this.openOIS();
		List<Grade> lg = new ArrayList<Grade>();
		Object obj;
		Grade gra = null;
		boolean flag = true;
		while(flag) {
			try {
				obj = ois.readObject();
				if(obj instanceof Grade){
					gra = (Grade)obj;
					if(gra.getName().equals(name)){
						lg.add(gra);
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (EOFException e){
				flag = false;
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		this.closeOIS();
		return lg;
	}

	public List<Grade> queryByCourse(String course){
		this.openOIS();
		List<Grade> lg = new ArrayList<Grade>();
		Object obj;
		Grade gra = null;
		boolean flag = true;
		while(flag) {
			try {
				obj = ois.readObject();
				if(obj instanceof Grade){
					gra = (Grade)obj;
					if(gra.getCourse().equals(course)){
						lg.add(gra);
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (EOFException e){
				flag = false;
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		this.closeOIS();
		return lg;
	}

	public List<Grade> queryByWhole(String name, String course){
		this.openOIS();
		List<Grade> lg = new ArrayList<Grade>();
		Object obj;
		Grade gra = null;
		boolean flag = true;
		while(flag) {
			try {
				obj = ois.readObject();
				if(obj instanceof Grade){
					gra = (Grade)obj;
					if(gra.getName().equals(name)&&gra.getCourse().equals(course)){
						lg.add(gra);
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (EOFException e){
				flag = false;
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		this.closeOIS();
		return lg;
	}

	public List<Grade> queryAll(){
		this.openOIS();
		List<Grade> lg = new ArrayList<Grade>();
		Object obj;
		Grade gra = null;
		boolean flag = true;
		while(flag) {
			try {
				obj = ois.readObject();
				if(obj instanceof Grade){
					gra = (Grade)obj;
					lg.add(gra);
				}
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (EOFException e){
				flag = false;
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		this.closeOIS();
		return lg;
	}

	public boolean save(Grade gra){
		try {
			File file = new File(path); 
			FileOutputStream fos = new FileOutputStream(file, true);     
			if(file.length()<1){           
				oos = new ObjectOutputStream(fos);    
			}else{         
				if(this.queryByWhole(gra.getName(), gra.getCourse()).size() > 0){
					return false;
				}
				oos = new ObjectOutputStreamWithoutHeader(fos);  
			}  
			oos.writeObject(gra);
			oos.close();
			oos = null;
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		/*Grade g1 = new Grade("s1", "c1", 50), g2 = new Grade("s1", "c2", 80);
		DataDAO dd = new DataDAO("data/grade.dat");
		dd.save(g1);
		dd.save(g2);
		List<Grade> lg = dd.queryByName("s1");
		System.out.println(lg.get(0));
		System.out.println(lg.get(1));
		System.out.println(dd.queryByCourse("c2").get(0));*/
	}

}

class ObjectOutputStreamWithoutHeader extends ObjectOutputStream { 
	public ObjectOutputStreamWithoutHeader() throws IOException {  
		super(); 
	}
	public ObjectOutputStreamWithoutHeader(OutputStream out) throws IOException {
		super(out);
	} 
	@Override 
	protected void writeStreamHeader() throws IOException { 
		return;
	}
}
