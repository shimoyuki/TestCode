package com.jianqiu.aframe;

import static java.lang.System.out;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TestAddFrame {

	public static void main(String[] args) {
		new TFJFrame().launchFrame();
	}

}


class TFJFrame extends  JFrame{
	private static final long serialVersionUID = 1l;
	private JTextField jtf1 = new JTextField(10);
	private JTextField jtf2 = new JTextField(10);
	private JTextField jtf3 = new JTextField(15);
	
	public void launchFrame(){
		JLabel jlPlus = new JLabel("*");
		JButton jbEqual = new JButton("=");
		jbEqual.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				float value1 = Float.parseFloat(TFJFrame.this.jtf1.getText());
				jtf1.setText(null);
				float value2 = Float.parseFloat(TFJFrame.this.jtf2.getText());
				jtf2.setText(null);
				TFJFrame.this.jtf3.setText((value1 * value2) + "");
			}
		});
		Container c = this.getContentPane();
		c.add(jtf1);
		c.add(jlPlus);
		c.add(jtf2);
		c.add(jbEqual);
		c.add(jtf3);
		JButton jbFC = new JButton("browser");
		jbFC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//初始化文件选择框
				JFileChooser fDialog = new JFileChooser();
				//设置文件选择框的标题 
				fDialog.setDialogTitle("请选择音频文件");
				//弹出选择框
				int returnVal = fDialog.showOpenDialog(null);
				// 如果是选择了文件
				if(JFileChooser.APPROVE_OPTION == returnVal){
				       //打印出文件的路径，你可以修改位 把路径值 写到 textField 中
					System.out.println(fDialog.getSelectedFile());
				}
			}
		});
		c.add(jbFC);
		c.setLayout(new FlowLayout());
		this.pack();
		this.setBackground(new Color(10,255,255));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		c.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				jtf3.setText("What's the fuck?");
			}
			public void mouseExited(MouseEvent e){
				jtf3.setText("mother fuck!");
			}
		});
	}
}
