package com.guanzhiwei;

public class Kmer {
	String Kmer_seq;
	int addr;
	// 当前正在拼接的read的个数
	int num;
	int cur;

	public String getKmer_seq() {
		return Kmer_seq;
	}

	public void setKmer_seq(String kmer_seq) {
		Kmer_seq = kmer_seq;
	}

	public int getAddr() {
		return addr;
	}

	public void setAddr(int addr) {
		this.addr = addr;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getCur() {
		return cur;
	}

	public void setCur(int cur) {
		this.cur = cur;
	}



}
