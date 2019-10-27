package com.lzw;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class LoginPanel extends JPanel {

	
	protected ImageIcon icon;
	public int width ;
	public int height ;
	public LoginPanel() {
		super();
		try{
			URL url= getClass().getResource("/res/µÇÂ¼.jpg");
			icon = new ImageIcon(url);
			
			
//			icon = new ImageIcon("res/login.jpg");
			width = icon.getIconWidth();
			height = icon.getIconHeight();
			}catch(Exception e){
				e.printStackTrace();
			}
		System.out.println("width");
//		setSize(width, height);
		setSize(400,300);
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image img = icon.getImage();
		g.drawImage(img, 0, 0,getParent());
	}
}