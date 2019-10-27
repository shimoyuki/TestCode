package com.cardinal.calendar;

import java.util.Calendar;

import static java.lang.System.out;

public class TestCalendar {
	
	public static boolean isCorrectDate(int y, int m, int d){
		Calendar c = Calendar.getInstance();
		if(y>c.getActualMaximum(Calendar.YEAR) || 
				y<c.getActualMinimum(Calendar.YEAR)){
			out.println("��ݷǷ���Ӧ�� " + c.getActualMinimum(Calendar.YEAR) + " �� " +
					c.getActualMaximum(Calendar.YEAR) + " ֮�䡣");
			return false;
		}
		if(m>c.getActualMaximum(Calendar.MONTH)+1 || 
				m<c.getActualMinimum(Calendar.MONTH)+1){
			out.println("�·ݷǷ���Ӧ�� " + (c.getActualMinimum(Calendar.MONTH)+1) + " �� " +
					(c.getActualMaximum(Calendar.MONTH)+1) + " ֮�䡣");
			return false;
		}
		if(d>c.getActualMaximum(Calendar.DATE) || 
				d<c.getActualMinimum(Calendar.DATE)){
			out.println("���ڷǷ���Ӧ�� " + c.getActualMinimum(Calendar.DATE) + " �� " +
					c.getActualMaximum(Calendar.DATE) + " ֮�䡣");
			return false;
		}
		out.println("���� "+y+"-"+m+"-"+d+" �Ϸ���");
		return true;
	}
	
	public static boolean isCorrectDate(Calendar c){
		return TestCalendar.isCorrectDate(c.get(Calendar.YEAR), 
				c.get(Calendar.MONTH)+1, c.get(Calendar.DATE));
	}
	
	public static Calendar dayGoesOn(Calendar c, int d){
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR)+d);
		return c;
	}
	
	public static int daysBetween(Calendar c1, Calendar c2){
		int day1 = c1.get(Calendar.DAY_OF_YEAR), day2 = c2.get(Calendar.DAY_OF_YEAR);
		if(day1 >= day2){
			return day1 - day2;
		}else{
			return day2 - day1;
		}
	}
	
	public static void main(String[] args) {
		Calendar c1 = Calendar.getInstance(), c2 =Calendar.getInstance();
		c2.set(Calendar.YEAR, 2015);
		c2.set(Calendar.MONTH, Calendar.JANUARY);
		c2.set(Calendar.DATE, 30);
		TestCalendar.isCorrectDate(c1);
		TestCalendar.isCorrectDate(c2);
		out.print("������֮��Ϊ��");
		out.println(TestCalendar.daysBetween(c1, c2));
		out.print("��������10�������Ϊ��");
		out.println(TestCalendar.dayGoesOn(Calendar.getInstance(), 10).getTime());
	}

}


