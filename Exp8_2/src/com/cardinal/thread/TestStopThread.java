package com.cardinal.thread;
import static java.lang.System.out;

class Message implements Runnable {

	private String message = "";
	private boolean flag = true;
	private int sleepingTime = 0;
	private int count = 0;
	
	public Message(String message, int st) {
		this.message = message;
		this.sleepingTime = st;
	}

	@Override
	public void run() {
		
		while(flag){
			if(count > 8){
				this.stop();
			}
			out.print(message+(count+1)+" ");
			count ++;
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

public class TestStopThread {
	public static void main(String[] args) {
		Message ma = new Message("A", 500);
		Message mb = new Message("B", 500);
		Message mc = new Message("C", 500);
		/*new Thread(ma).start();
		new Thread(mb).start();
		new Thread(mc).start();*/
		Thread ta = new Thread(ma),tb = new Thread(mb),tc = new Thread(mc);
		ta.start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tb.start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tc.start();
		
	}
}