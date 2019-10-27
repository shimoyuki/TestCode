package com.cardinal.shape;

import static java.lang.System.out;

public class TestShape {

	public static void main(String[] args) {
		Shape s = new Circle(5);
		out.println("Բ����� " + s.getArea() + "\tԲ�ܳ���" + s.getLengtn());
		s = new Rectangle(4, 6);
		out.println("���������" + s.getArea() + "\t�����ܳ���" + s.getLengtn());
	}

}

abstract class Shape{
	private String name;
	abstract public double getArea();
	abstract public double getLengtn();
}

class Circle extends Shape{

	private double radius;
	
	public Circle(double radius) {
		super();
		this.radius = radius;
	}

	@Override
	public double getArea() {
		return ((int)(100 * Math.PI * Math.pow(this.radius, 2)))/100d;
	}

	@Override
	public double getLengtn() {
		return ((int)(200 * Math.PI * this.radius))/100d;
	}
	
}

class Rectangle extends Shape{
	private double width;
	private double length;
	
	public Rectangle(double width, double length) {
		super();
		this.width = width;
		this.length = length;
	}
	
	@Override
	public double getArea() {
		return this.length * this.width;
	}
	@Override
	public double getLengtn() {
		return 2*(this.length + this.width);
	}
	
	
}