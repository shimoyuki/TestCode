package com.cardinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class TestJar {

	public URL getResourse(){
		String path = "data/专利.txt";
		return this.getClass().getClassLoader().getResource(path);
	}
	public static void main(String[] args) {
		URL url = new TestJar().getResourse();
		new JFrame(){
			public void launchFrame(){
				this.setBackground(Color.CYAN);
				this.setBounds(500, 100, 500, 300);
				Container c = this.getContentPane();
				c.setLayout(new BorderLayout());
				c.add(BorderLayout.CENTER, new JLabel(url.toString()));
				this.setContentPane(c);
				this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				this.setVisible(true);
			}
		}.launchFrame();
		
	}

}
