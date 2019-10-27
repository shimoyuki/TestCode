package com.jianqiu.rand;

import static java.lang.System.out;

import java.util.*;

public class TestRand {
	
	public static List<Float> randFloat(int n,float begin,float end){
		List<Float> array = new ArrayList<Float>();
		float len = end - begin;
		Random rand = new Random(System.currentTimeMillis());
		for(int i=0; i<n; i++){
			array.add(Math.round(100*(begin + len*rand.nextFloat())) / 100.0f);
		}
		Collections.sort(array);
		 Iterator<Float> it = array.iterator();
		 out.println("随机生成的加油站位置：");
		while(it.hasNext()){
			out.print(it.next() + "    ");
		}
		out.println();
		return array;
	}
}
