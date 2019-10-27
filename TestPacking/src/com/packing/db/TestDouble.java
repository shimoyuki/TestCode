package com.packing.db;

public class TestDouble {

	public static void main(String[] args) {
		String str = "1,2;3,4,5;6,7,8";
		String[] str1 = str.split(";");
		String[][] str2 = new String[str1.length][];
		double[][] d = new double[str1.length][];
		//try {
			for (int i = 0; i < str1.length; i++) {
				String[] str0 = str1[i].split(",");
				str2[i] = new String[str0.length];
				for (int j = 0; j < str0.length; j++) {
					str2[i][j] = str0[j];
					System.out.print(str2[i][j] + " ");
				}
				System.out.println();
			}
			for (int i = 0; i < str2.length; i++) {
				d[i] = new double[str2[i].length];
				for (int j = 0; j < str2[i].length; j++) {
					d[i][j] = Double.parseDouble(str2[i][j]);
					System.out.print(d[i][j] + "  ");
				}
				System.out.println();
			}
/*		} catch (NullPointerException np) {
			np.printStackTrace();
		}*/

	}

}
