package com.cardinal.reflection;

import static java.lang.System.out;

import java.lang.reflect.*;

public class TestReflection {

	public static void main(String[] args) {
		User user = new User("Yamamoto","00000000");
		Class userC =  user.getClass();
		out.println(userC.getName());
		Field[] f = userC.getDeclaredFields();
		for(Field fi:f){
			try {
				fi.setAccessible(true);
				out.println("��������" + fi.getName() + "    �������ͣ�" + fi.getType() + "    ����ֵ��" + fi.get(user));
				fi.set(user, "newValue");
				out.println("�޸ĺ����ֵ��" + fi.get(user));
			} catch (IllegalArgumentException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		
		Method[] m = userC.getDeclaredMethods();
		for(Method mi:m){
			mi.setAccessible(true);
			out.print("��������" + mi.getName() + "    ����ֵ���ͣ�" + mi.getReturnType() + "    �������ͣ�");
			Class[] p = mi.getParameterTypes();
			for(Class pi:p){
				out.print(pi);
			}
			out.println();
		}
		
	}

}

class User{
	private String username;
	private String password;
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}