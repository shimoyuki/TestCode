package com.jianqiu.refuel;

import static java.lang.System.out;
import com.jianqiu.rand.*;
import java.util.*;

public class TestRefuel {

	public static int greedyRefuel(List<Float> road,float len){
		int times = 0;
		int start = 0;
		out.println("停车的位置：");
		while(road.get(start) < road.get(road.size()-1) - len){
			if(road.get(start+1)-road.get(start)<len){
				for(int i=0; i<road.size(); i++){
					if(road.get(i)<=road.get(start)+len && road.get(i+1)>road.get(start)+len){
						times++;
						start = i;
						out.print(road.get(start) + "    ");
						break;
					}
				}
			}
			else{
				out.print("油量不足。");
				break;
			}
		}
		out.println();
		return times;
	}
	
	public static void main(String[] args) {
		List<Float> road = TestRand.randFloat(10, 0f, 20f);
		out.println("加油次数：:\n"+greedyRefuel(road,5f));
	}

}
