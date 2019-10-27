package com.jianqiu.graph;

import static java.lang.System.out;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

enum GraphColor{
	RED,BLUE,GREEN,UNKNOWN;
	
	boolean equals(GraphColor c){
		boolean com = false;
		if(this != UNKNOWN){
			if(this == c){
				com = true;
			}
		}
		return com;
	}
}

class ArcNode{
	private int vexName;
	private ArcNode arc; 
	
	public ArcNode(int vexName){
		this.vexName = vexName;
		this.arc = null;
	}
	
	public int getVexName() {
		return vexName;
	}

	public ArcNode getArc() {
		return arc;
	}

	public void setArc(ArcNode arc) {
		this.arc = arc;
	}

}

class VNode{
	private int vexName;
	private GraphColor c = GraphColor.UNKNOWN;
	private ArcNode arc;
	
	public VNode(int vexName,GraphColor c){
		this.vexName = vexName;
		this.c = c;
		this.arc = null;
	}
	
	public int getVexName() {
		return vexName;
	}

	public String toString(){	
		return "("+this.getVexName()+","+c+")";
	}
	
	public GraphColor getC() {
		return c;
	}

	public void setC(GraphColor c) {
		this.c = c;
	}

	public ArcNode getArc() {
		return arc;
	}

	public void setArc(ArcNode arc) {
		this.arc = arc;
	}
	
	public boolean addArc(ArcNode arc){
		boolean flag = false;
		if(this.arc == null){
			this.setArc(arc);
			flag = true;
		}else{
			ArcNode cur = this.arc;
			while(cur.getVexName() != arc.getVexName() && cur.getArc() != null){
				cur = cur.getArc();
			}
			if(cur.getArc() == null){
				cur.setArc(arc);
				flag = true;
			}
		}
		return flag;
	}
	
	public ArcNode delArc(int vexName){
		ArcNode cur = this.arc;
		if(cur != null && cur.getVexName() == vexName){
			this.arc = cur.getArc();
			cur.setArc(null);
		}else{
			ArcNode pre = cur;
			cur = cur.getArc();
			while(cur != null){
				if(cur.getVexName() == vexName){
					pre.setArc(cur.getArc());
					cur.setArc(null);
					break;
				}else{
					cur = cur.getArc();
				}
			}
		}
		return cur;
	}
	
	public boolean compareColor(VNode vex){
		boolean flag = false;
		if(this != null){
			if(this.getC().equals(vex.getC())){
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean compareColor(GraphColor c){
		boolean flag = false;
		if(this != null){
			if(this.getC().equals(c)){
				flag = true;
			}
		}
		return flag;
	}
}

public class ALGraph{
	private List<VNode> vertices;
	private int vexNum, arcNum;
	
	public int getVexNum() {
		return vexNum;
	}

	public int getArcNum() {
		return arcNum;
	}

	public ALGraph(int vexNum){
		vertices = new LinkedList<VNode>();
		for(int i=0; i<vexNum; i++){
			vertices.add(new VNode(i,GraphColor.UNKNOWN));
		}
		this.vexNum = vexNum;
		arcNum = 0;
	}
	
	public VNode getVex(int vexName){
		VNode founded = null;
		for(int i=0; i< vertices.size(); i++){
			if(vertices.get(i).getVexName() == vexName){
				founded = vertices.get(i);
			}
		}
		return founded;
	}

	public boolean addArc(int vexIn, int vexNew)throws VNodeNotExistsException{
		boolean flag = false;
		if(vexIn > vexNum-1){
			throw new VNodeNotExistsException(vexIn);
		}
		if(vexNew > vexNum-1){
			throw new VNodeNotExistsException(vexNew);
		}
		VNode in = getVex(vexIn);
		flag = in.addArc(new ArcNode(vexNew));
		if(flag){
			arcNum++;
		}
		return flag;
	}
	
	public ArcNode delArc(int vexSta, int vexEnd)throws VNodeNotExistsException{
		if(vexSta > vexNum-1){
			throw new VNodeNotExistsException(vexSta);
		}
		if(vexEnd > vexNum-1){
			throw new VNodeNotExistsException(vexEnd);
		}
		ArcNode del = null;
		VNode sta = getVex(vexSta);
				del = sta.delArc(vexEnd);
				if(del != null){
					arcNum--;
				}
		return del;
	}
	
	public boolean addArcDou(int vex1, int vex2)throws VNodeNotExistsException{
		boolean flag = true;
		if(!this.addArc(vex1, vex2)){
			flag = false;
		};
		if(!this.addArc(vex2, vex1)){
			flag = false;
		};
		return flag;
	}

	public void delArcDou(int vex1, int vex2)throws VNodeNotExistsException{
		this.delArc(vex1, vex2);
		this.delArc(vex2, vex1);
	}
	
	boolean checkColor(int vexName,GraphColor c)throws VNodeNotExistsException{
		if(vexName > vexNum-1){
			throw new VNodeNotExistsException(vexName);
		}
		boolean flag = false;
		VNode vex = getVex(vexName);
		ArcNode arc = vex.getArc();
		while(arc != null){
			if(this.getVex(arc.getVexName()).compareColor(c)){
				flag = true;
				break;
			}
			arc = arc.getArc();
		}
		return flag;
	}
	
	public void showGraph(){
		Iterator<VNode> it = vertices.iterator();
		VNode vex = null;
		ArcNode cur = null;
		while(it.hasNext()){
			vex = it.next();
			out.print(vex);
			cur = vex.getArc();
			while(cur != null){
				out.print("->" + this.getVex(cur.getVexName()));
				cur = cur.getArc();
			}
			out.println();
		}
	}
}

class VNodeNotExistsException extends Exception{
	VNodeNotExistsException(int vexName){
		out.println("结点" + vexName + "不存在。");
	}
}