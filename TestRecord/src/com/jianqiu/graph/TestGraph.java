package com.jianqiu.graph;

import java.util.*;

import sun.util.locale.provider.AvailableLanguageTags;
import static java.lang.System.out;

public class TestGraph {
	
	private ALGraph alg;
	private int[] c;
	
	public TestGraph(){
		newGraph();
		c = new int[alg.getVexNum()];
	}
	
	public void newGraph(){
		alg = new ALGraph(8);
		try{
		alg.addArcDou(0, 1);
		alg.addArcDou(0, 2);
		alg.addArcDou(0, 3);
		alg.addArcDou(1, 2);
		alg.addArcDou(1, 4);
		alg.addArcDou(2, 3);
		alg.addArcDou(2, 4);
		alg.addArcDou(2, 5);
		alg.addArcDou(2, 6);
		alg.addArcDou(3, 6);
		alg.addArcDou(4, 5);
		alg.addArcDou(4, 7);
		alg.addArcDou(5, 6);
		alg.addArcDou(5, 7);
		alg.addArcDou(6, 7);
		}catch(VNodeNotExistsException vnee){
			vnee.printStackTrace();
			System.exit(-1);
		}
		//alg.showGraph();
	}
	
	public boolean colorRecord(){
		boolean flag = false;
		int i = 0,count = 0;
		GraphColor[] cAvai = {
				GraphColor.UNKNOWN,
				GraphColor.BLUE,
				GraphColor.GREEN,
				GraphColor.RED
				};
		for(int j=0; j<alg.getVexNum(); j++){
			c[j] = 0;
		}
		
		while(i > -1){
			while(c[i] < cAvai.length-1){
				c[i] = c[i]+1;
				try{
					if(!alg.checkColor(i, cAvai[c[i]])){
						alg.getVex(i).setC(cAvai[c[i]]);
						if(i == alg.getVexNum()-1){
							count++;
							flag = true;
							out.println("Solution"+count+":");
							alg.showGraph();
						}else{
						i++;
						}
					}
					}catch(VNodeNotExistsException vnee){
						vnee.printStackTrace();
					}
			}
			c[i] = 0;
			alg.getVex(i).setC(GraphColor.UNKNOWN);
			i--;
		}
		return flag;
	}
	
	public void colorRecordRe(int vexName){
		GraphColor[] cAvai = {
				GraphColor.BLUE,
				GraphColor.GREEN,
				GraphColor.RED
				};
		for(int i=0; i<cAvai.length; i++){
			try{
				if(!alg.checkColor(vexName, cAvai[i])){
					alg.getVex(vexName).setC(cAvai[i]);
					if(vexName == alg.getVexNum()-1){
						alg.showGraph();
						out.println();
						//break;
					}else{
						colorRecordRe(vexName + 1);
					}
				}
				}catch(VNodeNotExistsException vnee){
					vnee.printStackTrace();
				}
		}
		alg.getVex(vexName).setC(GraphColor.UNKNOWN);
	}

	public static void main(String[] args) {
		TestGraph tg = new TestGraph();
		//tg.colorRecord();
		tg.colorRecordRe(0);
	}
}

