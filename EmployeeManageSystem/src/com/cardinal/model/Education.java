package com.cardinal.model;

public class Education {
	private int id;
	private String degree;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Education() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public Education(int id, String degree) {
		super();
		this.id = id;
		this.degree = degree;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Education other = (Education) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Education [id=" + id + ", degree=" + degree + "]";
	}
	
}
