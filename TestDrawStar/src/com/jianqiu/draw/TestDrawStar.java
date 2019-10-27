package com.jianqiu.draw;
import static java.lang.System.out;

import java.math.*;

public class TestDrawStar{

	private char star;
	private int[][] posMax;
	
	TestDrawStar(int n,char c){
		int size = (int)Math.pow(3,n);
		this.posMax = findPos(size);
		this.star = c;
	}
	
	private int[][] findPos(int size){
		int[][] posMax = new int[size][size];
		if(size == 1)
			posMax[0][0] = 1;
		else{
			int preSize = size / 3;
			for(int i = 0; i < 5; i ++){
				int[][] preMax = findPos(preSize);
				switch(i){
				case 0:
				for(int j = 0; j < preSize; j ++)
					for(int k = 0; k < preSize; k++){
						posMax[j][k] = preMax[j][k];
					}
				case 1:
					for(int j = 0; j < preSize; j ++)
						for(int k = 0; k < preSize; k++){
							posMax[j][2 * preSize + k] = preMax[j][k];
						}
				case 2:
					for(int j = 0; j < preSize; j ++)
						for(int k = 0; k < preSize; k++){
							posMax[preSize + j][preSize + k] = preMax[j][k];
						}
				case 3:
					for(int j = 0; j < preSize; j ++)
						for(int k = 0; k < preSize; k++){
							posMax[2 * preSize + j][k] = preMax[j][k];
						}
				case 4:
					for(int j = 0; j < preSize; j ++)
						for(int k = 0; k < preSize; k++){
							posMax[2 * preSize + j][2 * preSize + k] = preMax[j][k];
						}
				}
			}
		}
		return posMax;
	}
	
	public boolean drawStar(){
		for(int[] x1:this.posMax){
			for(int x2:x1){
				if(x2 == 1)
					//out.print(" " + this.star + " ");
					out.printf("%c\t",this.star);
				else
					out.print("\t");
			}
			out.println();
		}
		return true;
	}
	
	public static void main(String[] args) {
		 TestDrawStar tds = new TestDrawStar(3,'X');
		 tds.drawStar();

	}

}
