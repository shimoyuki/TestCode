package com.cardinal.saveon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class TestSaveOn {

	public static void main(String[] args) {
		File parent = new File("D:/Project/EclipseLunaProject/Exp7_3/src/com/cardinal/saveon");
		File fIn = new File(parent,"TestSaveOn.java");
		File fOut = new File(parent,"out.txt");
		if(!fOut.exists()){
			try {
				fOut.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			fOut.delete();
			try {
				fOut.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String str = "";
		try {
			Scanner sc = new Scanner(fIn);
			while(sc.hasNextLine()){
				str = sc.nextLine();
				try {
					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(fOut, true)));
					bw.write(str);
					bw.newLine();
					bw.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			sc.close();
			System.out.println("¸´ÖÆ³É¹¦£¡");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
