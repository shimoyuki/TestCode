package com.cardinal.fibonacci;

import java.io.*;
import static java.lang.System.out;

public class TestFibonacci {
	private int[] x;
	
	public int calcuFibonacci(int n) {
		x = new int[n];
		x[0] = 1; 
		x[1] = 1;
		for(int i=2; i<x.length; i++){
			x[i] = x[i-1]+x[i-2];
		}
		return x[n-1];
	}

	public static void main(String[] args){
		out.println("请输入需要查找的Fibonacci 数列的项数:");
		int n = -1;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(n<0){
			try {
				n = Integer.parseInt(br.readLine());
			} catch (IOException e) {
				out.println("发生未知输入错误。");
				System.exit(-1);
			} catch(NumberFormatException e){
				out.println("请输入正确的数字。");
				continue;
			}
			if(n<0){
				out.println("请输入正整数。");
			}
		}
		out.print("Fibonacci数列第" + n + "项为: ");
		out.println(new TestFibonacci().calcuFibonacci(n));
	}
	
}
