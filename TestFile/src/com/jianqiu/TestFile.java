package com.jianqiu;
import java.io.*;
import static  java.lang.System.out;

public class TestFile {

	public static void main(String[] args) {
		/*String separator = File.separator;
		String filename = "TestFile.txt";
		String directory = "Dir1" +  separator + "Dir2";
		File testFile = new File(directory,filename);
		if(testFile.exists()){
			out.println("Filename:" + testFile.getAbsolutePath());
			out.println("File Size:" + testFile.length());
		}
		else{
			testFile.getParentFile().mkdirs();
			try{
				testFile.createNewFile();
			}catch(IOException io){
				io.printStackTrace();
			}
		}*/
		File f = new File("D:\\EclipseLunaProject\\TestFile");
		Filelist.fileTree(f);
	}

}

class Filelist{
	public static void fileTree(File f){
		File[] childs = f.listFiles();
		out.print(f.getName() + "\\");
		for (int i = 0; i < childs.length; i ++){
			if(childs[i].isDirectory())
				fileTree(childs[i]);
			if(childs[i].isFile())
				out.println(childs[i].getName());
		}
	}
}