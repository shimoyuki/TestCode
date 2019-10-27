package com.cardinal.account;

class Account{
	private String id;
	private String password;
	private double balance;

	public Account(String id, String password) {
		super();
		this.id = id;
		this.password = password;
		this.balance = 0;
	}

	public double getBalance() {
		return balance;
	}

	public String getId() {
		return id;
	}
	
	public int getLeftMoney(){
		return (int)this.getBalance();
	}

	public void getMoney(double money){
		if(money<=this.getBalance()){
			this.setBalance(this.getBalance()-money);
		}else{
			System.out.println("余额不足，账户当前余额为: "+this.getBalance());
		}
	}

	public String getPassword() {
		return password;
	}

	public void saveMoney(double money){
		this.setBalance(money+this.getBalance());
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}

public class TestAccount {

	public static void main(String[] args) {
		Account ba=new Account("888123","1000");
		ba.saveMoney(21000);
		System.out.println("存入21000元后余额为："+ba.getLeftMoney());
		ba.getMoney(11500);
		System.out.println("取出11500元后余额为："+ba.getLeftMoney());

	}

}
