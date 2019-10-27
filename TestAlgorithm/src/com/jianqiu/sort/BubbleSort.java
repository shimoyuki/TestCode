package com.jianqiu.sort;

public class BubbleSort {

	private float arr[];
	private int length;
	
	public BubbleSort(float[] arr,int n){
		int i=0;
		this.length=n;
		this.arr=new float[n];
		for(i=0;i<n;i++)
			this.arr[i]=arr[i];
	}
	
	public BubbleSort(int n){
		int i=0;
		this.length=n;
		this.arr=new float[n];
		for(i=0;i<n;i++)
			this.arr[i]=i+1;
	}
	
	public void bsort(){
		int j=0,k=0,count=2;
		float temp=0f;
		boolean sorted=false;
		while(j<this.length&&sorted==false){
			count+=2;
			sorted=true;
			for(k=this.length-1;k>j;k--)
				if(this.arr[k]<this.arr[k-1]){
					count++;
					temp=this.arr[k];
					this.arr[k]=this.arr[k-1];
					this.arr[k-1]=temp;
					sorted=false;
				}
				else
					count++;
			j=j+1;
		}
		System.out.println('\n'+"The bubble sort result:");
		for(j=0;j<this.length;j++)
			System.out.print(" "+this.arr[j]);
		System.out.println('\n'+"Comparasions:"+count);
	}
	
	public static void test() {
		System.out.println("This is a bubble sort method!");
		float[] arr=new float[]{11,12,1,5,15,3,4,10,7,2,16,9,8,14,13,6};
		int n=16,j=0;
		BubbleSort b=new BubbleSort(arr,n);
		System.out.println("The array is:");
		for(j=0;j<b.length;j++)
			System.out.print(" "+b.arr[j]);
		b.bsort();
	}

}
