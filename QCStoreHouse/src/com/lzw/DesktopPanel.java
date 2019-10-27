package com.lzw;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

import javax.swing.*;

public class DesktopPanel extends JDesktopPane {

	private static final long serialVersionUID = 1L;
	private final Image backImage;

	/**
	 * Ĭ�ϵĹ��췽��
	 */
	public DesktopPanel() {
		super();
		URL url = DesktopPanel.class.getResource("/res/back.jpg");
		backImage = new ImageIcon(url).getImage();
	}
	@Override
	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
		int width = getWidth();
		int height = this.getHeight();
		g.drawImage(backImage, 0, 0, width, height, this);
	}
}