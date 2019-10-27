package com.cardinal.ui.exception;

import javax.swing.JOptionPane;

public class NoSuchCourseException extends Exception {

	public NoSuchCourseException(String message) {
		super(message);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void printStackTrace() {
		JOptionPane.showMessageDialog(null, this.getMessage(), "程序出错", JOptionPane.ERROR_MESSAGE);
		super.printStackTrace();
	}
	
	
}
