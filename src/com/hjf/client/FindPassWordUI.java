package com.hjf.client;

import javax.swing.JLabel;

public class FindPassWordUI {
	public void showFrame() {
		// 创建窗体对象
		javax.swing.JFrame jf = new javax.swing.JFrame();
		jf.setSize(320, 200);
		jf.setTitle("找回密码");
		// 创建退出进程
		jf.setDefaultCloseOperation(2);
		// 居中显示
		jf.setLocationRelativeTo(null);
		// 流式布局管理器
		java.awt.FlowLayout f = new java.awt.FlowLayout();
		jf.setLayout(f);// 设置窗体为流式布局
		// 标签
		JLabel jlb1 = new JLabel("您的用户名");
		jf.add(jlb1);
		// 文本框
		javax.swing.JTextField jtf = new javax.swing.JTextField();
		java.awt.Dimension d = new java.awt.Dimension(200, 30);
		jtf.setPreferredSize(d);
		jf.add(jtf);
		// 标签
		JLabel jlb2 = new JLabel("您的密保    ");
		jf.add(jlb2);
		// 文本框
		javax.swing.JTextField jtf2 = new javax.swing.JTextField();
		java.awt.Dimension d3 = new java.awt.Dimension(200, 30);
		jtf2.setPreferredSize(d3);
		jf.add(jtf2);
		// 按钮
		javax.swing.JButton jbu = new javax.swing.JButton("找回");
		java.awt.Dimension d2 = new java.awt.Dimension(200, 30);
		jbu.setPreferredSize(d2);
		jf.add(jbu);// 把按钮添加到窗体上
		// 文本框
		javax.swing.JTextField jtf3 = new javax.swing.JTextField();
		java.awt.Dimension d4 = new java.awt.Dimension(250, 30);
		jtf3.setPreferredSize(d4);
		jf.add(jtf3);
		jtf3.setEditable(false);
		// 设置窗体可见
		jf.setVisible(true);
	}
	// public static void main(String[] args) {
	// new FindPassWordUI().showFrame();
	// }
}
