package com.jianqiu.search;
import static java.lang.System.out;

import com.jianqiu.sort.*
;
public class TernarySearch {

	private float arr[];
	public TernarySearch(int n,int k){
		int i = 0;
		this.arr = new float[n];
		for(i = 0; i < n; i ++)
			this.arr[i] = k * i + 1;
	}
	
	public TernarySearch(float arr[]){
		this.arr = new float[arr.length];
		for(int i = 0; i <  arr.length; i ++)
			this.arr[i] = arr[i];
	}
	
	public void show(){
		for(int i = 0; i < this.arr.length; i ++)
			out.print(this.arr[i] + " ");
		out.println();
	}
	
	public  int tSearch(float elem){
		int low = 1,high = this.arr.length,mid = 0,x=-1;
		int count = 0;
		while(low <= high && x == -1){
			mid =(2 * low + high) / 3;
			if(elem == this.arr[mid-1]){
				x = mid;
				count ++;
			}
			
			else if(elem < this.arr[mid - 1]){
				high = mid - 1;
				count += 2;
			}
			else{
				low = mid + 1;
				count += 2;
			}
		}
		out.println("Comparasions:" + count);
		return x;
	}
	
	public int tSearch2(float elem){
		int low = 1,high = this.arr.length,fMid = 0,sMid = 0,x=-1;
		int count = 0;
		while(low <= high && x == -1){
			fMid = (2 * low + high) / 3;
			sMid = (low + 2 * high) / 3;
			if(elem == this.arr[fMid-1]){
				x = fMid;
				count ++;
			}
			
			else if(elem < this.arr[fMid - 1]){
				high = fMid - 1;
				count += 2;
			}
			
			else if(elem == this.arr[sMid - 1]){
				x = sMid;
				count +=3;
			}
			
			else if(elem < this.arr[sMid - 1]){
				high = sMid -1;
				low = fMid + 1;
				count +=4;
			}
			
			else{
				low = sMid +1;
				count +=4;
			}
		}
		out.println("Comparasions:" + count);
		return x;
	}
	
	public static void test() {
		int i;
		float[] arr = {6,13,7,3,4,5,2,9,15,11,8,16,10,12,1,14};
		InsertSort is = new InsertSort(arr);
		is.isort();
		TernarySearch ts = new TernarySearch(is.getArr());
		
		TernarySearch ts2= new TernarySearch(16,2);
		out.println("Another sorted array:");
		ts2.show();
		
		out.println("Test \"tSearch\"(from 0 to 17)");
		for(float e = 0; e < 18; e ++){
			i = ts.tSearch(e);
			out.println("The number be founded:" + i);
		}
		out.println("Test \"tSearch2\"(from 0 to 32)");
		for(float e = 0; e < 33; e ++){
			i = ts2.tSearch2(e);
			out.println("The number be founded:" + i);
		}
	}

}
