package com.cardinal.calendar;

import java.util.Calendar;

import static java.lang.System.out;

public class TestCalendar {
	
	public static boolean isCorrectDate(int y, int m, int d){
		Calendar c = Calendar.getInstance();
		if(y>c.getActualMaximum(Calendar.YEAR) || 
				y<c.getActualMinimum(Calendar.YEAR)){
			out.println("年份非法，应在 " + c.getActualMinimum(Calendar.YEAR) + " 与 " +
					c.getActualMaximum(Calendar.YEAR) + " 之间。");
			return false;
		}
		if(m>c.getActualMaximum(Calendar.MONTH)+1 || 
				m<c.getActualMinimum(Calendar.MONTH)+1){
			out.println("月份非法，应在 " + (c.getActualMinimum(Calendar.MONTH)+1) + " 与 " +
					(c.getActualMaximum(Calendar.MONTH)+1) + " 之间。");
			return false;
		}
		if(d>c.getActualMaximum(Calendar.DATE) || 
				d<c.getActualMinimum(Calendar.DATE)){
			out.println("日期非法，应在 " + c.getActualMinimum(Calendar.DATE) + " 与 " +
					c.getActualMaximum(Calendar.DATE) + " 之间。");
			return false;
		}
		out.println("日期 "+y+"-"+m+"-"+d+" 合法。");
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
		out.print("两日期之差为：");
		out.println(TestCalendar.daysBetween(c1, c2));
		out.print("今天往后10天的日期为：");
		out.println(TestCalendar.dayGoesOn(Calendar.getInstance(), 10).getTime());
	}

}


