package com.lzw.iframe;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.lzw.iframe.czyGl.*;
public class JsrGL extends JInternalFrame {
	public JsrGL() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 491, 200);
		setTitle("����������");
		JTabbedPane tabPane = new JTabbedPane();
		final TJJingShouRen tjPanel = new TJJingShouRen();
		final SetJSR setPanel = new SetJSR();
		tabPane.addTab("��Ӿ�����", null, tjPanel, "��Ӿ�����");
		tabPane.addTab("���þ�����", null, setPanel, "���þ�����");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				setPanel.initTable();
			}
		});
		pack();
		setVisible(true);
	}
}
