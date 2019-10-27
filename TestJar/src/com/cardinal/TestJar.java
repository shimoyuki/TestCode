package com.cardinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class TestJar {

	public URL getResourse(){
		String path = "data/专利.txt";
		return this.getClass().getClassLoader().getResource(path);
	}
	
	public InputStream getResourseAsStream(){
		String path = "data/专利.txt";
		return this.getClass().getClassLoader().getResourceAsStream(path);
	}
	
	public static void main(String[] args) {
		//test();
		System.out.println(TestJar.class.getClassLoader().getResource("").getPath());
	}

	public static void test() {
		URL url = new TestJar().getResourse();
		BufferedReader br = new BufferedReader(new InputStreamReader(new TestJar().getResourseAsStream()));
		try {
			br.readLine();
			final String str = br.readLine();
			new JFrame(){
				public void launchFrame(){
					this.setBackground(Color.CYAN);
					this.setBounds(500, 100, 500, 300);
					Container c = this.getContentPane();
					c.setLayout(new BorderLayout());
					c.add(BorderLayout.CENTER, new JLabel(url.toString()));
					c.add(BorderLayout.SOUTH, new JLabel(str));
					this.setContentPane(c);
					this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					this.setVisible(true);
				}
			}.launchFrame();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//new MessageForm("p").setText("xxxxx");;
	}

}


class MessageForm extends JFrame{
	JTextField jtf = new JTextField("开始录入。");
	
	public String getText() {
		return jtf.getText();
	}

	public void setText(String str) {
		jtf.setText(str);
	}

	public MessageForm() throws HeadlessException {
		super();
	}

	public MessageForm(String title) throws HeadlessException {
		if(title.equals("p")){
			this.setTitle("专利录入中……");
		}else if(title.equals("i")){
			this.setTitle("情报录入中……");
		}else if(title.equals("l")){
			this.setTitle("文献录入中……");
		}
		this.setBackground(Color.CYAN);
		this.setBounds(500, 100, 500, 300);
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		jtf.setAutoscrolls(true);
		jtf.setEditable(false);
		jtf.setHorizontalAlignment(JTextField.CENTER);
		c.add(BorderLayout.CENTER, jtf);
		this.setContentPane(c);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}