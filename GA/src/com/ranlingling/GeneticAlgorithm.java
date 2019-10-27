package com.ranlingling;

import java.util.Random;

class GeneticAlgorithm{
	
    double[][] d = { { 0, 4, 6, 7.5, 9, 20, 10, 16, 8 },  
            { 4, 0, 6.5, 4, 10, 5, 7.5, 11, 10 },  
            { 6, 6.5, 0, 7.5, 10, 10, 7.5, 7.5, 7.5 },  
            { 7.5, 4, 7.5, 0, 10, 5, 9, 9, 15 },  
            { 9, 10, 10, 10, 0, 10, 7.5, 7.5, 10 },  
            { 20, 5, 10, 5, 10, 0, 7, 9, 7.5 },  
            { 10, 7.5, 7.5, 9, 7.5, 7, 0, 7, 10 },  
            { 16, 11, 7.5, 9, 7.5, 9, 7, 0, 10 },  
            { 8, 10, 7.5, 15, 10, 7.5, 10, 10, 0 } };  
    double[] q = { 0, 1, 2, 1, 2, 1, 4, 2, 2 };  
  
    Random random = new Random();  
  
    int rows;  
    int time;  
    int mans;  
    int cars;  
    int tons;  
    int dis;  
    int PW;  
  
    double JCL = 0.9;  
    double BYL = 0.07;  
    int JYHW = 8;  
    int PSCS = 18;  
  
    /** 
     * 
     * @param rows 
     *            ���и��� 
     * @param time 
     *            �������� 
     * @param mans 
     *            �ͻ����� 
     * @param cars 
     *            �������� 
     * @param tons 
     *            �������� 
     * @param distance 
     *            ���������ʻ���� 
     * @param PW 
     *            �ͷ����� 
     * 
     */  
    public GeneticAlgorithm(int rows, int time, int mans, int cars, int tons,  
            int distance, int PW) {  
        this.rows = rows;  
        this.time = time;  
        this.mans = mans;  
        this.cars = cars;  
        this.tons = tons;  
        this.dis = distance;  
        this.PW = PW;  
    }  
  
    public String run() {  
        int[][] lines = new int[rows][mans];  
        // ��Ӧ��  
        double[] fit = new double[rows];  
  
        // ��ȡrows��������У���������Ӧ��  
        int j = 0;  
        for (int i = 0; i < rows; i++) {  
            j = 0;  
            while (j < mans) {  
                int num = random.nextInt(mans) + 1;  
                if (!isHas(lines[i], num)) {  
                    lines[i][j] = num;  
                    j++;  
  
                    // System.out.print(num + ",");  
                }  
            }  
  
            // System.out.println();  
  
            fit[i] = calFitness(lines[i]);  
  
            // System.out.println(fit[i]);  
        }  
  
        int t = 0;  
  
        while (t < time) {  
            int[][] nextLines = new int[rows][mans];  
            // ��Ӧ��  
            double[] nextFit = new double[rows];  
  
            double[] ranFit = new double[rows];  
            double totalFit = 0, tempFit = 0;  
  
            for (int i = 0; i < rows; i++) {  
                totalFit += fit[i];  
            }  
            for (int i = 0; i < rows; i++) {  
                ranFit[i] = tempFit + fit[i] / totalFit;  
                tempFit += ranFit[i];  
            }  
  
            // �ϴ�����ֱ�ӵ���һ��  
            double m = fit[0];  
            int ml = 0;  
  
            for (int i = 0; i < rows; i++) {  
                if (m < fit[i]) {  
                    m = fit[i];  
                    ml = i;  
                }  
            }  
  
            for (int i = 0; i < mans; i++) {  
                nextLines[0][i] = lines[ml][i];  
            }  
            nextFit[0] = fit[ml];  
  
            // ����ʹ����ɽ�㷨  
            clMountain(nextLines[0]);  
  
            int nl = 1;  
  
            while (nl < rows) {  
                // ���ݸ���ѡȡ����  
                int r = ranSelect(ranFit);  
  
                // �ж��Ƿ񽻲� ���ܳ�������  
                if (random.nextDouble() < JCL && nl + 1 < rows) {  
                    int[] fLine = new int[mans];  
                    int[] nLine = new int[mans];  
  
                    // ��ȡ��������  
                    int rn = ranSelect(ranFit);  
  
                    // ��ý���Ķ�  
                    int f = random.nextInt(mans);  
                    int l = random.nextInt(mans);  
                    int min, max, fpo = 0, npo = 0;  
  
                    if (f < l) {  
                        min = f;  
                        max = l;  
                    } else {  
                        min = l;  
                        max = f;  
                    }  
  
                    // ����ȡ�Ķμ��������ɵĻ���  
                    while (min <= max) {  
                        fLine[fpo] = lines[rn][min];  
                        nLine[npo] = lines[r][min];  
  
                        min++;  
                        fpo++;  
                        npo++;  
                    }  
  
                    for (int i = 0; i < mans; i++) {  
                        if (!isHas(fLine, lines[r][i])) {  
                            fLine[fpo] = lines[r][i];  
                            fpo++;  
                        }  
  
                        if (!isHas(nLine, lines[rn][i])) {  
                            nLine[npo] = lines[rn][i];  
                            npo++;  
                        }  
                    }  
  
                    // �������  
                    change(fLine);  
                    change(nLine);  
  
                    // ������һ��  
                    for (int i = 0; i < mans; i++) {  
                        nextLines[nl][i] = fLine[i];  
                        nextLines[nl + 1][i] = nLine[i];  
                    }  
  
                    nextFit[nl] = calFitness(fLine);  
                    nextFit[nl + 1] = calFitness(nLine);  
  
                    nl += 2;  
                } else {  
                    int[] line = new int[mans];  
  
                    int i = 0;  
                    while (i < mans) {  
                        line[i] = lines[r][i];  
  
                        i++;  
                    }  
  
                    // �������  
                    change(line);  
  
                    // ������һ��  
                    i = 0;  
                    while (i < mans) {  
                        nextLines[nl][i] = line[i];  
  
                        i++;  
                    }  
  
                    nextFit[nl] = calFitness(line);  
  
                    nl++;  
                }  
            }  
  
            // �µ�һ��������һ��  
            for (int i = 0; i < rows; i++) {  
                for (int h = 0; h < mans; h++) {  
                    lines[i][h] = nextLines[i][h];  
                }  
                fit[i] = nextFit[i];  
            }  
  
            t++;  
        }  
  
        // �ϴ�����  
        double m = fit[0];  
        int ml = 0;  
  
        for (int i = 0; i < rows; i++) {  
            if (m < fit[i]) {  
                m = fit[i];  
                ml = i;  
            }  
        }  
  
//      System.out.println("���Ž��Ϊ:");  
//      for (int i = 0; i < mans; i++) {  
//          System.out.print(lines[ml][i] + ",");  
//      }  
//      System.out.println();  
  
        return showResult(lines[ml]);  
    }  
  
    /** 
     * ��ɽ�㷨 
     * 
     * @param line 
     */  
    public void clMountain(int[] line) {  
        double oldFit = calFitness(line);  
        int i = 0;  
        while (i < PSCS) {  
            int f = random.nextInt(mans);  
            int n = random.nextInt(mans);  
  
            change(line, f, n);  
  
            double newFit = calFitness(line);  
  
            if (newFit < oldFit) {  
                change(line, f, n);  
            }  
  
            i++;  
        }  
    }  
  
    /** 
     * ������� 
     * 
     * @param line 
     */  
    public void change(int[] line) {  
        if (random.nextDouble() < BYL) {  
            int i = 0;  
            while (i < JYHW) {  
                int f = random.nextInt(mans);  
                int n = random.nextInt(mans);  
  
                change(line, f, n);  
  
                i++;  
            }  
        }  
    }  
  
    public void change(int[] line, int f, int n) {  
        int temp = line[f];  
  
        line[f] = line[n];  
        line[n] = temp;  
    }  
  
    /** 
     * ѡ����������� 
     * 
     * @param ranFit 
     * @return 
     */  
    public int ranSelect(double[] ranFit) {  
        double ran = random.nextDouble();  
  
        for (int i = 0; i < rows; i++) {  
            if (ran < ranFit[i])  
                return i;  
        }  
        System.out.println("ERROR!!! get ranSelect Error!");  
        return 0;  
    }  
  
    /** 
     * ������Ӧ�� 
     * 
     * @param line 
     * @return 
     */  
    public double calFitness(int[] line) {  
        double carTon = 0;  
        double carDis = 0;  
        double newTon = 0;  
        double newDis = 0;  
        double totalDis = 0;  
        // Ĭ��Ϊ2��  
        int[][] ll = new int[cars * 2][mans];  
        int r = 0, l = 0, fore = 0, M = 0;  
        for (int i = 0; i < mans; i++) {  
            // ����  
            newDis = carDis + d[fore][line[i]];  
            // ����  
            newTon = carTon + q[line[i]];  
            // �����������룬����  
            if ((newDis + d[line[i]][0]) > dis || newTon > tons) {  
                // ��һ����  
                totalDis += carDis + d[fore][0];  
                l = 0;  
                r++;  
                fore = 0;  
                i--;  
                carTon = 0;  
                carDis = 0;  
            } else {  
                carDis = newDis;  
                carTon = newTon;  
                fore = line[i];  
  
                ll[r][l] = line[i];  
                l++;  
            }  
        }  
        // �������һ����������  
        totalDis += carDis + d[fore][0];  
  
        // for (int i = 0; i < cars * 2; i++) {  
        // for (int j = 0; j < mans; j++) {  
        // System.out.print(ll[i][j]);  
        // }  
        // System.out.println();  
        // }  
        // System.out.println("��·������Ϊ:" + totalDis);  
  
        if ((r - cars + 1) > 0)  
            M = r - cars + 1;  
  
        // Ŀ�꺯��  
        double result = 1 / (totalDis + M * PW);  
  
        return result;  
    }  
  
    public String showResult(int[] line) {  
        double carTon = 0;  
        double carDis = 0;  
        double newTon = 0;  
        double newDis = 0;  
        double totalDis = 0;  
        // Ĭ��Ϊ2��  
        int[][] ll = new int[cars * 2][mans];  
        int r = 0, l = 0, fore = 0, M = 0;  
        for (int i = 0; i < mans; i++) {  
            // ����  
            newDis = carDis + d[fore][line[i]];  
            // ����  
            newTon = carTon + q[line[i]];  
            // �����������룬����  
            if ((newDis + d[line[i]][0]) > dis || newTon > tons) {  
                // ��һ����  
                totalDis += carDis + d[fore][0];  
                l = 0;  
                r++;  
                fore = 0;  
                i--;  
                carTon = 0;  
                carDis = 0;  
            } else {  
                carDis = newDis;  
                carTon = newTon;  
                fore = line[i];  
  
                ll[r][l] = line[i];  
                l++;  
            }  
        }  
        // �������һ����������  
        totalDis += carDis + d[fore][0];  
  
        StringBuffer sb = new StringBuffer();  
  
        for (int i = 0; i < cars; i++) {  
            for (int j = 0; j < mans; j++) {  
              System.out.print(ll[i][j]);  
                sb.append(ll[i][j]);  
            }  
          System.out.println();  
            sb.append("\n");  
        }  
        System.out.println("��·������Ϊ:" + totalDis);  
        sb.append("��·������Ϊ:" + totalDis + "\n");  
  
        if ((r - cars + 1) > 0)  
            M = r - cars + 1;  
  
        // Ŀ�꺯��  
        double result = 1 / (totalDis + M * PW);  
  
//      System.out.println("����ȨֵΪ:" + result);  
        sb.append("����ȨֵΪ:" + result + "\n");  
  
        return sb.toString();  
    }  
  
    public boolean isHas(int[] line, int num) {  
        for (int i = 0; i < mans; i++) {  
            if (line[i] == num)  
                return true;  
        }  
  
        return false;  
    }  
  
    public static void main(String[] args) {  
        GeneticAlgorithm ga = new GeneticAlgorithm(20, 25, 8, 2, 8, 50, 100);  
  
        for (int i = 0; i < 10; i++) {  
            System.out.println("��" + (i + 1) + "��:");  
            long begin = System.currentTimeMillis();  
            ga.run();  
            long end = System.currentTimeMillis();  
            System.out.println("ʹ��ʱ��" + (end - begin) + "����");  
            System.out.println();  
        }  
    }  
}