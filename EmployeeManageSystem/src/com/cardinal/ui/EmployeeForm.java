package com.cardinal.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import java.sql.*;
import java.util.Iterator;
import java.util.List;

import com.cardinal.dao.DataDAO;
import com.cardinal.model.Employee;

public class EmployeeForm extends JFrame {

	private JPanel contentPane;
	private BackPanel backPane;
	private JPanel cardPane;
	private JSplitPane splitPane;
	private JPanel btnPane;
	private JPanel registPane;
	private JScrollPane inquiryPane;
	private JPanel menuPane ;
	private CardLayout cardLayout;
	private JTextField jfId;
	private JTextField jfName;
	private JTextField jfDepId;
	private JTextField jfEduId;
	private JTextField jfPost;
	private JTextField jfSalary;
	private JButton btnSave;
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	
	protected void resetRegistPane(){
		jfId.setText("");
		jfName.setText("");
		jfDepId.setText("");
		jfEduId.setText("");
		jfPost.setText("");
		jfSalary.setText("");
	}
	
	protected void initTable() {
		table = new JTable();
		String[] columnNames = {"员工编号","员工姓名","部门编号","学历编号","职务","工资"};
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
		tableModel.setColumnIdentifiers(columnNames);
		final JTextField cellField = new JTextField();
		cellField.setAlignmentX(CENTER_ALIGNMENT);
		cellField.setEditable(false);
		cellField.setHorizontalAlignment(JTextField.CENTER);
		DefaultCellEditor cellEditor = new DefaultCellEditor(cellField);
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setCellEditor(cellEditor);
		}
		Object[] row = new Object[6];
		List<Employee> le = DataDAO.getEmpInfo();
		Iterator<Employee> ie = le.iterator();
		while(ie.hasNext()){
			Employee emp = ie.next();
			row[0] = emp.getId();
			row[1] = emp.getName();
			row[2] = emp.getDepId();
			row[3] = emp.getEduId();
			row[4] = emp.getPost();
			row[5] = emp.getSalary();
			tableModel.addRow(row);
		}
		table.setRowHeight(20);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();   
		dtcr.setHorizontalAlignment(JLabel.CENTER); 
		table.setDefaultRenderer(Object.class, dtcr);
		table.setRowSorter(new TableRowSorter<>(tableModel));
		inquiryPane.setViewportView(table);
		
	}
	
	public static void main(String[] args) {
		new DataDAO();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeForm frame = new EmployeeForm();
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
	public EmployeeForm() {
		setBackground(new Color(255, 248, 220));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(275, 100, 800, 500);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		splitPane = new JSplitPane();
		splitPane.setBounds(0, 70, 794, 420);
		contentPane.add(splitPane);
		
		btnPane = new JPanel();
		btnPane.setBackground(new Color(255, 228, 225));
		splitPane.setDividerLocation(100);
		splitPane.setDividerSize(8);
		splitPane.setOneTouchExpandable(true);
		splitPane.setLeftComponent(btnPane);
		btnPane.setLayout(null);
		JButton btnRegist = new JButton("员工登记");
		btnRegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPane, "regist");
			}
		});
		btnRegist.setBounds(20, 70, 60, 27);
		btnPane.add(btnRegist);
		btnRegist.setBorder(new LineBorder(new Color(210, 180, 140), 1, true));
		btnRegist.setFont(new Font("隶书", Font.PLAIN, 13));
		btnRegist.setBackground(new Color(250, 235, 215));
		btnRegist.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnExit = new JButton("退出系统");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeForm.this.setVisible(false);
				System.exit(0);
			}
		});
		btnExit.setBounds(20, 150, 60, 27);
		btnPane.add(btnExit);
		btnExit.setBorder(new LineBorder(new Color(210, 180, 140), 1, true));
		btnExit.setFont(new Font("隶书", Font.PLAIN, 13));
		btnExit.setBackground(new Color(250, 235, 215));
		btnExit.setAlignmentX(0.5f);
		
		JButton btnInquiry = new JButton("员工查询");
		btnInquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initTable();
				cardLayout.show(cardPane, "inquiry");
			}
		});
		btnInquiry.setFont(new Font("隶书", Font.PLAIN, 13));
		btnInquiry.setBorder(new LineBorder(new Color(210, 180, 140), 1, true));
		btnInquiry.setBackground(new Color(250, 235, 215));
		btnInquiry.setAlignmentX(0.5f);
		btnInquiry.setBounds(20, 110, 60, 27);
		btnPane.add(btnInquiry);
		
		JButton btnWel = new JButton("返回首页");
		btnWel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.first(cardPane);
			}
		});
		btnWel.setFont(new Font("隶书", Font.PLAIN, 13));
		btnWel.setBorder(new LineBorder(new Color(210, 180, 140), 1, true));
		btnWel.setBackground(new Color(250, 235, 215));
		btnWel.setAlignmentX(0.5f);
		btnWel.setBounds(20, 30, 60, 27);
		btnPane.add(btnWel);
		
		cardPane = new JPanel();
		splitPane.setRightComponent(cardPane);
		cardLayout = new CardLayout(5, 5);
		cardPane.setLayout(cardLayout);
		
		backPane = new BackPanel();
		cardPane.add(backPane, "back");
		backPane.setLayout(null);
		
		registPane = new JPanel();
		registPane.setBackground(new Color(255, 250, 240));
		cardPane.add(registPane, "regist");
		registPane.setLayout(null);
		
		jfId = new JTextField();
		jfId.setBorder(new LineBorder(new Color(220, 20, 60), 1, true));
		jfId.setBackground(Color.WHITE);
		jfId.setBounds(135, 50, 150, 25);
		registPane.add(jfId);
		jfId.setColumns(10);
		
		JLabel jlId = new JLabel("员工编号*");
		jlId.setHorizontalAlignment(SwingConstants.CENTER);
		jlId.setFont(new Font("楷体", Font.PLAIN, 15));
		jlId.setLabelFor(jfId);
		jlId.setBounds(50, 50, 70, 25);
		registPane.add(jlId);
		
		JLabel jlName = new JLabel("员工姓名*");
		jlName.setHorizontalAlignment(SwingConstants.CENTER);
		jlName.setFont(new Font("楷体", Font.PLAIN, 15));
		jlName.setBounds(350, 50, 70, 25);
		registPane.add(jlName);
		
		jfName = new JTextField();
		jfName.setColumns(10);
		jfName.setBorder(new LineBorder(new Color(220, 20, 60), 1, true));
		jfName.setBackground(Color.WHITE);
		jfName.setBounds(435, 50, 150, 25);
		registPane.add(jfName);
		
		JLabel jlDepId = new JLabel("部门编号*");
		jlDepId.setHorizontalAlignment(SwingConstants.CENTER);
		jlDepId.setFont(new Font("楷体", Font.PLAIN, 15));
		jlDepId.setBounds(50, 135, 70, 25);
		registPane.add(jlDepId);
		
		jfDepId = new JTextField();
		jfDepId.setColumns(10);
		jfDepId.setBorder(new LineBorder(new Color(220, 20, 60), 1, true));
		jfDepId.setBackground(Color.WHITE);
		jfDepId.setBounds(135, 135, 150, 25);
		registPane.add(jfDepId);
		
		JLabel jlEduId = new JLabel("学历编号");
		jlEduId.setHorizontalAlignment(SwingConstants.CENTER);
		jlEduId.setFont(new Font("楷体", Font.PLAIN, 15));
		jlEduId.setBounds(350, 135, 70, 25);
		registPane.add(jlEduId);
		
		jfEduId = new JTextField();
		jfEduId.setColumns(10);
		jfEduId.setBorder(new LineBorder(new Color(220, 20, 60), 1, true));
		jfEduId.setBackground(Color.WHITE);
		jfEduId.setBounds(435, 135, 150, 25);
		registPane.add(jfEduId);
		
		JLabel jlPost = new JLabel("职务");
		jlPost.setHorizontalAlignment(SwingConstants.CENTER);
		jlPost.setFont(new Font("楷体", Font.PLAIN, 15));
		jlPost.setBounds(50, 210, 70, 25);
		registPane.add(jlPost);
		
		jfPost = new JTextField();
		jfPost.setColumns(10);
		jfPost.setBorder(new LineBorder(new Color(220, 20, 60), 1, true));
		jfPost.setBackground(Color.WHITE);
		jfPost.setBounds(135, 210, 400, 25);
		registPane.add(jfPost);
		
		JLabel jlSalary = new JLabel("工资");
		jlSalary.setHorizontalAlignment(SwingConstants.CENTER);
		jlSalary.setFont(new Font("楷体", Font.PLAIN, 15));
		jlSalary.setBounds(50, 285, 70, 25);
		registPane.add(jlSalary);
		
		jfSalary = new JTextField();
		jfSalary.setColumns(10);
		jfSalary.setBorder(new LineBorder(new Color(220, 20, 60), 1, true));
		jfSalary.setBackground(Color.WHITE);
		jfSalary.setBounds(135, 285, 150, 25);
		registPane.add(jfSalary);
		
		btnSave = new JButton("保存");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idS=jfId.getText().trim(), name=jfName.getText().trim(), 
						depIdS=jfDepId.getText().trim(), eduIdS=jfEduId.getText().trim(),
						post=jfPost.getText().trim(), salaryS=jfSalary.getText().trim();
				
				if(idS.equals("") && name.equals("") && depIdS.equals("")){
					JOptionPane.showMessageDialog(null, "*号项不能为空！","错误",  JOptionPane.ERROR_MESSAGE);
				}else{
					int id, eduId = 0, depId = 0;
					float salary = 0.0f;
					try {
						id = Integer.parseInt(idS);
						depId = Integer.parseInt(depIdS);
						if(eduIdS.equals("")){
							eduId = -1;
						}else{
							eduId = Integer.parseInt(eduIdS);
						}
						if(salaryS.equals("")){
							salary = 0.0f;
						}else{
							salary = Float.parseFloat(salaryS);
						}
						List<Employee> le = DataDAO.getEmpInfoById(depId, id);
						Employee emp = new Employee(id, name, depId, eduId, post, salary);
						if(le.size()==0){
							DataDAO.save(emp);
						JOptionPane.showMessageDialog(null, "保存成功！");
						}else{
							int confirm = JOptionPane.showConfirmDialog(null, "该编号的员工已存在,是否覆盖?","提示",  JOptionPane.YES_NO_CANCEL_OPTION);
							if(confirm == 0){
								DataDAO.update(emp);
							}
						}
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "数据格式错误，请重新输入！", 
								"错误", JOptionPane.ERROR_MESSAGE );
					}
				}
			}
		});
		btnSave.setBorder(new LineBorder(new Color(255, 248, 220)));
		btnSave.setBackground(new Color(255, 235, 205));
		btnSave.setFont(new Font("隶书", Font.PLAIN, 15));
		btnSave.setBounds(250, 340, 100, 25);
		registPane.add(btnSave);
		
		JButton btnReset = new JButton("取消");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetRegistPane();
				cardLayout.first(cardPane);
			}
		});
		btnReset.setFont(new Font("隶书", Font.PLAIN, 15));
		btnReset.setBorder(new LineBorder(new Color(255, 248, 220)));
		btnReset.setBackground(new Color(255, 235, 205));
		btnReset.setBounds(450, 340, 100, 25);
		registPane.add(btnReset);
		
		
		
		inquiryPane = new JScrollPane();
		inquiryPane.setBackground(new Color(255, 250, 240));
		cardPane.add(inquiryPane, "inquiry");
		
		menuPane = new JPanel();
		menuPane.setBounds(0, 0, 794, 70);
		contentPane.add(menuPane);
		menuPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("隶书", Font.PLAIN, 15));
		menuBar.setBounds(0, 0, 794, 35);
		menuPane.add(menuBar);
		menuBar.setBackground(new Color(255, 250, 250));
		
		JMenu mnOption = new JMenu("选项");
		mnOption.setSize(40, 30);
		mnOption.setFont(new Font("黑体", Font.PLAIN, 15));
		mnOption.setHorizontalAlignment(SwingConstants.CENTER);
		mnOption.setBackground(new Color(250, 250, 210));
		mnOption.addSeparator();
		menuBar.add(mnOption);
		
		JMenuItem mntmRegist = new JMenuItem("员工登记");
		mntmRegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPane, "regist");
			}
		});
		mntmRegist.setFont(new Font("宋体", Font.PLAIN, 12));
		mntmRegist.setBackground(new Color(255, 228, 225));
		mnOption.add(mntmRegist);
		
		JMenuItem mntmCalcu = new JMenuItem("员工查询");
		mntmCalcu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initTable();
				cardLayout.show(cardPane, "inquiry");
			}
		});
		mntmCalcu.setFont(new Font("宋体", Font.PLAIN, 12));
		mntmCalcu.setBackground(new Color(255, 228, 225));
		mnOption.add(mntmCalcu);
		
		JMenuItem mntmTotal = new JMenuItem("员工统计");
		mntmTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "select count(*) from person";
				ResultSet rs = DataDAO.query(sql);
				int total = -1;
				try {
					while (rs.next()) {
						total = rs.getInt(1);
					}
					JOptionPane.showMessageDialog(null, "共有"+total+"名员工");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		mntmTotal.setFont(new Font("宋体", Font.PLAIN, 12));
		mntmTotal.setBackground(new Color(255, 228, 225));
		mnOption.add(mntmTotal);
		
		JMenu mnSystem = new JMenu("系统");
		mnSystem.setSize(40, 30);
		mnSystem.setFont(new Font("黑体", Font.PLAIN, 15));
		mnSystem.setBackground(new Color(255, 228, 225));
		menuBar.add(mnSystem);
		
		JMenuItem mntmExit = new JMenuItem("退出系统");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeForm.this.setVisible(false);
				System.exit(0);
			}
		});
		mntmExit.setFont(new Font("宋体", Font.PLAIN, 12));
		mnSystem.add(mntmExit);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 35, 794, 35);
		menuPane.add(toolBar);
		toolBar.setBackground(new Color(255, 228, 225));
	}

	
}

class BackPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3582181863186413398L;
	private Image img;

	public BackPanel() {
		super();
		img = Toolkit.getDefaultToolkit().getImage(
				this.getClass().getClassLoader().getResource("resources/background.jpg"));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		int width = getWidth();
		int height = this.getHeight();
		g2.drawImage(img , 0, 0, width, height, this);
		g2.setColor(Color.RED);
		g2.setFont(new Font("隶书", Font.BOLD, 40));
		g2.drawString("员工信息管理", 220, 210);
	}

}
