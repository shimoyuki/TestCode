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
     *            排列个数 
     * @param time 
     *            迭代次数 
     * @param mans 
     *            客户数量 
     * @param cars 
     *            货车数量 
     * @param tons 
     *            货车载重 
     * @param distance 
     *            货车最大行驶距离 
     * @param PW 
     *            惩罚因子 
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
        // 适应度  
        double[] fit = new double[rows];  
  
        // 获取rows个随机排列，并计算适应度  
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
            // 适应度  
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
  
            // 上代最优直接到下一代  
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
  
            // 最优使用爬山算法  
            clMountain(nextLines[0]);  
  
            int nl = 1;  
  
            while (nl < rows) {  
                // 根据概率选取排列  
                int r = ranSelect(ranFit);  
  
                // 判断是否交叉 不能超出界限  
                if (random.nextDouble() < JCL && nl + 1 < rows) {  
                    int[] fLine = new int[mans];  
                    int[] nLine = new int[mans];  
  
                    // 获取交叉排列  
                    int rn = ranSelect(ranFit);  
  
                    // 获得交叉的段  
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
  
                    // 将截取的段加入新生成的基因  
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
  
                    // 基因变异  
                    change(fLine);  
                    change(nLine);  
  
                    // 加入下一代  
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
  
                    // 基因变异  
                    change(line);  
  
                    // 加入下一代  
                    i = 0;  
                    while (i < mans) {  
                        nextLines[nl][i] = line[i];  
  
                        i++;  
                    }  
  
                    nextFit[nl] = calFitness(line);  
  
                    nl++;  
                }  
            }  
  
            // 新的一代覆盖上一代  
            for (int i = 0; i < rows; i++) {  
                for (int h = 0; h < mans; h++) {  
                    lines[i][h] = nextLines[i][h];  
                }  
                fit[i] = nextFit[i];  
            }  
  
            t++;  
        }  
  
        // 上代最优  
        double m = fit[0];  
        int ml = 0;  
  
        for (int i = 0; i < rows; i++) {  
            if (m < fit[i]) {  
                m = fit[i];  
                ml = i;  
            }  
        }  
  
//      System.out.println("最优结果为:");  
//      for (int i = 0; i < mans; i++) {  
//          System.out.print(lines[ml][i] + ",");  
//      }  
//      System.out.println();  
  
        return showResult(lines[ml]);  
    }  
  
    /** 
     * 爬山算法 
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
     * 基因变异 
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
     * 选择随机的序列 
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
     * 计算适应度 
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
        // 默认为2倍  
        int[][] ll = new int[cars * 2][mans];  
        int r = 0, l = 0, fore = 0, M = 0;  
        for (int i = 0; i < mans; i++) {  
            // 距离  
            newDis = carDis + d[fore][line[i]];  
            // 载重  
            newTon = carTon + q[line[i]];  
            // 超过货车距离，载重  
            if ((newDis + d[line[i]][0]) > dis || newTon > tons) {  
                // 下一辆车  
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
        // 加上最后一辆货车距离  
        totalDis += carDis + d[fore][0];  
  
        // for (int i = 0; i < cars * 2; i++) {  
        // for (int j = 0; j < mans; j++) {  
        // System.out.print(ll[i][j]);  
        // }  
        // System.out.println();  
        // }  
        // System.out.println("总路径长度为:" + totalDis);  
  
        if ((r - cars + 1) > 0)  
            M = r - cars + 1;  
  
        // 目标函数  
        double result = 1 / (totalDis + M * PW);  
  
        return result;  
    }  
  
    public String showResult(int[] line) {  
        double carTon = 0;  
        double carDis = 0;  
        double newTon = 0;  
        double newDis = 0;  
        double totalDis = 0;  
        // 默认为2倍  
        int[][] ll = new int[cars * 2][mans];  
        int r = 0, l = 0, fore = 0, M = 0;  
        for (int i = 0; i < mans; i++) {  
            // 距离  
            newDis = carDis + d[fore][line[i]];  
            // 载重  
            newTon = carTon + q[line[i]];  
            // 超过货车距离，载重  
            if ((newDis + d[line[i]][0]) > dis || newTon > tons) {  
                // 下一辆车  
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
        // 加上最后一辆货车距离  
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
        System.out.println("总路径长度为:" + totalDis);  
        sb.append("总路径长度为:" + totalDis + "\n");  
  
        if ((r - cars + 1) > 0)  
            M = r - cars + 1;  
  
        // 目标函数  
        double result = 1 / (totalDis + M * PW);  
  
//      System.out.println("最终权值为:" + result);  
        sb.append("最终权值为:" + result + "\n");  
  
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
            System.out.println("第" + (i + 1) + "次:");  
            long begin = System.currentTimeMillis();  
            ga.run();  
            long end = System.currentTimeMillis();  
            System.out.println("使用时间" + (end - begin) + "毫秒");  
            System.out.println();  
        }  
    }  
}