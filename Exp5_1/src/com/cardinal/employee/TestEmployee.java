package com.cardinal.employee;

class BasePlusSalesEmployee extends SalesEmployee{
    private double baseSalary;
    public BasePlusSalesEmployee(String name, int birth, double sales, double pre, double baseSalary){
        super(name, birth, sales, pre);
        this.baseSalary = baseSalary;
    }
    public double getSalary(int month){
    	this.setOvertime(1000);
        return baseSalary+super.getSalary(month);
    }
}

class Employee{
	
    public static double getWholeOvertime() {
		return wholeOvertime;
	}
    public static void setWholeOvertime(double wholeOvertime) {
		Employee.wholeOvertime = wholeOvertime;
	}
    
    private String name;
    private int birth;
    private double overtime = 0;
	private static double wholeOvertime;
	
	public Employee(String name, int birth){
        this.name = name;
        this.birth = birth;
    }
	
	public String getName(){
        return name;
    }
	
	public double getOvertime() {
		return overtime;
	}
    public double getSalary(int month){
        if(month==birth){
            return 100+this.getOvertime();
        }
        return this.getOvertime();
    }
    public void setOvertime(double overtime) {
		this.overtime = overtime;
		this.setWholeOvertime(overtime + this.getWholeOvertime());
	}
}

class HourlyEmployee extends Employee{
    private double hourSalary;
    private int hour;
    public HourlyEmployee(String name, int birth, double hourSalary, int hour){
        super(name, birth);
        this.hourSalary = hourSalary;
        this.hour = hour;
    }
    public double getSalary(int month){
        if(hour<=160){
            return hourSalary*hour+super.getSalary(month);
        }else{
            return 160*hourSalary+(hour-160)*hourSalary*1.5+super.getSalary(month);
        }
    }
}

class SalariedEmployee extends Employee{
    private double salary;
    public SalariedEmployee(String name, int birth, double salary){
        super(name, birth);
        this.salary = salary;
    }
    public double getSalary(int month){
    	this.setOvertime(2000);
        return salary + super.getSalary(month);
    }
}

class SalesEmployee extends Employee{
    private double sales;
    private double pre;
    public SalesEmployee(String name, int birth, double sales, double pre){
        super(name, birth);
        this.sales = sales;
        this.pre = pre;
    }
    public double getSalary(int month){
        return sales*pre+super.getSalary(month);
    }
}

public class TestEmployee{
    public static void main(String[]args){
        Employee[] es = new Employee[5];
        es[0] = new Employee("�Ծ�",2);
        es[1] = new SalariedEmployee("���", 1, 8000);
        es[2] = new HourlyEmployee("����", 5, 10, 300);
        es[3] = new SalesEmployee("���", 2, 200000, 0.05);
        es[4] = new BasePlusSalesEmployee("�����", 1, 1000000, 0.1, 10000);
        int month = 2;//����Ϊ2��
        System.out.println("���漯��"+month+"�¹��ʱ���");
        for(int i=0; i<es.length; i++){
            System.out.println(es[i].getName()+":"+es[i].getSalary(month));
        }
        System.out.println("�ܼӰ๤��: " + Employee.getWholeOvertime());
    }
}