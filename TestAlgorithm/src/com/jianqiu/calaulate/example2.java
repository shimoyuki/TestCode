package com.jianqiu.calaulate;

public class example2 {

	public static void test() {
		int count=0,i=0;
		float n=10f,j=0f;
		for(i=1;i<=n;i++){
			j=n/2;
			while(j>=1){
				count++;
				if(j%2==1)
					j=0;
				else
					j=j/2;
				}
			}
		System.out.println("Result:"+count);	
		}
	}
