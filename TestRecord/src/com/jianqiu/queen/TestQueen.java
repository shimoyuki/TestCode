package com.jianqiu.queen;

import static java.lang.System.out;

public class TestQueen {
	private int[] c;
	private int n;
	
	public TestQueen(int n){
		this.n = n;
		c = new int[n];
		for(int i=0; i<n; i++){
			c[i] = -1;
		}
	}
	
	public void drawChessBoard(){
		for(int j=0; j<n; j++){
			out.print(" ！！\t");
		}
		out.println();
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(j == 0){
					out.print("|");
				}
				if(j == c[i]){
					out.print("  ☆\t|");
				}else{
					out.print("\t|");
				}
			}
			out.println();
			for(int j=0; j<n; j++){
				out.print(" ！！\t");
			}
			out.println();
		}
		out.println("\n\n");
	}
	
	public void queenRecord(){
		int row = 0;
		while(row > -1){
			while(c[row] < n-1){
				c[row] ++;
				if(this.inRightPosition(row, c[row])){
					if(row == n-1){
						this.drawChessBoard();
					}else{
						row++;
					}
				}
			}
			c[row] = -1;
			row--;
		}
	}
	
	public boolean inRightPosition(int row, int col){
		boolean judge = true;
		for(int i=0; i<row; i++){
			if(col==c[i] || row-i==Math.abs(col-c[i])){
				judge = false;
			}
		}
		return judge;
	}
	
	public void queenRecordRe(int row){
		for(int col=0; col<n; col++){
			c[row] = col;
			if(inRightPosition(row,col)){
				if(row == n-1){
					this.drawChessBoard();
					
				}else{
					queenRecordRe(row+1);
				}
			}
		}
		c[row] = -1;
	}
	
	
	
	public static void main(String[] args) {
		//new TestQueen(8).queenRecordRe(0);
		new TestQueen(4).queenRecord();

	}

}
