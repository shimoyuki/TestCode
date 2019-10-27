package com.guanzhiwei;
import java.io.*;
import java.net.URL;
import java.sql.*;
public class DataInput {

	/**
	 *A=00
	 *C=01
	 *G=10
	 *T=11
	 */
	public static void main(String[] args) throws SQLException,ClassNotFoundException{
		// TODO Auto-generated method stub

		URL resUrl = DataInput.class.getClassLoader().getResource("");
		//File file=new File(resUrl.getPath()+"res/","01.clean");
		File file = new File(resUrl.getPath(),"res/01.clean");
		/*File file2=new File("D:\\基因数组","oo.txt");*/
		String []a = new String[60000];
		String []b = new String[60000];
		String []c = new String[60000];
		DataInput myData = new DataInput();
		try{
			FileReader inOne = new FileReader (file);
			BufferedReader inTwo=new BufferedReader(inOne);
			/*FileWriter outOne=new FileWriter(file2);*/
			/*BufferedWriter outTwo=new BufferedWriter(outOne);*/
			String s=null;
			int i;
			int j;
			int k;
			int p;
			for(j=0,k=0,p=0,i=1;(s=inTwo.readLine())!=null;i++){
				if(i%4 == 1){
					a[j] = s;
					j++;
				}else if(i%4 == 2){
					b[k] = myData.StringToByte(s);
					k++;
				}else if(i%4 == 0){
					c[p] = s;
					p++;
				}
		/*	System.out.println(i+":"+s);
			outTwo.write(i+":"+s);//write()函数用法▲
			outTwo.newLine();*/
			}
			inOne.close();
			inTwo.close();
	/*		outTwo.flush();//▲
			outOne.close();
			outTwo.close();*/
		}
		catch(Exception e){
			System.out.println(e);
		}
		for(int i = 0;i<100;i++)
			System.out.println("a["+i+"]"+a[i]);
		for(int i = 0;i<100;i++){
			System.out.println(b[i]);
		}
		System.out.println("B length:"+b.length );
		System.out.println("A length:"+a.length);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/chuck?user=root&password=kagari8243";
			Connection conn =DriverManager.getConnection(url);
			//Statement stmt = conn.createStatement();
			
			String sql = "";
			sql ="insert into gene(dataId,data,quality) values(?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			for(int i = 0;i<a.length;i++){
				//sql = "insert into datasave(dataId,data,quality) values('"+a[i]+"','"+b[i]+"','"+c[i]+"')";
				pstmt.setString(1,a[i]);
				pstmt.setString(2,b[i]);
				pstmt.setString(3,c[i]);
				pstmt.executeUpdate();
				//stmt.executeUpdate(sql);
			}
			//stmt.close();
			pstmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("a1:"+a[1]);
	}
	public String StringToByte(String str){
		String reString="";
		char c[] = str.toCharArray();
		for(int i=0;i<c.length;i++){
			if(c[i]=='A'){
				reString +="00";
			}else if(c[i]=='C'){
				reString +="01";
			}else if(c[i]=='G'){
				reString +="10";
			}else if(c[i]=='T'){
				reString +="11";
			}
		}
		return reString;
	}

}

