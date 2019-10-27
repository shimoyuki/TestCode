package com.cardinal.model;

public class Employee {
	private int id;
	private String name;
	private int depId;
	private int eduId;
	private String post;
	private float salary;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDepId() {
		return depId;
	}
	public void setDepId(int depId) {
		this.depId = depId;
	}
	public int getEduId() {
		return eduId;
	}
	public void setEduId(int eduId) {
		this.eduId = eduId;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public Employee() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public Employee(int id, String name, int depId, int eduId, String post,
			float salary) {
		super();
		this.id = id;
		this.name = name;
		this.depId = depId;
		this.eduId = eduId;
		this.post = post;
		this.salary = salary;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + depId;
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
		Employee other = (Employee) obj;
		if (depId != other.depId)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", depId=" + depId
				+ ", eduId=" + eduId + ", post=" + post + ", salary=" + salary
				+ "]";
	}
	
}
