package com.lzw.login;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.lzw.*;
import com.lzw.dao.*;

public class LoginDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private LoginPanel loginPanel = null;
	private JLabel jLabel = null;
	private JTextField userField = null;
	private JLabel jLabel1 = null;
	private JPasswordField passwordField = null;
	private JButton loginButton = null;
	private JButton exitButton = null;
	private static String userStr;
	private MainFrame mainFrame;
	
	public LoginDialog() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getSystemLookAndFeelClassName());
			mainFrame = new MainFrame();
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʼ��loginPanel��¼���ķ���
	 * 
	 * @return com.lzw.login.LoginPanel
	 */
	private LoginPanel getLoginPanel() {
		if (loginPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(86, 71, 55, 18));
			jLabel1.setText("�ܡ��룺");
			jLabel = new JLabel();
			jLabel.setText("�û�����");
			jLabel.setBounds(new Rectangle(85, 41, 56, 18));
			loginPanel = new LoginPanel();
			loginPanel.setLayout(null);
			loginPanel.setBackground(new Color(0xD8DDC7));
			loginPanel.add(jLabel, null);
			loginPanel.add(getUserField(), null);
			loginPanel.add(jLabel1, null);
			loginPanel.add(getPasswordField(), null);
			loginPanel.add(getLoginButton(), null);
			loginPanel.add(getExitButton(), null);
		}
		return loginPanel;
	}
	
	/**
	 * This method initializes userField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getUserField() {
		if (userField == null) {
			userField = new JTextField();
			userField.setBounds(new Rectangle(142, 39, 127, 22));
		}
		return userField;
	}
	
	/**
	 * This method initializes passwordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getPasswordField() {
		if (passwordField == null) {
			passwordField = new JPasswordField();
			passwordField.setBounds(new Rectangle(143, 69, 125, 22));
			passwordField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() == '\n')
						loginButton.doClick();
				}
			});
		}
		return passwordField;
	}
	
	/**
	 * This method initializes loginButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getLoginButton() {
		if (loginButton == null) {
			loginButton = new JButton();
			loginButton.setBounds(new Rectangle(109, 114, 48, 20));
			loginButton.setIcon(new ImageIcon(getClass().getResource(
					"/res/loginButton.jpg")));
			loginButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						userStr = userField.getText();
						String passStr = new String(passwordField
								.getPassword());
						if (!Dao.checkLogin(userStr, passStr)) {
							JOptionPane.showMessageDialog(LoginDialog.this,
									"�û����������޷���¼", "��¼ʧ��",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					mainFrame.setVisible(true);
					MainFrame.getCzyStateLabel().setText(userStr);
					setVisible(false);
				}
			});
		}
		return loginButton;
	}
	
	/**
	 * This method initializes exitButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getExitButton() {
		if (exitButton == null) {
			exitButton = new JButton();
			exitButton.setBounds(new Rectangle(181, 114, 48, 20));
			exitButton.setIcon(new ImageIcon(getClass().getResource(
					"/res/exitButton.jpg")));
			exitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitButton;
	}
	
	/**
	 * �����ʼ������
	 * 
	 * @return void
	 */
	private void initialize() {
		Dimension size = getToolkit().getScreenSize();
		setLocation((size.width - 296) / 2, (size.height - 188) / 2);
		setSize(296, 188);
		this.setTitle("ϵͳ��¼");
		setContentPane(getLoginPanel());
	}
	
	public String getUserStr() {
		return userStr;
	}
	
}
