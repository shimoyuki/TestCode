package com.jianqiu;

import static  java.lang.System.out;
import com.jianqiu.calaulate.*;
import com.jianqiu.search.*;
import com.jianqiu.sort.*;


public class Test {
	public static void main(String[] agrs){
		out.println("The result of program 1(Binary Search):");
		BinarySearch.test();
		out.println("！！！！！！！！！！！！！！cut-off rule！！！！！！！！！！！！！！");
		out.println("The result of program 2(Bubble Sort):");
		BubbleSort.test();
		out.println("！！！！！！！！！！！！！！cut-off rule！！！！！！！！！！！！！！");
		out.println("The result of program 3(Insert Sort):");
		InsertSort.test();
		out.println("！！！！！！！！！！！！！！cut-off rule！！！！！！！！！！！！！！");
		out.println("The result of program 4:");
		example1.test();
		out.println("！！！！！！！！！！！！！！cut-off rule！！！！！！！！！！！！！！");
		out.println("The result of program 5:");
		example2.test();
		out.println("！！！！！！！！！！！！！！cut-off rule！！！！！！！！！！！！！！");
		out.println("The result of Ternary Search:");
		TernarySearch.test();
	}

}
