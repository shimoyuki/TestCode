package com.cardinal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5096245824415150468L;
	private JPanel contentPane;
	JPanel mainPanel;
	private CardLayout cardLayout;
	JPanel buttonPanel;

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
		setType(Type.UTILITY);
		setTitle("GeneMatching");
		setBackground(new Color(51, 255, 204));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		mainPanel = new JPanel();
		cardLayout = new CardLayout(5, 5);
		mainPanel.setLayout(cardLayout);
		mainPanel.add(new JLabel("11111"));
		mainPanel.add(new JLabel("22222"));

		buttonPanel = new JPanel();
		JButton jbFirst = new JButton("FirstPage"), 
				jbLast = new JButton("LastPage"),
				jbPrevious = new JButton("PreviousPage"),
				jbNext = new JButton("NextPage");
		jbFirst.addActionListener(new ButtonControlListener());
		jbPrevious.addActionListener(new ButtonControlListener());
		jbNext.addActionListener(new ButtonControlListener());
		jbLast.addActionListener(new ButtonControlListener());
		buttonPanel.add(jbFirst);
		buttonPanel.add(jbPrevious);
		buttonPanel.add(jbNext);
		buttonPanel.add(jbLast);
		
		contentPane.add(mainPanel,BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	class ButtonControlListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton jb = (JButton)e.getSource();
			String text = jb.getText();
			switch(text){
			case "FirstPage":
				cardLayout.first(mainPanel);
				break;
			case "PreviousPage":
				cardLayout.previous(mainPanel);
				break;
			case "NextPage":
				cardLayout.next(mainPanel);
				break;
			case "LastPage":
				cardLayout.last(mainPanel);
				break;
			}
		}
		
	}

}

