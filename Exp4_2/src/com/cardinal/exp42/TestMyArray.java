package com.cardinal.exp42;

import static java.lang.System.out;

public class TestMyArray {

	public static void main(String[] args) {
		MyArray m = new MyArray();
		m.show();
		
		for(int i=0; i <m.getLength(); i++){
			m.set(i, i);
			out.print(m.get(i)+"\t");
		}
		out.println();
		
		m.add(1.6);
		m.show();
		
		m.remove(3);
		m.show();
	}

}

class MyArray{
	private Object[] os = new Object[10];
	
	public int getLength(){
		return os.length;
	}

	public void add(Object o){
		Object[] temp = os;
		os = new Object[os.length + 1];
		
		for(int i=0; i<os.length-1; i++){
			this.set(i, temp[i]);
		}
		this.set(os.length-1,o);
	}
	
	public void set(int index, Object o){
		this.os[index] = o;
	}
	
	public Object get(int index){
		return this.os[index];
	}
	
	public void show(){
		for(int i=0; i<os.length; i++){
			out.print(os[i]+"\t");
		}
		out.println();
	}
	
	public void insert(int index, Object o){
		Object[] temp = os;
		os = new Object[os.length + 1];
		
		for(int i=0; i<index; i++){
			this.set(i, temp[i]);
		}
		this.set(index, o);
		for(int i=index+1; i<os.length; i++){
			this.set(i, temp[i-1]);
		}
	}
	
	public void remove(int index){
		Object[] temp = os;
		os = new Object[os.length - 1];
		for(int i=0; i<index; i++){
			this.set(i, temp[i]);
		}
		for(int i=index; i<os.length; i++){
			this.set(i, temp[i+1]);
		}
	}
	
}