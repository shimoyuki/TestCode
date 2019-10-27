package com.jianqiu.lp;

import static java.lang.System.out;

public class TestLP {

	private int[][] m;//m[i][j]表示子问题c,a,x去掉前i-1项,b值为j时的最优值
	private int[] ans;
	
	public void showM(){
		if( m!= null){
			out.println("矩阵m：");
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
			out.println("最优解：");
			for(int i=0; i<ans.length; i++){
				out.print(ans[i]+"\t");
			}
			out.println();
		}
	}
	
	public int dynamicLP(int[] c, int[] a, int b){
		//原问题为 max c·x
		//s.t. a·x<=b，x均为大于等于0的整数
		//其中c为目标系数向量，x为目标变量，a为约束系数向量
		//由于一些情况下，本例不能用动态规划求解，故在此只考虑a,c,b均大于等于0的情况
		boolean flag = true;
		int n = c.length, i = 0, j = 0;
		m = new int[n][b+1];
		if(b<0){
			out.println("b应为正数！");
			flag = false;
		}
		if(n!=a.length){
			out.println("输入有误！");
			flag = false;
		}else{
			for(i=0; i<n; i++){
				if(a[i]<0 || c[i]<0){
					out.println("系数应为正数！");
					flag = false;
				}
			}
		}
		if(flag){
			//a,b均大于0时
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
		out.println("输入数据为:");
		for(int i=0; i<3; i++){
			out.println("c"+i+":  "+c[i]+"\ta"+i+":  "+a[i]);
		}
		int b = 5;
		out.println("b:  "+b);
		int z = tl.dynamicLP(c, a, b);
		out.println("最优值为："+z);
		tl.showM();
		tl.traceBack(c, a, b);
		tl.showAns();
	}
	
	public static void main(String[] args) {
		TestLP.test();

	}

}
