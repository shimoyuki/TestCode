package com.cardinal.josephos;

public class TestJosephos {
	
	public void josephosCircle(int n, int m, int index){
		boolean[] cir = new boolean[n];
		for(int i=0; i<cir.length; i++) {
			cir[i] = true;
		}
		
		int leftCount = n;
		int countNum = 0;
		
		while(leftCount != 0) {
			if(cir[index]) {
				countNum ++;
				if(countNum == m) {
					countNum = 0;
					cir[index] = false;
					leftCount --;
					System.out.print((index+1) + "\t");
					if(leftCount%10==0){
						System.out.println();
					}
				}
			}
			index ++;
			if(index == cir.length) {
				index = 0;
			}
		}
	}
	
	public static void main(String[] args) {
		new TestJosephos().josephosCircle(100, 5, 3);
	}
}