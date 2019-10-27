package com.jianqiu.search;

public class BinarySearch {
	private float arr[];
	private int length;
	
	public BinarySearch(int n,int k){
		int i=0;
		this.length=n;
		this.arr=new float[n];
		for(i=0;i<n;i++)
			this.arr[i]=k*i+1;
	}
	
	public int bsearch(float elem){
		int x=-1,low=1,high=this.length,mid=0,count=0;
		while(low<=high&&x==-1){
			mid=(low+high)/2;
			if(elem==this.arr[mid-1]){
				count++;
				x=mid;
				}
			
			else if(elem<this.arr[mid-1]){
				count+=2;
				high=mid-1;
				}
			
			else{
				count+=2;
				low=mid+1;
				}
			}
		
		System.out.println('\n'+"Comparison times:"+count);
		return x;

	}
	
	public int bsearch2(float elem){
		int low=1,high=this.length,mid=0;
		while(low<high){
			mid=(low+high+1)/2;
			if(elem<this.arr[mid-1]){
				high=mid-1;
				}
			
			else{
				low=mid;
				}
			}
		if(elem==this.arr[low-1])
			return low;
		else 
			return -1;

	}
	
	private  boolean check(){
		int i;
		System.out.println("The result of checking the method \"bsearch2\":(from 1 to 20)");
		for(float x:this.arr){
			i=this.bsearch2(x);
			System.out.print(i+" ");
		}
		System.out.println();
		return true;
	}
	
	public int[] bsearch3(float elem){
		int low=1,high=this.length,mid=0,loc[]=new int[2];
		boolean flag=false;
		while(low<=high&&flag==false){
			mid=(low+high)/2;
			if(elem==arr[mid-1]){
				loc[0]=mid;
				loc[1]=mid;
				flag=true;
			}
			
			else if(elem<arr[mid-1]){
				high=mid-1;
			}
			else{
				low=mid+1;
			}
		}
		if(flag==false){
			loc[0]=high;
			loc[1]=low;
		}
		return loc;
	}

	public static void test() {
		//System.out.println("This is a binary search method!");
		BinarySearch b=new BinarySearch(2000,1);
		int i=0;
		float e=4000f;
		//System.out.println("The array is:");
		//for(i=0;i<b.length;i++)
			//System.out.print(" "+b.arr[i]);
		System.out.print("The number you want to search is:"+e);
		i=b.bsearch(e);
		System.out.println("The position of the number  be founded:"+i);
		
		//b.check();
		
		/*System.out.println("The result of \"bsearch3\"(from 0 to 40)");
		for(e=0;e<41;e++){
			int[] loc=b.bsearch3(e);
			System.out.println("The number to search is between "+loc[0]+" and "+loc[1]+" .");
		}*/
	}

	public static void main(String[] args){
		BinarySearch.test();
	}
}
