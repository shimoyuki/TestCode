package com.jianqiu.split;

import static java.lang.System.out;

import javax.swing.JFrame;

public class TestSplit {
	private int count = 0;
	private int[] x;
	private int[] result;
	
	public int getCount() {
		return count;
	}

	public void splitNum(int num, int min){
		count++;
		if(num>1){
			for(int left=num-min, right=min; left>=right; left--, right++){
			splitNum(left, right);
			}
		}
	}
	
	/*public int sumReAns(int k){
		int sumReAns = 0;
		for(int i=0; i<=k; i++){
			if(x[i] > -1){
			sumReAns += result[x[i]];
			}
		}
		return sumReAns;
	}
	
	public boolean isSuitable(int num, int k){
		if(sumReAns(k) > num){
			return false;
		}
		if(k>0 && result[x[k]] > result[x[k-1]]){
			return false;
		}
		return true;
	}
	
	public void splitNumRecord(int num){
		result = new int[num];
		x = new int[num];
		for(int i=0; i<num; i++){
			result[i] = num-i;
		}
		for(int resNum=0; resNum<num; resNum++){
			int k = 0;
			x[k] = -1;
			while(k > -1){
				x[k] +=1;
				while(x[k]<num && !isSuitable(num,k)){
					x[k] +=1;
				}
				if(x[k] > num-1){
					k -= 1;
				}else if(k == resNum){
					if(sumReAns(k) == num){
						for(int i=0; i<=k; i++){
							out.print(result[x[i]] + " ");
						}
						out.println();
						count++;
					}
				}else{
					k += 1;
					x[k] = -1;
				}
			}
		}
	}*/
	
	public int sumReAns(int num, int k){
		int sum = 0;
		for(int i=0; i<=k; i++){
			if(x[i] < num+1){
			sum += x[i];
			}
		}
		return sum;
	}
	
	public boolean isSuitable(int num, int k){
		if(sumReAns(num,k) > num){
			return false;
		}
		if(k>0 && x[k] > x[k-1]){
			return false;
		}
		return true;
	}
	
	public void splitNumRecord(int num){
		x = new int[num];
		int k = 0;
		for(int i=0; i<x.length; i++){
			x[i]= num+1;
		}
		while(k > -1){
			while(x[k] > 0){
				x[k]--;
				if(this.isSuitable(num, k)){
					if(sumReAns(num,k) == num){
						out.print("Answer"+(count+1)+":    ");
						for(int i=0; i<=k; i++){
							out.print(x[i] + " ");
						}
						out.println();
						count++;
					}else if(k < num-1){
						k++;
					}
				}
			}
			x[k] = num+1;
			k--;
		}
	}

	public static void main(String[] args) {
		TestSplit ts = new TestSplit();
		//ts.splitNum(3, 1);
		ts.splitNumRecord(10);
		out.println("Counts of right answers:    "+ts.getCount());
	}

}
