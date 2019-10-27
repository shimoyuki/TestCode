package com.jianqiu.parallel;

import static java.lang.System.out;


public class TestParaThread {

	public static int runPThread(int[][] needTime){
		int n = needTime.length;
		int maxTime = needTime[0][0];
		for(int i = 0; i < n; i ++){
			for(int j  = 0; j < 2; j ++)
				if(needTime[i][j] > maxTime)
					maxTime = needTime[i][j];
		}
		//out.printf("The max need time:\t%d", maxTime);
		//out.println();
		int mn = n * maxTime;
		int leastTime = mn, iLeast = mn, jLeast = mn;
		boolean[][][] p = new boolean[n + 1][mn][mn];
		for(int k = 0; k < n + 1; k ++)
			for(int i = 0; i < mn; i ++)
				for(int j = 0; j < mn; j ++){
					if(k == 0)
						p[k][i][j] = true;
					else
						p[k][i][j] = false;
				}
		
		for(int k = 1; k < n + 1; k ++)
			for(int i = 0; i < mn; i ++)
				for(int j = 0; j < mn; j ++){
					if(i >= needTime[k - 1][0] && j >= needTime[k - 1][1])
						p[k][i][j] =p[k - 1][i - needTime[k - 1][0]][j]  || p[k - 1][i][j - needTime[k - 1][1]];
					else if(i >= needTime[k - 1][0])
						p[k][i][j] =  p[k - 1][i - needTime[k - 1][0]][j];
					else if (j >= needTime[k - 1][1])
						p[k][i][j] = p[k - 1][i][j - needTime[k - 1][1]];
					else
						p[k][i][j] = false;
				} 
		for(int i = 0; i < mn; i ++)
			for(int j = 0; j < mn; j ++){
				if(p[n][i][j])
					if(leastTime > Math.max(i,j)){
						leastTime = Math.max(i,j);
						iLeast = i;
						jLeast = j;
					}
			}
		String str = traceBack(p,needTime,iLeast,jLeast,n);
		out.println("The task running in thread 1:" + str);
		/*for(int k = 1; k < n + 1; k ++){
			for(int i = 0; i < mn; i ++){
				for(int j = 0; j < mn; j ++)
					out.printf("(%d,%d,%d):%b  ", k,i,j,p[k][i][j]);
				out.println();
			}
			out.println();
		}*/
		return leastTime;
	}
	
	public static String traceBack(boolean[][][] p,int[][] nt,int i,int j,int n){
		//out.println(n + " " + i + " " + j);
		if(n == 0){
			return "|";
		}
		else{
			String str = new String();
		if(i >= nt[n - 1][0]){
			str = traceBack(p,nt,i - nt[n - 1][0],j,n-1);
			if(str != "false")
				return str + " Task" + n + " |";
		}
		if(j >= nt[n - 1][1]){
			str = traceBack(p,nt,i,j - nt[n - 1][1],n - 1);
			if(str != "false")
				return str;
		}
		return "false";
		}
	}
	
	public static void main(String[] args) {
		int[][] nt = {{4,6},{3,5},{1,2},{5,5},{1,3},{4,3},{3,7}};
		int lt = TestParaThread.runPThread(nt);
		out.println("The least time needed:" + lt);
	}

}
