package com.cardinal.goldbach;
import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestGoldbach {

	public boolean checkPrimeNumber(int n){
		if(n%2==0 && n>2){
			return false;
		}
		for(int i=3; i<=Math.sqrt(n); i+=2){
			if(n%i==0){
				return false;
			}
		}
		return true;
	}
	
	public void splitEvenNumber(int n){
		int[] ans = new int[2];
		for(int i=0; i<ans.length; i++){
			ans[i] = -1;
		}
		for(int i=1; i<=n/2; i++){
			if(this.checkPrimeNumber(i) && this.checkPrimeNumber(n-i)){
				ans[0] = i;
				ans[1] = n-i;
				out.println("��ֵõ�����������Ϊ " + ans[0] + " �� " +ans[1]);
			}
		}
	}
	
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		out.println("������һ��ż��:");
		int n = -1;
		while(n<0 || n%2!=0){
			try {
				n = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
				out.println("��������ȷ�����֡�");
				continue;
			} catch (IOException e) {
				out.println("����δ֪�������");
				System.exit(-1);
			}
			if(n<0){
				out.println("��������������");
			}else if(n%2!=0){
				out.println("������ż����");
			}
		}
		
		new TestGoldbach().splitEvenNumber(n);
		
	}

}
