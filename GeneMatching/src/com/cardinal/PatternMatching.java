package com.cardinal;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PatternMatching {
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	
	public PatternMatching(String pathIn, String pathOut){
		try {
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(pathIn)));
			bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(pathOut, true)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	public  int matchingSeriesBySysFanc(String strSeries, String strInfo, String templet){
		
		int count = 0;
		if(strSeries.contains(templet)){
			String info = strInfo.split(" ")[2];
			int index = strSeries.indexOf(templet), lastIndex = strSeries.lastIndexOf(templet);
			out.println(info + " : " + (index+1));
			count++;
			while(index<lastIndex){
				index = strSeries.indexOf(templet, index+1);
				out.println(info + " : " + (index+1));
				count++;
			}
		}
		return count;
	}
	
	public int matchingSeriesByOwnFanc(String strSeries, String strInfo, String templet){
		int count = 0, m = templet.length(), n = strSeries.length(),  pos = m-1;
		char[] charsTemp = templet.toCharArray(), charsSeries = strSeries.toCharArray();
		int[] skip = new int[4];//分别对应ACTG的跳跃距离
		for (int i = 0; i < skip.length; i++) {
			skip[i] = m;
		}//初始跳跃距离为m
		for(int j = 0; j < m-1; j++){
			switch(charsTemp[j]){
			case 'A':
				skip[0]=m-j-1;
				break;
			case 'C':
				skip[1]=m-j-1;
				break;
			case 'T':
				skip[2]=m-j-1;
				break;
			case 'G':
				skip[3]=m-j-1;
				break;
			}
		}
		while(pos<n){
			if(charsTemp[m-1]==charsSeries[pos]){
				int i = 1;
				for(; i <m; i++){
					if(charsTemp[m-i-1]!=charsSeries[pos-i]){
						break;
					}
				}
				if(i==m){
					String info = strInfo.split(" ")[2];
					int index = pos-m+1;
					//out.println(info + " : " + (index+1));
					try {
						bw.write("DNA序列号: "+info + "\t;\t" +"序列中的位置: "+ (index+1));
						bw.newLine();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					count++;
				}
			}
			switch(charsSeries[pos]){
			case 'A':
				pos+=skip[0];
				break;
			case 'C':
				pos+=skip[1];
				break;
			case 'T':
				pos+=skip[2];
				break;
			case 'G':
				pos+=skip[3];
				break;
			}
		}
		return count;
	}
	
	public  int searchSeries(String templet) {
		String strInfo = "", strSeries="";
		int count = 0;
			try {
				while(strInfo!=null && strSeries!=null){
						//count+=matchingSeriesBySysFanc(strSeries, strInfo, templet);
						count+=matchingSeriesByOwnFanc(strSeries, strInfo, templet);
					strInfo = br.readLine();
					strSeries = br.readLine();
				}
				out.println("共找到 " + count + "条数据。");
				this.closeStream();
			}catch (IOException e) {
				e.printStackTrace();
			}
			return count;
	}
	
	public void closeStream(){
		try {
			br.close();
			bw.flush();
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	/*	long startTime=System.currentTimeMillis();
		new PatternMatching("data/solexa_100_170_1.fa", "data/result.dat").searchSeries("CAGATG");
		new PatternMatching("data/solexa_100_170_2.fa", "data/result.dat").searchSeries("CAGATG");
		long endTime=System.currentTimeMillis();
		out.println("程序运行时间： "+(endTime - startTime)+"ms");*/
		File f = new File("data/result.dat");
		try {
			if(f.exists()){
				f.delete();
				f.createNewFile();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int k  = -1, count = 0;
		String gen = "";
		out.println("请输入查找序列的长度k：");
		try {
			String str = br.readLine();
			k  = Integer.parseInt(str);
			out.println("请输入长度为"+k+"的碱基对序列：");
			while(gen.equals("")){
				gen = br.readLine();
				if(gen.length()!=k){
					out.println("输入有误，请重新输入。");
					gen = "";
				}
			}
			long startTime=System.currentTimeMillis();
			count += new PatternMatching("data/solexa_100_170_1.fa", "data/result.dat").searchSeries(gen);
			count += new PatternMatching("data/solexa_100_170_2.fa", "data/result.dat").searchSeries(gen);
			long endTime=System.currentTimeMillis();
			BufferedWriter bw =  new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream("data/result.dat", true)));
			bw.write("共查询到 "+count+" 条数据。");
			bw.newLine();
			bw.write("程序运行时间: "+(endTime - startTime)+"ms");
			bw.flush();
			bw.close();
			out.println("程序运行时间: "+(endTime - startTime)+"ms");
		} catch (NumberFormatException e) {
			out.println("输入数据必须为数字！");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
