package com.guanzhiwei;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AssemblyCenter {
	private String contig = null;
	private List<String> readList = new ArrayList<>(); 
	private Kmer curKmer = null;
	
	@SuppressWarnings("unchecked")
	public ArrayList<Reading> getFirstKmer(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url= "jdbc:mysql://localhost/chuck?user=root&password=1234";
			Connection conn =DriverManager.getConnection(url);
			ResultSet rs1;
			Statement stmt = conn.createStatement();
			String sql = "select count(*) from gene";
			int datalength = 0;
			rs1 = stmt.executeQuery(sql);
			if(rs1.next())
				datalength = Integer.parseInt(rs1.getString("count(*)"));
			System.out.println("datalength="+datalength);
			sql = "select * from gene";
			rs1 = stmt.executeQuery(sql);
			
			//该数组用来判定是否为该序列
			String readById = "";
			List<ReadBank> bankList = new ArrayList<ReadBank>();
			
			int i = 0;
			//readBank的录入
			//第一个序列的data数据已经拿到
			while(rs1.next()){
				ReadBank readBank = new ReadBank();
				readBank.setDataIndex(i);
				readBank.setReadData(rs1.getString("data"));
				bankList.add(readBank);
				String[] readId = rs1.getString("dataId").split(":");
				//System.out.println("readID:"+readId[2]);
				readById = readId[2];
				if(readById.equals("1101")){
					readList.add(rs1.getString("data"));
				}
				i++;
			}
			/*
			开始选取初始Kmer(firstKmer) 先坐1101序列
			1.先取出第一个data找与它前22个相同的read,用一个dataReConunt记录，若是后面的比当前的大就覆盖
			2.并且返回这些相同read的数组序号
			*/
			String dataCut1 = "";
			String dataCut2 = "";
			ArrayList<Reading> finalList= new ArrayList<Reading>();
			ArrayList<Reading> middleList1= new ArrayList<Reading>();
			ArrayList<Reading> middleList2= new ArrayList<Reading>();
			int finalCount = 0;
			int middleCount1 = 0;
			int middleCount2 = 0;
			Reading middleReading = new Reading();
			for(int j = 0;j<readList.size();j++){
				dataCut1 = readList.get(j).substring(0,8);
				
				for(int k = 0 ;k<readList.size();k++){
					dataCut2 =  readList.get(k).substring(0,8);
					if(dataCut2.equals(dataCut1)){
						middleReading.setData(readList.get(k));
						middleReading.setPos(0);
						middleReading.setDel_flag("0");
						middleReading.setIndex(k);
						middleList1.add(middleCount1,middleReading);
						middleCount1++;
					}
				}
				middleCount2 = middleCount1;
				middleCount1 = 0;
				middleList2 = (ArrayList<Reading>) middleList1.clone();
				middleList1.clear();
				if(finalCount<middleCount2){
					finalCount = middleCount2;
					finalList = (ArrayList<Reading>)middleList2.clone();
				}
			}
			curKmer = new Kmer();
			curKmer.setAddr(0);
			curKmer.setCur(-1);
			curKmer.setKmer_seq(finalList.get(0).getData().substring(0,8));
			curKmer.setNum(finalCount);
			System.out.println("初始Kmer:"+curKmer.getKmer_seq());
			contig = finalList.get(0).getData().substring(0,8);
			System.out.println("contig="+contig);
			return finalList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		}
	
	public ArrayList<Reading> getNextKmer(ArrayList<Reading> initKmer){
		if (initKmer.isEmpty()) {
			return null;
		}
		int count[] = new int[4];
		int countMax = 0;
		String checkCount = null;
		
		int pos = initKmer.get(0).getPos();
			String subString= initKmer.get(0).getData().substring(pos+2,pos+8);
			for (int i = 0; i < readList.size(); i++) {
				String readData = readList.get(i);
				if(readData.substring(0,6).equals(subString)){
					if(readData.substring(7,8).equals("00")){
						count[0]++;
					}else if(readData.substring(7,8).equals("01")){
						count[1]++;
					}else if(readData.substring(7,8).equals("10")){
						count[2]++;
					}else if(readData.substring(7,8).equals("11")){
						count[3]++;
					}
				}
			}
			countMax=Math.max(Math.max(count[0],count[1]), Math.max(count[2],count[3]));
			if(countMax == 0){
				return null;
			}
			for(int k = 0;k<count.length;k++ ){
				if(countMax == count[k]){
					if(k==0){
						checkCount = "00";
					}else if(k==1){
						checkCount = "01";
					}else if(k==2){
						checkCount = "10";
					}else if(k==3){
						checkCount = "11";
					}
				}
			}
			for (int i = 0; i < initKmer.size(); i++) {
				Reading reading = initKmer.get(i);
				int initPos = reading.getPos();
				String initData = reading.getData();
				if (initPos+8>initData.length() || !initData.substring(initPos+6,initPos+8).equals(checkCount)) {
					initKmer.remove(i);
				}else {
					initKmer.get(i).setPos(initPos+2);
				}
			}
			Reading middleReading = new Reading();
			for (int i = 0; i < readList.size(); i++) {
				if (readList.get(i).startsWith(subString+checkCount)) {
					middleReading.setData(readList.get(i));
					middleReading.setPos(0);
					middleReading.setDel_flag("0");
					middleReading.setIndex(i);
					initKmer.add(middleReading);
				}
			}
			curKmer.setKmer_seq(subString+checkCount);
			curKmer.setNum(countMax);
			System.out.println("当前Kmer:"+curKmer.getKmer_seq());
			contig = contig + checkCount;
			System.out.println("contig:"+contig);
			
		return initKmer;
	}
	
	public void main(String[] args) {
		AssemblyCenter ac =  new AssemblyCenter();
		ArrayList<Reading> initKmer =ac.getFirstKmer();
		while (initKmer!=null) {
			initKmer = ac.getNextKmer(initKmer);
		}
	}
}
