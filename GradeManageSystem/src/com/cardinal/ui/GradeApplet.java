package com.cardinal.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.cardinal.dao.DataDAO;
import com.cardinal.model.Grade;
import com.cardinal.ui.exception.*;
import java.util.*;
import java.util.List;

public class GradeApplet extends JApplet {
	private JTextField jfNameQuery;
	private JTextField jfCourseQuery;
	private JTextField jfNameSave;
	private JTextField jfCourseSave;
	private JTextField jfPointSave;
	private JTextArea textArea;

	/**
	 * Create the applet.
	 */
	public GradeApplet() {
		getContentPane().setBackground(new Color(255, 239, 213));
		setBackground(new Color(255, 239, 213));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.add(splitPane);

		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(new Color(255, 228, 225));
		splitPane.setRightComponent(rightPanel);
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(new Color(255, 228, 225));
		splitPane.setLeftComponent(leftPanel);

		JLabel jlNameSave = new JLabel("姓名", SwingConstants.CENTER);
		jlNameSave.setForeground(Color.BLACK);
		jlNameSave.setFont(new Font("隶书", Font.PLAIN, 12));
		jlNameSave.setBackground(Color.WHITE);
		rightPanel.add(jlNameSave);

		jfNameSave = new JTextField();
		jfNameSave.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					save();
				}
			}
		});
		jfNameSave.setColumns(10);
		rightPanel.add(jfNameSave);

		JLabel jlCourseSave = new JLabel("课程", SwingConstants.CENTER);
		jlCourseSave.setForeground(Color.BLACK);
		jlCourseSave.setFont(new Font("隶书", Font.PLAIN, 12));
		jlCourseSave.setBackground(Color.WHITE);
		rightPanel.add(jlCourseSave);

		jfCourseSave = new JTextField();
		jfCourseSave.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					save();
				}
			}
		});
		jfCourseSave.setColumns(10);
		rightPanel.add(jfCourseSave);

		JLabel jlPointSave = new JLabel("分数", SwingConstants.CENTER);
		jlPointSave.setForeground(Color.BLACK);
		jlPointSave.setFont(new Font("隶书", Font.PLAIN, 12));
		jlPointSave.setBackground(Color.WHITE);
		rightPanel.add(jlPointSave);

		jfPointSave = new JTextField();
		jfPointSave.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					save();
				}
			}
		});
		jfPointSave.setColumns(10);
		rightPanel.add(jfPointSave);

		JButton btnSave = new JButton("保存");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		btnSave.setBackground(new Color(245, 222, 179));
		btnSave.setToolTipText("点击保存数据");
		btnSave.setFont(new Font("隶书", Font.PLAIN, 12));
		rightPanel.add(btnSave);

		JLabel jlNameQuery = new JLabel("姓名", JLabel.CENTER);
		leftPanel.add(jlNameQuery);
		jlNameQuery.setForeground(Color.BLACK);
		jlNameQuery.setBackground(Color.WHITE);
		jlNameQuery.setFont(new Font("隶书", Font.PLAIN, 12));

		jfNameQuery = new JTextField();
		jfNameQuery.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					query();
				}
			}
		});
		leftPanel.add(jfNameQuery);
		jfNameQuery.setColumns(10);

		JLabel jlCourseQuery = new JLabel("课程", SwingConstants.CENTER);
		leftPanel.add(jlCourseQuery);
		jlCourseQuery.setForeground(Color.BLACK);
		jlCourseQuery.setFont(new Font("隶书", Font.PLAIN, 12));
		jlCourseQuery.setBackground(Color.WHITE);

		jfCourseQuery = new JTextField();
		jfCourseQuery.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					query();
				}
			}
		});
		leftPanel.add(jfCourseQuery);
		jfCourseQuery.setColumns(10);

		JButton btnQuery = new JButton("查询");
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				query();
			}
		});
		btnQuery.setBackground(new Color(245, 245, 220));
		btnQuery.setToolTipText("点击查询数据");
		btnQuery.setFont(new Font("隶书", Font.PLAIN, 12));
		leftPanel.add(btnQuery);

		JButton btnAll = new JButton("一览");
		btnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuilder str = new StringBuilder("查询到的数据为：\r\n姓名\t\t课程\t\t成绩\r\n");			
				DataDAO dd = new DataDAO(getCodeBase().getPath().substring(1)+"data/grade.dat");
				List<Grade> lg = null;
				try {
					lg = dd.queryAll();
				} catch (Exception e1) {
					str = new StringBuilder("没有查询到相关信息");
				}
				if(lg != null){
					Iterator<Grade> ig = lg.iterator();
					while(ig.hasNext()){
						Grade g = ig.next();
						if(g.getCourse().length()<=4){
							str.append(g.getName()+"\t\t"+g.getCourse()+"\t\t"+g.getPoint()+"\r\n");
						}else{
							str.append(g.getName()+"\t\t"+g.getCourse()+"\t"+g.getPoint()+"\r\n");
						}
					}
				}
				textArea.setText(str.toString());

				resetTextField();
			}
		});
		btnAll.setToolTipText("点击查询数据");
		btnAll.setFont(new Font("隶书", Font.PLAIN, 12));
		btnAll.setBackground(new Color(245, 245, 220));
		leftPanel.add(btnAll);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		textArea.setAlignmentY(Component.TOP_ALIGNMENT);
		textArea.setEditable(false);
		textArea.setBackground(new Color(250, 240, 230));
		textArea.setFont(new Font("宋体", Font.PLAIN, 16));
		scrollPane.setViewportView(textArea);

	}

	public static String toStandaFormat(String str){
		if(str.length()>1){
			return str.trim().substring(0,1).toUpperCase() + str.trim().substring(1).toLowerCase();
		}else if(str.length()==1){
			return str.trim().toUpperCase();
		}else{
			return str;
		}
	}

	public void resetTextField(){
		jfNameQuery.setText("");
		jfNameSave.setText("");
		jfCourseQuery.setText("");
		jfCourseSave.setText("");
		jfPointSave.setText("");
	}
	
	public void save(){
		DataDAO dd = new DataDAO(getCodeBase().getPath().substring(1)+"data/grade.dat");

		String name = jfNameSave.getText().trim(), 
				course = GradeApplet.toStandaFormat(jfCourseSave.getText()), 
				point = jfPointSave.getText().trim();

		try {
			if(!name.equals("") && !course.equals("") && !point.equals("")){
				if(!course.equals("C") && !course.equals("Java") && !course.equals("Database")){
					throw new NoSuchCourseException("输入的课程不存在,现有课程为:\r\nJava、C、Database");
				}else if(Integer.parseInt(point)>100 || Integer.parseInt(point)<0){
					throw new PointOutOfRangeException("输入成绩应在0-100之间！");
				}else{
					Grade gra = new Grade(name, course, Integer.parseInt(point));
					if(dd.save(gra)){
						JOptionPane.showMessageDialog(null, "数据已保存！", "保存成功", JOptionPane.PLAIN_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "数据已存在！", "保存失败", JOptionPane.PLAIN_MESSAGE);
					}
					
				}
			}else{
				throw new NoEnoughParameterException("请完整填写三项信息！");
			}
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "输入的成绩必须为整数！", "程序出错", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		} catch (NoSuchCourseException e1) {
			e1.printStackTrace();
		} catch (PointOutOfRangeException e1) {
			e1.printStackTrace();
		} catch (NoEnoughParameterException e1) {
			e1.printStackTrace();
		}
		textArea.setText("");

		resetTextField();
	}

	public void query(){
		StringBuilder str = new StringBuilder("查询到的数据为：\r\n姓名\t\t课程\t\t成绩\r\n");			
		DataDAO dd = new DataDAO(getCodeBase().getPath().substring(1)+"data/grade.dat");
		List<Grade> lg = null;

		String name = jfNameQuery.getText().trim(), 
				course = GradeApplet.toStandaFormat(jfCourseQuery.getText());

		try {
			if(!name.equals("") && !course.equals("")){
				lg = dd.queryByWhole(name, course);
			}else if(!name.equals("") && course.equals("")){
				lg = dd.queryByName(name);
			}else if(name.equals("") && !course.equals("")){
				lg = dd.queryByCourse(course);
			}else{
				throw new NoEnoughParameterException("请至少输入一个查询项！");
			}
		} catch (NoEnoughParameterException e1) {
			e1.printStackTrace();
		} catch (Exception e2){
			str = new StringBuilder("没有查询到相关信息");
		}
		if(lg != null){
			Iterator<Grade> ig = lg.iterator();
			while(ig.hasNext()){
				Grade g = ig.next();
				if(g.getCourse().length()<=4){
					str.append(g.getName()+"\t\t"+g.getCourse()+"\t\t"+g.getPoint()+"\r\n");
				}else{
					str.append(g.getName()+"\t\t"+g.getCourse()+"\t"+g.getPoint()+"\r\n");
				}
			}
		}
		textArea.setText(str.toString());

		resetTextField();
	}
	
	@Override
	public void init() {
		super.init();
		setSize(512, 288);
	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void stop() {
		super.stop();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}
