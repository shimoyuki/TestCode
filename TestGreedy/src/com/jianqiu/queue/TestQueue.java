package com.jianqiu.queue;

import static java.lang.System.out;
import com.jianqiu.rand.*;
import java.util.*;

public class TestQueue {

	public static float greedyQueue(List<Float> queue){
		float wTime = 0f;
		for(int i=0; i<queue.size(); i++){
			wTime += queue.get(i) * (queue.size()-i-1);
		}
		return wTime;
	}
	
	public static void main(String[] args) {
		List<Float> queue = TestRand.randFloat(5, 1f, 5f);
		float wTime = greedyQueue(queue);
		out.println("The whole hours wasted in waiting:" + wTime);
	}

}
