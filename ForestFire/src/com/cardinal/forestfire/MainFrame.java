package com.cardinal.forestfire;

import java.awt.*;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6640887729874295651L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new MainContent();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
	}

}

class MainContent extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5340334910803570096L;
	public Thread paintThread = new Thread(){
		@Override
		public void run() {
			while(true){
				MainContent.this.repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	

	public MainContent() {
		this.paintThread.start();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Date d = new Date();
		g2.drawOval(100, 100, 60-d.getSeconds(), 60-d.getSeconds());
		//g2.fill3DRect(50, 50, d.getSeconds(), d.getSeconds(), false);
		//g2.draw3DRect(150, 150, d.getSeconds(), d.getSeconds(), true);
	}

	@Override
	public void update(Graphics g) {
		super.update(g);
		//paint(g);
	}
	
	
}