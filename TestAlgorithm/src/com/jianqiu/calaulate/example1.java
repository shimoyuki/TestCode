package com.jianqiu.calaulate;
import com.generationjava.math.*;

public class example1 {

	public static void test() {
		int count=0,i=0,j=0,k=0;
		float n=10f;
		for(i=1;i<=Logarithm.log(n,2);i++)
			for(j=i;j<=i+5;j++)
				for(k=1;k<=i*i;k++)
					count++;
				System.out.println("Result:"+count);		
	}

}
