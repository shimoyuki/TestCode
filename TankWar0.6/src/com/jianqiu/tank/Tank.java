package com.jianqiu.tank;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class Tank {
	enum Direction{
		NORTH,SOUTH,EAST,WEST,NEAST,SEAST,NWEST,SWEST,STOP
	}
	private boolean up=false ,down=false ,left=false, right=false;
	private int x, y, xSpeed=2, ySpeed=2;
	private Direction dir = Direction.STOP;
	private Image img;
	

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void move(){
		switch(dir){
		case NORTH:
			y -= ySpeed;
			break;
		case SOUTH:
			y += ySpeed;
			break;
		case EAST:
			x += xSpeed;
			break;
		case WEST:
			x -= xSpeed;
			break;
		case NEAST:
			y -= ySpeed;
			x += xSpeed;
			break;
		case SEAST:
			y += ySpeed;
			x += xSpeed;
			break;
		case NWEST:
			y -= ySpeed;
			x -= xSpeed;
			break;
		case SWEST:
			y += ySpeed;
			x -= xSpeed;
			break;
		case STOP:
			break;
		}
	}
	
	public void drawTank(Graphics g,String str){
		Graphics2D g2 = (Graphics2D)g;
		URL imgPUrl = TankClient.class.getResource("image/2.jpg"),
				imgEUrl = TankClient.class.getResource("image/3.jpg");
		
		switch(str){
		case "player":
			img = Toolkit.getDefaultToolkit().getImage(imgPUrl);
			break;
		case "enemy":
			img = Toolkit.getDefaultToolkit().getImage(imgPUrl);
			break;
		}
		move();
		g2.drawImage(img, this.x ,this.y, null);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		}
	}
	
	public void changeDir(){
		if(up && !down && !left && !right){
			dir = Direction.NORTH;
		}else if(!up && down && !left && !right){
			dir = Direction.SOUTH;
		}else if(!up && !down && left && !right){
			dir = Direction.WEST;
		}else if(!up && !down && !left && right){
			dir = Direction.EAST;
		}else if(up && !down && left && !right){
			dir = Direction.NWEST;
		}else if(up && !down && !left && right){
			dir = Direction.NEAST;
		}else if(!up && down && left && !right){
			dir = Direction.SWEST;
		}else if(!up && down && !left && right){
			dir = Direction.SEAST;
		}else{
			dir = Direction.STOP;
		}
	}
}
