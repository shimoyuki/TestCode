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
		out.println("��������Ҫ���ҵ�Fibonacci ���е�����:");
		int n = -1;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(n<0){
			try {
				n = Integer.parseInt(br.readLine());
			} catch (IOException e) {
				out.println("����δ֪�������");
				System.exit(-1);
			} catch(NumberFormatException e){
				out.println("��������ȷ�����֡�");
				continue;
			}
			if(n<0){
				out.println("��������������");
			}
		}
		out.print("Fibonacci���е�" + n + "��Ϊ: ");
		out.println(new TestFibonacci().calcuFibonacci(n));
	}
	
}
