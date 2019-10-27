package com.jianqiu.lp;

import static java.lang.System.out;

public class TestLP {

	private int[][] m;//m[i][j]��ʾ������c,a,xȥ��ǰi-1��,bֵΪjʱ������ֵ
	private int[] ans;
	
	public void showM(){
		if( m!= null){
			out.println("����m��");
		for(int i=m.length-1; i>=0; i--){
			for(int j=0; j<m[i].length; j++){
				out.print("("+i+","+j+"):  "+m[i][j]+"\t");
			}
			out.println();
		}
		}
	}
	
	public void showAns(){
		if(ans!=null){
			out.println("���Ž⣺");
			for(int i=0; i<ans.length; i++){
				out.print(ans[i]+"\t");
			}
			out.println();
		}
	}
	
	public int dynamicLP(int[] c, int[] a, int b){
		//ԭ����Ϊ max c��x
		//s.t. a��x<=b��x��Ϊ���ڵ���0������
		//����cΪĿ��ϵ��������xΪĿ�������aΪԼ��ϵ������
		//����һЩ����£����������ö�̬�滮��⣬���ڴ�ֻ����a,c,b�����ڵ���0�����
		boolean flag = true;
		int n = c.length, i = 0, j = 0;
		m = new int[n][b+1];
		if(b<0){
			out.println("bӦΪ������");
			flag = false;
		}
		if(n!=a.length){
			out.println("��������");
			flag = false;
		}else{
			for(i=0; i<n; i++){
				if(a[i]<0 || c[i]<0){
					out.println("ϵ��ӦΪ������");
					flag = false;
				}
			}
		}
		if(flag){
			//a,b������0ʱ
		for(j=0; j<=b; j++){
			if(a[n-1]>j){
				m[n-1][j] = 0;
			}
			else{
				m[n-1][j] = m[n-1][j-a[n-1]] + c[n-1];
			}
		}
		for(i=n-2; i>=0; i--){
			for(j=0; j<=b; j++){
				if(a[i]>j){
					m[i][j] = m[i+1][j];
				}
				else{
					m[i][j] = Math.max(m[i+1][j], m[i][j-a[i]]+c[i]);
				}
			}
		}
		}
		return m[0][b];
	}
	
	public void traceBack(int[] c, int[] a, int b){
		if(m[0][b] != 0){
		int n = c.length;
		ans = new int[n];
		for(int k=0; k<n;k++){
			ans[k] = 0;
		}
		int i = 0, j = b;
		while(i<n && j>=a[i]){
			if(m[i][j]!=m[i][j-a[i]]+c[i]){
				i++;
			}else{
				j -= a[i];
				ans[i]++;
			}
		}
		}
	}
	
	public static void test(){
		TestLP tl = new TestLP();
		int[] c = {1,3,5}, a = {1,2,3};
		out.println("��������Ϊ:");
		for(int i=0; i<3; i++){
			out.println("c"+i+":  "+c[i]+"\ta"+i+":  "+a[i]);
		}
		int b = 5;
		out.println("b:  "+b);
		int z = tl.dynamicLP(c, a, b);
		out.println("����ֵΪ��"+z);
		tl.showM();
		tl.traceBack(c, a, b);
		tl.showAns();
	}
	
	public static void main(String[] args) {
		TestLP.test();

	}

}
