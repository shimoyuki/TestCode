package com.jianqiu.window;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class TestWindows {

	public static void main(String[] args) {
		MyFrame mf = new MyFrame("JianQiu's test window");
		new ApFrame();
		new FlpFrame();
		new BlpFrame();
		new GlpFrame();
		new MyPanel();
	}

}

class MyFrame extends JFrame{
	
	public MyFrame(String title){
		super(title);
		Container container = this.getContentPane();
		
		DrawIcon di = new DrawIcon(15,15);
		JLabel jl = new JLabel("¹éÀ´Òû±ùÑ©£¬",di,SwingConstants.CENTER);
		//jl.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*JLabel jl2 = new JLabel("",JLabel.CENTER);
		URL url = MyFrame.class.getResource("image.png");
		Icon ic = new ImageIcon(url);
		jl2.setIcon(ic);
		jl2.setHorizontalAlignment(SwingConstants.CENTER);
		jl2.setOpaque(true);*/
		
		JButton jb = new JButton("Open Dialog!");
		jb.setBounds(300,210,150,21);
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				new MyDialog(MyFrame.this).setVisible(true);
			}
		});
		
		
		container.add(jb);
		container.add(jl);
		//container.add(jl2);
		//container.setLayout(null);
		container.setBackground(Color.cyan);
		this.setVisible(true);
		this.setSize(750,421);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
}

class MyDialog extends JDialog{
	
	public MyDialog(MyFrame frame){
		super(frame,"JianQiu's test Dialog");
		
		Container container = this.getContentPane();
		DrawIcon di = new DrawIcon(15,15);
		container.add(new JLabel("ÌìÑÄÂÒ·ÉËª¡£",di,SwingConstants.CENTER));
		this.setBounds(300,300,500,100);
	}
	
}

class DrawIcon implements Icon{
	
	private int width;
	private int height;
	
	public DrawIcon(int width,int height){
		this.width = width;
		this.height = height;
	}
	
	public int getIconWidth(){
		return this.width;
	}
	
	public int getIconHeight(){
		return this.height;
	}
	
	public void paintIcon(Component arg0,Graphics arg1,int x,int y){
		arg1.fillRect(x, y, width, height);
	}
	
}

class ApFrame extends JFrame{
	
	public ApFrame(){
		this.setTitle("Absolute Position Frame!");
		this.setLayout(null);
		this.setBounds(0,0,750,421);
		Container c = this.getContentPane();
		
		JButton b1 = new JButton("Button 1");
		JButton b2 = new JButton("Button 2");
		b1.setBounds(10,30,80,30);
		b2.setBounds(60,70,100,20);
		c.add(b1);
		c.add(b2);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
}

class FlpFrame extends JFrame{
	
	public FlpFrame(){
		this.setTitle("Flow Layout  Position Frame!");
		Container c = this.getContentPane();
		
		this.setLayout(new FlowLayout(1,10,10));
		for(int i = 0; i < 10; i ++){
			c.add(new JButton("Button" + i));
		}
		
		this.setSize(300,200);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
}

class BlpFrame extends JFrame{
	
	String[] border = {BorderLayout.CENTER,BorderLayout.NORTH,
			BorderLayout.SOUTH,BorderLayout.WEST,BorderLayout.EAST};
	String[] buttonName = {"Center","North","South","West","East"};
	
	public BlpFrame(){
		this.setTitle("Border Layout Position!");
		Container c = this.getContentPane();
		this.setLayout(new BorderLayout());
		
		for(int i = 0; i < border.length; i ++){
			c.add(border[i],new  JButton(buttonName[i]));
		}
		this.setSize(350,200);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
}

class GlpFrame extends JFrame{
	
	public GlpFrame(){
		Container c = this.getContentPane();
		this.setLayout(new GridLayout(7,3,5,5));
		
		for(int i = 0; i < 21; i ++){
			c.add(new JButton("Button" + i));
		}
		
		this.setSize(500,300);
		this.setTitle("Grid Layout Position");
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
}

class MyPanel extends JFrame{
	
	public MyPanel(){
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(2,1,10,10));
		JPanel p1 = new JPanel(new GridLayout(1,3,10,10));
		JPanel p2 = new JPanel(new GridLayout(3,2,10,10));
		JPanel p3 = new JPanel(new GridLayout(2,3,10,10));
		JScrollPane sp = new JScrollPane(new JTextArea(20,50));
		
		for(int i = 0; i < 3; i ++){
			p1.add(new JButton("button" +1 + i));
		}
		for(int i = 0; i < 5; i ++){
			p2.add(new JButton("button" + 2  + i));
		}
		for(int i = 0; i < 6; i ++){
			p3.add(new JButton("button" + 3 + i));
		}
		c.add(p1);
		c.add(p2);
		c.add(p3);
		c.add(sp);
		
		this.setTitle("JPanel!");
		this.setSize(750,400);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}