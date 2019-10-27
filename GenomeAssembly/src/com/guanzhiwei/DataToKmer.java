package com.guanzhiwei;

import java.util.*;
import java.sql.*;

public class DataToKmer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException,ClassNotFoundException{
		// TODO Auto-generated method stub
		/*HashMap hs =new HashMap();*/
		/*
		 * 建立两个集合一个存储read 一个存储Kmer
		 * 
		 * */
/*		String sql1 = "";*/
		String contig = "";
		ReadBank []readBanks =new ReadBank [46847];
		Read read[] = new Read[46847];
		HashMap hsToRead = new HashMap();
		List<ReadBank> bankList = new ArrayList<ReadBank>();
		String getRead = "";
		//初始Kmer
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url= "jdbc:mysql://localhost/chuck?user=root&password=1234";
			Connection conn =DriverManager.getConnection(url);
			ResultSet rs1;
			Statement stmt = conn.createStatement();
			PreparedStatement pstmt;
			String sql = "select count(*) from gene";
			int datalength = 0;
			rs1 = stmt.executeQuery(sql);
			if(rs1.next())
				datalength = Integer.parseInt(rs1.getString("count(*)"));
			System.out.println("datalength="+datalength);
			sql = "select * from gene";
			rs1 = stmt.executeQuery(sql);
			//该数组用来判定是否为该序列
			/*String[] readId ;*/
			String readById = "";
			//该数组用来存取第一个data的值（data）
			String[] readFirst = new String [386]; 
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
					readFirst[i] =rs1.getString("data");
					System.out.println("第"+i+"个data为"+readFirst[i]);
				}
				i++;
				/*System.out.println(rs1.getString("data"));*/
			}
			/*			System.out.println("list46847="+bankList.get(46846).readData);*/
			//开始选取初始Kmer(firstKmer) 先坐1101序列
			/*1.先取出第一个data找与它前22个相同的read,用一个dataReConunt记录，若是后面的比当前的大就覆盖
			 *2.并且返回这些相同read的数组序号
			*/
			String dataCut1 = "";
			String dataCut2 = "";
			ArrayList<Reading> finalList= new ArrayList<Reading>();
			ArrayList<Reading> middleList1= new ArrayList<Reading>();
			ArrayList<Reading> middleList2= new ArrayList<Reading>();
			int finalCount = 0;
			int middleCount1 = 0;
			int middleCount = 0;
			int[]
					index = new int[100],finalIndex = new int[100];
			Reading firstReading = new Reading();
			Reading middleReading = new Reading();
			for(int j = 0;j<readFirst.length;j++){
				dataCut1 = readFirst[j].substring(0,8);
				
				for(int k = 0 ;k<readFirst.length && j!=i ;k++){
					dataCut2 =  readFirst[k].substring(0,8);
					if(dataCut2.equals(dataCut1)){
/*		 if(middleList1.size()==0){
							firstReading.setData(dataCut1);
							middleList1.add(middleCount,firstReading);
							index[middleCount] = j;
							middleCount++;
							
						}*/
						middleReading.setData(readFirst[k]);
						middleReading.setPos("1");
						middleReading.setDel_flag("0");
						middleList1.add(middleCount,middleReading);
						index[middleCount] = k;
						middleCount++;
					}
				}
				middleCount1 = middleCount;
				middleCount = 0;
				middleList2 = (ArrayList<Reading>) middleList1.clone();
				middleList1.clear();
				if(finalCount<middleCount1){
					finalCount = middleCount1;
					finalList = (ArrayList<Reading>)middleList2.clone();
					finalIndex = index.clone();
				}
			}
			System.out.println("初始Kmer:"+finalList.get(0).getData()+"length:"+finalList.get(0).getData().length());

			//初始Kmer设定,并且定义Kmer库
			
			ArrayList<Kmer> kmerBanking = new ArrayList<Kmer>();
			Kmer firstKmer = new Kmer();
			firstKmer.setKmer_seq(finalList.get(0).getData());
			firstKmer.setNum(String.valueOf(finalCount));
			firstKmer.setCur("-1");
			firstKmer.setAddr("0");
			kmerBanking.add(firstKmer);
			
			ArrayList<Read> readBanking = new ArrayList<Read>();
			for(int l = 0;l<finalList.size();l++ ){
				Read read1 = new Read();
				read1.setPos(String.valueOf(index[l]));
				//0表示正在参与拼接1表示使用完毕已删除
				System.out.println(index[l]);
				read1.setDel_flag("0");
				read1.setReadData(readFirst[l]);
				readBanking.add(read1);
			}// maybe has some problem
			/*
			 * 开始选择后继的Kmer
			 * */
			//此处为判定read库中是否为空
			Kmer kmering = firstKmer;
			String checkNull = "";
			String checkCount = "";
			int KmerCount ;
			
			
			/*;*/
			int count[] = new int[4];
			int countMax = 0;
			int myIndex = 0;
			int Pos = 2;
			for(int l = 0;l<index.length;l++){
				contig+= readFirst[l];
			}
			System.out.println("contig="+contig);
			for(int m = 0;m<readFirst.length;m++){
				if(readFirst[m].equals("1")){
					checkNull = "ok";
				}else{
					checkNull = "lose";
				}
			}
			
				for(int m = 0;m<readFirst.length;m++){
					if(readFirst[m].equals("1")){
						checkNull = "ok";
					}else{
						checkNull = "lose";
					}
				}
				KmerCount = readFirst.length-4;
				System.out.println("Kmer共有:"+KmerCount);
				for(int m = 0;m<readFirst.length;m++){
					String subString= kmerBanking.get(Integer.parseInt((kmering.getAddr()))).getKmer_seq().substring(2,8);
					if(readFirst[m].substring(0,6).equals(subString)){
						if(readFirst[m].substring(7,8).equals("00")){
							count[0]++;
						}else if(readFirst[m].substring(7,8).equals("01")){
							count[1]++;
						}else if(readFirst[m].substring(7,8).equals("10")){
							count[2]++;
						}else if(readFirst[m].substring(7,8).equals("11")){
							count[3]++;
						}
						countMax=Math.max(Math.max(count[0],count[1]), Math.max(count[2],count[3]));
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
						kmering.setKmer_seq(subString+checkCount);
						String addString = subString+checkCount;
						for(int k=0;k<readFirst.length;k++ ){
							if(readFirst[k].equals(addString)){
								int []index1 = new int[10];
								index1[myIndex] = k;
								myIndex++;
								middleCount++;
							}
						}
	/*		   for(int l=0;l<readFirst.length;l++){
							readFirst[l]="1";
						}*/
						for(int p = 0;p<middleCount;p++){
							middleReading.setData(dataCut2);
							middleReading.setPos(String.valueOf(Pos));
							middleReading.setDel_flag("0");
							middleList1.add(middleReading);
							for(int q=0;q<finalList.size();q++){
								if(88-Integer.parseInt(finalList.get(q).getPos())==1){
									finalList.get(q).setDel_flag("1");

								}
							}
							finalList.add(middleReading);
						}
					}
				}
				for(int m = 0;m<readFirst.length;m++){
					if(readFirst[m].equals("1")){
						checkNull = "ok";
					}else{
						checkNull = "lose";
					}
				}
			
			/*System.out.println(finalList.get(3).getData());*/
			/*System.out.println(finalList.get(1).getData());*/
			//将正在参与拼接的read从read库中清除
			for(int l=0;l<index.length;l++){
				readFirst[index[l]]="1";
			}
			
		/*	int geneArray [] = new int[100];*/
/*	String dataCut1 = "";
			String dataCut2 = "";
			
			Reading firstReading = new Reading();
			List<Reading> indexSaveArray = new ArrayList<Reading>();
			for(Integer j = 0;j<readFirst.length;j++){
				int dataReCount = 0;
				int index = 0;
				List<Reading> indexSaveArray2 = new ArrayList<Reading>();
				int dataReCount2 = 0;
				dataCut1 = readFirst[j].substring(0,43);
				firstKmer.setKmer_seq(dataCut1);
				firstKmer.setAddr("0");
				firstReading.setData(dataCut1);
				for(Integer k=0;k<readFirst.length && j!=k;k++ ){
					dataCut2 = readFirst[k].substring(0,43);
					if(dataCut1.equals(dataCut2) && dataReCount2>dataReCount){
						Reading reading = new Reading();
						reading.setData(dataCut2);
						if(indexSaveArray2.get(0)==null){
							indexSaveArray2.add(index,firstReading);
							index++;
						}		
						dataReCount2++;
						indexSaveArray2.add(index, reading);
						index++;
						System.out.println(dataCut2);
					}
				}
				if(dataReCount2>dataReCount){
					dataReCount = dataReCount2;
				}*/
			/*	indexSaveArray = indexSaveArray2;*/
			/*}*/
		/*	System.out.println(indexSaveArray.get(0));*/
			rs1.close();
			stmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}

