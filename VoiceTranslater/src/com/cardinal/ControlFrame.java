package com.cardinal;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javafx.scene.control.ProgressBar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.prefs.Preferences;
import java.awt.Font;
import java.awt.Color;

import javax.swing.SwingConstants;

public class ControlFrame extends JFrame{

	private JPanel contentPane;
	private JMenuBar menuBar;
	private BackPanel backPanel;
	private JMenu option;
	private JMenuItem open;
	private File[] files = null;
	private File outputDir = null;
	private JMenuItem exec;
	private int[] counter;
	private ProgressFrame progressFrame;
	private JMenu setting;
	private JCheckBoxMenuItem unify;
	private JMenuItem output;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlFrame frame = new ControlFrame();
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
	public ControlFrame() {
		setTitle("艦これ時計音源转换器");
		counter = new int[4];
		for (int i = 0; i < counter.length; i++) {
			counter[i] = 0;
		}
		Image icon = Toolkit.getDefaultToolkit ().getImage(
				this.getClass().getClassLoader().getResource("resources/programICO.jpg"));
		this.setIconImage(icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 600, 400);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.8);
		
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(248, 248, 255));
		menuBar.setBounds(0, 0, 595, 30);
		contentPane.add(menuBar);
		
		setting = new JMenu("设置");
		setting.setFont(new Font("隶书", Font.PLAIN, 18));
		menuBar.add(setting);
		
		unify = new JCheckBoxMenuItem("统合");
		unify.setHorizontalAlignment(SwingConstants.CENTER);
		unify.setFont(new Font("方正隶变_GBK", Font.PLAIN, 16));
		setting.add(unify);
		
		output = new JMenuItem("输出文件夹");
		output.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Preferences pref = Preferences.userRoot().node(
						ControlFrame.this.getClass().getName());
				String outputPath = pref.get("outputPath", "");
				JFileChooser jfc = null;
				if (!outputPath.equals("")) {
				    jfc = new JFileChooser(outputPath);
				} else{
				    jfc = new JFileChooser();
				}

				jfc.setMultiSelectionEnabled(false);
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int nRetVal = jfc.showOpenDialog(ControlFrame.this);
				if (nRetVal == JFileChooser.APPROVE_OPTION){
					outputDir = jfc.getSelectedFile();
					pref.put("outputPath",outputDir.getPath());
				}
			}
		});
		output.setHorizontalAlignment(SwingConstants.CENTER);
		output.setFont(new Font("方正隶变_GBK", Font.PLAIN, 16));
		setting.add(output);
		
		option = new JMenu("选项");
		option.setFont(new Font("隶书", Font.PLAIN, 18));
		menuBar.add(option);
		
		open = new JMenuItem("打开");
		open.setHorizontalAlignment(SwingConstants.CENTER);
		open.setFont(new Font("方正隶变_GBK", Font.PLAIN, 16));
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Preferences pref = Preferences.userRoot().node(
						ControlFrame.this.getClass().getName());
				String lastPath = pref.get("lastPath", ""), 
						outputPath = pref.get("outputPath", "");
				if(!outputPath.equals("")){
					outputDir = new File(outputPath);
				}
				JFileChooser jfc = null;
				if (!lastPath.equals("")) {
				    jfc = new JFileChooser(lastPath);
				} else{
				    jfc = new JFileChooser();
				}

				jfc.setMultiSelectionEnabled(true);
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int nRetVal = jfc.showOpenDialog(ControlFrame.this);
				if (nRetVal == JFileChooser.APPROVE_OPTION){
					files = jfc.getSelectedFiles();
					int fileCount = 0;
					for (int i = 0; i < files.length; i++) {
						for (int j = 0; j < files[i].list().length; j++) {
							fileCount++;
						}
					}
					if(unify.getState()){
						progressFrame = new ProgressFrame("统合模式；输出："+outputPath, fileCount);
					}else{
						progressFrame = new ProgressFrame("分离模式；输出："+outputPath, fileCount);
					}
					progressFrame.setVisible(true);
					backPanel.add(progressFrame);
					pref.put("lastPath",files[files.length-1].getParent());
				}
			}
		});
		option.add(open);
		
		exec = new JMenuItem("执行");
		exec.setHorizontalAlignment(SwingConstants.CENTER);
		exec.setFont(new Font("方正隶变_GBK", Font.PLAIN, 16));
		exec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(files == null){
					JOptionPane.showConfirmDialog(ControlFrame.this, 
							"尚未选择待处理文件！","错误", JOptionPane.DEFAULT_OPTION);
				}else if(outputDir == null){
					JOptionPane.showConfirmDialog(ControlFrame.this, 
							"尚未选择输出文件夹！","错误", JOptionPane.DEFAULT_OPTION);
				}else{
					if(unify.getState()){
						executeByUnityMode();
					}else{
						executeByDivideMode();
					}
					
				}
			}
		});
		option.add(exec);
		
		backPanel = new BackPanel();
		backPanel.setBounds(0, 30, 595, 340);
		contentPane.add(backPanel);
		
	}

	private void executeByUnityMode() {
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if(file.isDirectory()){
				System.out.println(file.getPath());
				String[] item = file.list();
				if(item.length == 0){
					JOptionPane.showConfirmDialog(ControlFrame.this, 
							file.getPath()+"下没有文件", "提示", JOptionPane.DEFAULT_OPTION);
				}
				for (int j = 0; j < item.length; j++) {
					String string = item[j];
					System.out.println("\t"+string);
					File fItem = new File(file, string);
					if(fItem.isFile()){
						changeFileName(fItem);
					}
					progressFrame.getProgressBar().setValue(
							progressFrame.getProgressBar().getValue()+1);
				}
				progressFrame.getLabel().setText("转换完成");
			}else if(file.isFile()){
				System.out.println(file.getName());
				changeFileName(file);
				progressFrame.getProgressBar().setValue(
						progressFrame.getProgressBar().getValue()+1);
			}
		}
		for (int i = 0; i < counter.length; i++) {
			counter[i] = 0;
		}
		
	}

	private void executeByDivideMode() {
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if(file.isDirectory()){
				System.out.println(file.getPath());
				String[] item = file.list();
				if(item.length == 0){
					JOptionPane.showConfirmDialog(ControlFrame.this, 
							file.getPath()+"下没有文件", "提示", JOptionPane.DEFAULT_OPTION);
				}
				for (int j = 0; j < item.length; j++) {
					String string = item[j];
					System.out.println("\t"+string);
					File fItem = new File(file, string);
					if(fItem.isFile()){
						changeFileName(fItem);
					}
					progressFrame.getProgressBar().setValue(
							progressFrame.getProgressBar().getValue()+1);
				}
				progressFrame.getLabel().setText("转换完成");
			}else if(file.isFile()){
				System.out.println(file.getName());
				changeFileName(file);
				progressFrame.getProgressBar().setValue(
						progressFrame.getProgressBar().getValue()+1);
			}
			for (int j = 0; j < counter.length; j++) {
				counter[j] = 0;
			}
		}
		
	}
	
	private void changeFileName(File fItem) {
		String[] name = fItem.getName().split("[.]");
		File dest = null;
		int num = -1;
		try {
			num = Integer.parseInt(name[0]);
		} catch (NumberFormatException e) {
			
		}
		if(num != -1){
			File outputParent = new File(outputDir, fItem.getParentFile().getName());
			if(num>=30 && num <=53){
				dest = new File(outputParent, countIndex(counter[0]++)+".mp3");
			}else if(num==1 || num == 25){
				dest = new File(outputParent, "op"+countIndex(counter[1]++)+".mp3");
			}else if(num==24 || num == 28 || num==29){
				dest = new File(outputParent, "wa"+countIndex(counter[2]++)+".mp3");
			}else if((num>=2&&num<=4) || num==6 || num==8 || (num>=9&&num<=12) || num==26 || num==27){
				dest = new File(outputParent, "dc"+countIndex(counter[3]++)+".mp3");
			}
			if(dest != null){
				CopyFileUtil.copyFile(fItem.getAbsolutePath(), dest.getAbsolutePath(), true);
			}
			
		}
	}
	
	private String countIndex(int count){
		if(0<=count && count<=9){
			return "0" + count;
		}else{
			return "" + count;
		}
	}
}

class BackPanel extends JDesktopPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3582181863186413398L;
	private Image img;

	public BackPanel(){
		super();
		int index = new Date().getSeconds()%12+1;
		String path;
		if(0<=index && index<=9){
			path = "resources/0"+index+".jpg";
		}else{
			path = "resources/"+index+".jpg";
		}
		img = Toolkit.getDefaultToolkit().getImage(
				this.getClass().getClassLoader().getResource(path));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		int width = getWidth();
		int height = this.getHeight();
		g2.drawImage(img , 0, 0, width, height, this);
	}

}


