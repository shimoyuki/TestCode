package com.jianqiu.bag;

import static java.lang.System.out;
import java.util.*;

public class TestBag {
	private int[][][] m;
	private boolean[] select;
	
	public boolean[] getSelected(){
		return this.select;
	}
	
	public void printM(){
		for(int i = 0; i < this.m.length; i ++)
			for(int j = 0; j < this.m[i].length; j ++){
				for(int k = 0; k < this.m[i][j].length; k ++)
					out.printf("%d,%d,%d:%d\t", i,j,k,this.m[i][j][k]);
				out.println();
			}
	}

	public void knapsack(Bag b, Item[] it) {
		int n = it.length - 1, c = b.getContent(), d = b.getCubage();
		this.m = new int[n + 1][c + 1][d + 1];

		for (int j = 0; j <= c; j++)
			for (int k = 0; k <= d; k++) {
				if (j >= it[n].getWeight() && k >= it[n].getVolume())
					m[n][j][k] = it[n].getValue();
				else
					m[n][j][k] = 0;
			}

		for (int i = n - 1; i > 0; i --)
			for (int j = 0; j <= c; j++)
				for (int k = 0; k <= d; k++) {
					if (j >= it[i].getWeight() && k >= it[i].getVolume())
						m[i][j][k] = Math.max(m[i + 1][j][k], m[i + 1][j
								- it[i].getWeight()][k - it[i].getVolume()]
								+ it[i].getValue());
					else
						m[i][j][k] = m[i + 1][j][k];
				}
		if (c >= it[0].getWeight() && d >= it[0].getVolume())
			m[0][c][d] = Math.max(m[1][c][d], m[1][c - it[0].getWeight()][d
					- it[0].getVolume()]
					+ it[0].getValue());
		else
			m[0][c][d] = m[1][c][d];
	}
	
	public void traceBack(Bag b, Item[] it){
		int n = it.length - 1, c = b.getContent(), d = b.getCubage();
		this.select = new boolean[n + 1];
		for(int i = 0; i < n; i ++){
			if(m[i][c][d] == m[i + 1][c][d])
				this.select[i] = false;
			else{
				this.select[i] = true;
				c -= it[i].getWeight();
				d -= it[i].getVolume();
			}
		}
			select[n] = (m[n][c][d] > 0) ? true : false;
	}
	
	public static void main(String[] args){
		Bag b = new Bag(20,20);
		Item[] it = new Item[5];
		for(int i = 0; i < it.length; i ++){
			it[i] = new Item(2 * i,i + 2,i * i);
		}
		TestBag tb = new TestBag();
		tb.knapsack(b, it);
		tb.traceBack(b, it);
		for(int i = 0; i < it.length; i ++){
			if(tb.getSelected()[i] == true){
				b.addItem(it[i]);	
				}
		}
		
		b.showInfo();
		b.showItem();
		tb.printM();
		
	}

}

class Item{
	private int weight,volume,value;
	String name;
	static public int i = 0;
	
	Item(int w, int vo, int va){
		Item.i ++;
		this.weight = w;
		this.volume = vo;
		this.value = va;
		this.name = new String("item" + Item.i);
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		return this.getName();
	}
	
	public int getWeight(){
		return this.weight;
	}
	
	public int getVolume(){
		return this.volume;
	}
	
	public int getValue(){
		return this.value;
	}
	
}

class Bag{
	private int content,cubage,value;
	private int count;
	private List<Item> list;
	
	Bag(int co, int cu){
		this.content = co;
		this.cubage = cu;
		this.value = 0;
		this.count = 0;
		this.list = new ArrayList<>();
	}
	
	public int getContent(){
		return this.content;
	}
	
	public int getCubage(){
		return this.cubage;
	}
	
	public void showItem(){
		Iterator<Item> ite = this.list.iterator();
		while(ite.hasNext()){
			out.printf("%s\t",ite.next());
		}
		out.println();
	}
	
	public void showInfo(){
		out.printf("Bag's information:\nContent:%d\tCubage:%d\tValue:%d\t", 
				this.content,this.cubage,this.value);
		out.println();
	}
	
	public int getValue(){
		return this.value;
	}

	public boolean addItem(Item it){
		if (this.content >= it.getWeight() && this.cubage >= it.getVolume()) {
			this.count++;
			this.content -= it.getWeight();
			this.cubage -= it.getVolume();
			this.value += it.getValue();
			this.list.add(it);
			return true;
		} 
		else
			return false;
	}
	
	public boolean removeItem(int index){
		if(this.count > 0){
			this.count --;
			Item it = this.list.get(index);
			this.content += it.getWeight();
			this.cubage += it.getVolume();
			this.value -= it.getValue();
			this.list.remove(index);
			return true;
		} 
		else
			return false;
	}
	
}