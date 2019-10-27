package com.cardinal.thread;
import static java.lang.System.out;

public class TestThread {

	public static void main(String[] args) {
		Message ma = new Message("A", 500);
		Message mb = new Message("B", 300);
		new Thread(ma).start();
		new Thread(mb).start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ma.stop();
		mb.stop();
	}

}

class Message implements Runnable {

	private String message = "";
	private boolean flag = true;
	private int sleepingTime = 0;
	
	public Message(String message, int st) {
		this.message = message;
		this.sleepingTime = st;
	}

	@Override
	public void run() {
		while(flag){
			out.print(message+"  ");
			try {
				Thread.sleep(sleepingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void stop(){
		this.flag = false;
	}
	
}