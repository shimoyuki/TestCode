package com.jianqiu.window;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.net.URL;

public class TestButton {

	public static void main(String[] args) {
		//new MyButton();
		CheckBoxTest.main(null);

	}

}

class MyButton extends JFrame{
	
	public MyButton(){
		JMenuBar mb = new JMenuBar();
		JMenu mSetting = new JMenu("设置");
		JMenuItem jmiScSize = new JMenuItem("窗口大小");
		JMenuItem jmiScBack = new JMenuItem("窗口背景");
		mSetting.add(jmiScSize);
		mSetting.add(jmiScBack);
		mb.add(mSetting);
		this.setJMenuBar(mb);
		URL url = MyButton.class.getResource("image.png");
		Icon ic = new ImageIcon(url);
		this.setLayout(new GridLayout(3,2,5,5));
		Container c = this.getContentPane();
		
		for(int i = 0; i < 4; i ++){
			JButton jb = new JButton("button" + i);
			c.add(jb);
			if(i % 2 == 0){
				jb.setEnabled(false);
			}
		}
			JButton jb2 = new JButton();
			jb2.setMaximumSize(new Dimension(90,30));
			jb2.setIcon(ic);
			//jb2.setHideActionText(true);
			jb2.setToolTipText("Click!");
			//jb2.setBorderPainted(false);
			jb2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					JOptionPane.showMessageDialog(null, "Sorry,There is nothing you want!");
				}
			});
			
			c.add(jb2);
			this.setSize(500,300);
			this.setVisible(true);
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
}

class CheckBoxTest extends JFrame{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	private JTextArea jt=new JTextArea(11,10);
	private JCheckBox jc1=new JCheckBox("1");
	private JCheckBox jc2=new JCheckBox("2");
	private JCheckBox jc3=new JCheckBox("3");
	public CheckBoxTest(){
		Container c=getContentPane();
		setSize(300,320);
		setVisible(true);
		setTitle("复选框的使用");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		c.setLayout(new BorderLayout());

		c.add(panel3,BorderLayout.NORTH);
		panel3.add(new JButton("选择一个复选框："));
		
		c.add(panel1, BorderLayout.CENTER);
		final JScrollPane scrollPane = new JScrollPane(jt);
		panel1.add(scrollPane);


		c.add(panel2, BorderLayout.SOUTH);
		panel2.add(jc1);
		jc1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(jc1.isSelected())
				jt.append("复选框1被选中\n");
			}
		});

		panel2.add(jc2);
		jc2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(jc2.isSelected())
				jt.append("复选框2被选中\n");
			}
		});

		panel2.add(jc3);
		jc3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(jc3.isSelected())
				jt.append("复选框3被选中\n");
			}
		});
	}
	
	public static void main(String[] args) {
		new CheckBoxTest();

	}

}
