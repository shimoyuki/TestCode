package com.jianqiu.tank;

import static java.lang.System.out;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.File;
import java.net.*;

public class TankClient extends JFrame {

	private static final long serialVersionUID = 1l;
	private int ScWidth = 960, ScHeight = 630;
	private BackPanel back = new BackPanel(ScWidth,ScHeight);
	
	
	public TankClient(){
		super();
		
		JMenuBar mb = new JMenuBar();
		JMenu mSetting = new JMenu("ゲーム設定");
		JMenuItem jmiScSize = new JMenuItem("ゲーム設定");
		jmiScSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ClientSetting(TankClient.this);
			}
		});
		
		JMenu mSE = new JMenu("スタート/エンド");
		JMenuItem jmiStart = new JMenuItem("ゲームスタート");
		jmiStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				back.control.start();
			}
		});
		JMenuItem jmiEnd = new JMenuItem("ゲームエンド");
		jmiEnd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				back.control.pause();;
			}
		});
		
		mSE.add(jmiStart);
		mSE.add(jmiEnd);
		mSetting.add(jmiScSize);
		mb.add(mSE);
		mb.add(mSetting);
		mb.setVisible(true);
		this.setJMenuBar(mb);
	}
	
	public void launchFrame(){
		this.setTitle("タンクウォー");
		this.setBounds(200, 50, ScWidth, ScHeight);
		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		this.setContentPane(back);
		back.drawBack();
		this.addKeyListener(new TankListener());
		
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.setVisible(true);
	}
	
	class TankListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			back.player.keyPressed(e);
			back.player.changeDir();
			back.repaint();
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			back.player.keyReleased(e);
		}
	}
	
	class ClientSetting extends JDialog{
		
		private static final long serialVersionUID = 1l;
		
		public ClientSetting(JFrame jf){
			
			super(jf,"設定",true);
			Container c = this.getContentPane();
			c.setLayout(new GridLayout(4,2));
			
			JRadioButton jrbScSize1 = new JRadioButton("1280:800");
			JRadioButton jrbScSize2 = new JRadioButton("960:600");
			JRadioButton jrbScSize3 = new JRadioButton("640:400");
			ButtonGroup grScSize = new ButtonGroup();
			
			grScSize.add(jrbScSize1);
			jrbScSize1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ScWidth = 1280;
					ScHeight = 830;
					TankClient.this.setBounds(43,0,1280, 830);
					back.setSize(1280, 830);
				}
			});
			grScSize.add(jrbScSize2);
			jrbScSize2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ScWidth = 960;
					ScHeight = 630;
					TankClient.this.setBounds(203,50,960, 630);
					back.setSize(960, 630);
				}
			});
			grScSize.add(jrbScSize3);
			jrbScSize3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ScWidth = 640;
					ScHeight = 430;
					TankClient.this.setBounds(358,150,640, 430);
					back.setSize(640, 430);
				}
			});
			
			c.add(new JLabel("スクリーン　サイズ"));
			c.add(jrbScSize1);
			c.add(jrbScSize2);
			c.add(jrbScSize3);
			
			c.add(new JLabel("バックグラウンド　イメージ"));
			JButton jbBack = new JButton("Browser");
			jbBack.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser jfcBack = new JFileChooser();
					jfcBack.setDialogTitle("一つグラフを選んでください");
					int returnVal = jfcBack.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						File imgFile = jfcBack.getSelectedFile();
						try{
						URL imgURL = imgFile.toURL();
						out.println(imgURL);
						back.setBack(imgURL);
						back.repaint();
						}catch(MalformedURLException mue){
							mue.printStackTrace();
						}
					}
				}
			});
			c.add(jbBack);
			c.add(new JLabel("まだ開発中"));
			this.setBounds(200,200,480,270);
			this.setVisible(true);
		}
					
	}
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}

}

class BackPanel extends JPanel{
	private static final long serialVersionUID = 1l;
	private Image img;
	private int ScWidth, ScHeight;
	private Image offScreen = null;
	public PaintThread control =new PaintThread();
	Tank player = new Tank(480,400);
	
	public BackPanel(int w,int h){
		this.ScWidth = w;
		this.ScHeight = h;
	}
	
	@Override
	public void setSize(int w,int h){
		this.ScWidth = w;
		this.ScHeight = h;
	}
	
	public void drawBack(){
		URL imUrl = TankClient.class.getResource("image/1.jpg");
		img = Toolkit.getDefaultToolkit().getImage(imUrl);
	}
	
	public void setBack(URL imgURL){
		img = Toolkit.getDefaultToolkit().getImage(imgURL);
	}
	
	@Override
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(img,0,0,ScWidth,ScHeight-25,this);
		player.drawTank(g,"player");
	}
	
	@Override
	public void update(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		if(offScreen == null){
			offScreen = this.createImage(ScWidth,ScHeight);
		}
		Graphics2D g2dOffScreen = (Graphics2D)offScreen.getGraphics();
		paint(g2dOffScreen);
		g2d.drawImage(offScreen,0,0,this);
	}
	
	class PaintThread extends Thread{
		 private boolean flag = true;
		 
		 public void pause(){
			 this.flag = false;
		 }
		 
		@Override
		public void run(){
			while(flag){
				repaint();
				try{
				Thread.sleep(50);
				}catch (InterruptedException ie){
					ie.printStackTrace();
				}
			}
		}
	}
	
}

