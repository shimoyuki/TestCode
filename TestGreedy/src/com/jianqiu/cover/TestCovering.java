package com.jianqiu.cover;

import static java.lang.System.out;
import com.jianqiu.rand.*;
import java.util.*;

public class TestCovering {

	public static int greedyCover(List<Float> point,float len){
		int start = 0;
		int sum = 0;
		out.println("The point where the covering line started with:");
		out.print(point.get(start) + "    ");
		while(point.get(start) < point.get(point.size()-1) - len){
		for(int i=0; i<point.size(); i++){
			if(point.get(i) > point.get(start)+len){
				sum++;
				start = i;
				out.print(point.get(start) + "    ");
				break;
			}
		}
		}
		out.println();
		return sum;
	}
	
	public static void main(String[] args) {
		List<Float> point = TestRand.randFloat(20,1f,10f);
		int sum = greedyCover(point,1f);
		out.println("The whole number of lines be used:\n"+sum);

	}

}
