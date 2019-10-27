package com.cardinal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JProgressBar;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Font;

public class ProgressFrame extends JInternalFrame {
	private JProgressBar progressBar;
	private JLabel label;
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public JLabel getLabel() {
		return label;
	}
	
	public Thread paintThread = new Thread(){
		@Override
		public void run() {
			while(true){
				ProgressFrame.this.repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	/**
	 * Create the frame.
	 */
	public ProgressFrame(String title, int fileCount) {
		super();
		setIconifiable(true);
		setTitle(title);
		setForeground(new Color(0, 0, 0));
		setBackground(new Color(255, 239, 213));
		setBorder(new LineBorder(new Color(64, 64, 64), 1, true));
		ImageIcon icon = new ImageIcon(
				this.getClass().getClassLoader().getResource("resources/internalICO.jpg"));
		this.setFrameIcon(icon);
		setClosable(true);
		setBounds(100, 150, 400, 80);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("方正隶变简体", Font.PLAIN, 16));
		progressBar.setOrientation(JProgressBar.HORIZONTAL);
		progressBar.setMinimum(0);
		progressBar.setMaximum(fileCount);
		progressBar.setValue(0);
		progressBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = progressBar.getValue();
				if (e.getSource() == progressBar) {
					label.setText("目前已完成进度：" + Integer.toString(100*value/fileCount) + "%");
				}
			}
		});
		progressBar.setPreferredSize(new Dimension(300, 20));
		progressBar.setBorderPainted(true);
		progressBar.setBackground(new Color(255, 255, 255));
		progressBar.setForeground(new Color(127, 255, 212));
		getContentPane().add(progressBar, BorderLayout.CENTER);

		label = new JLabel("共有"+fileCount+"个文件");
		label.setForeground(new Color(0, 0, 0));
		label.setBackground(new Color(127, 255, 212));
		label.setFont(new Font("方正隶变简体", Font.PLAIN, 16));
		getContentPane().add(label, BorderLayout.NORTH);
		
	}

}
