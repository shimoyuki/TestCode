package com.cardinal.sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestSudoku {
	private int[][] board = null;
	private int[] grid = new int[2];
	private static int num = 1;

	public boolean hasNext(){
		if(num<=Math.pow(this.board.length, 2)){
			return true;
		}
		return false;
	}

	public void fillBoard(){
		board[grid[0]][grid[1]] = num;
		num++;
	}

	public void nextGrid(int x, int y) {
		grid[0] = x;
		grid[1] = y;
		this.fillBoard();
	}

	public void nextStep(){
		if(this.getX()==0 && this.getY()!=this.board.length-1){
			this.nextGrid(this.board.length-1, this.getY()+1);
		}else if(this.getX()!=0 && this.getY()==this.board.length-1){
			this.nextGrid(this.getX()-1, 0);
		}else if(this.getX()==0 && this.getY()==this.board.length-1){
			this.nextGrid(this.getX()+1, this.getY());
		}else{
			this.forwardtoEdge();
		}
	}

	public void forwardtoEdge(){
		if(board[this.getX()-1][this.getY()+1] == 0){
			this.nextGrid(this.getX()-1, this.getY()+1);
		}else{
			this.nextGrid(this.getX()+1, this.getY());
		}
	}

	public int getX(){
		return grid[0];
	}

	public int getY(){
		return grid[1];
	}

	public void showBoard(){
		if(board != null){
			for(int i=0; i<board.length; i++){
				for(int j=0; j<board[i].length; j++){
					System.out.print(board[i][j] + "\t");
				}
				System.out.println();
			}
		}
	}

	public TestSudoku(int n){
		board = new int[n][n];
		this.nextGrid(0, n/2);
		while(this.hasNext()){
			this.nextStep();
		};
	}

	public static int inputData() throws NotOddException, NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入一个奇数：");
		int n = -1;
		n = Integer.parseInt(br.readLine());
		if(n%2==0){
			throw new NotOddException("输入的数字必须为奇数");
		}
		br.close();
		return n;
	}

	public static void main(String[] args) {
		while(true){
				try {
					int n = inputData();
					System.out.println(n + "阶九宫格为：");
					new TestSudoku(n).showBoard();
					break;
				} catch (NumberFormatException e) {
					System.out.println("请输入正确的数值。");
				} catch (NotOddException e) {
					System.out.println("输入值必须为奇数。");
				} catch (IOException e) {
					System.out.println("未知输入错误，请联系技术人员。");
				}
		}

	}

}

class NotOddException extends Exception{
	private static final long serialVersionUID = 1L;

	public NotOddException(String message) {
		super(message);
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}



}