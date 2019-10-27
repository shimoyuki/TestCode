package com.jianqiu.sort;

public class InsertSort {

	private float arr[];
	private int length;
	
	public InsertSort(float[] arr){
		int i=0;
		this.length=arr.length;
		this.arr=new float[this.length];
		for(i=0;i<this.length;i++)
			this.arr[i]=arr[i];
	}
	
	public InsertSort(int n,int k){
		int i=0;
		this.length=n;
		this.arr=new float[n];
		for(i=0;i<n;i++)
			this.arr[i]=k*i+1;
	}
	
	public float[] getArr(){
		return this.arr;
	}
	
	public void isort(){
		int j=0,k=0,count=2;
		float min;
		for(j=1;j<this.length;j++){
			min=this.arr[j];
			k=j-1;
			while(k>-1&&this.arr[k]>min){
				count+=2;
				this.arr[k+1]=this.arr[k];
				k=k-1;
			}
			this.arr[k+1]=min;
		}
		System.out.println('\n'+"The insert sort result:");
		for(j=0;j<this.length;j++)
			System.out.print(" "+this.arr[j]);
		System.out.println('\n'+"Comparasions:"+count);
	}
	
	public static void test() {
		System.out.println("This is a insert sort method!");
		float[] arr=new float[]{11,12,1,5,15,3,4,10,7,2,16,9,8,14,13,6};
		int j=0;
		InsertSort i=new InsertSort(arr);
		System.out.println("The array is:");
		for(j=0;j<i.length;j++)
			System.out.print(" "+i.arr[j]);
		i.isort();
	}

}
