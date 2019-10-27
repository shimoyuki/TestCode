package com.lycoris.test;

public class Test1 {
	private String a;
	private static String b;
	
	public void printOne(){
		System.out.println(a+"one");
	}
	
	public void printTwo(){
		System.out.println(b+"two");
	}
	
	public static void main(String[] args){
		new Test1().printOne();
		new Test1().printTwo();
		}
	}
