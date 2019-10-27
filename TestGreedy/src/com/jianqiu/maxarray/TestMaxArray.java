package com.jianqiu.maxarray;

import static java.lang.System.out;
import com.jianqiu.rand.*;
import java.util.*;

public class TestMaxArray {

	public static float greedyArray(List<Float> arr1,List<Float> arr2){
		float max = 0f;
		Collections.reverse(arr1);
		Collections.reverse(arr2);
		Iterator<Float> it1 = arr1.iterator(),it2 = arr2.iterator();
		while(it1.hasNext() && it2.hasNext()){
			max += it1.next() * it2.next();
		}
		return max;
	}
	public static void main(String[] args) {
		List<Float> arr1 = TestRand.randFloat(10, 1f, 100f);
		List<Float> arr2 = TestRand.randFloat(10, 1f, 100f);
		out.println("The max sum:\n" + greedyArray(arr1,arr2));

	}

}
