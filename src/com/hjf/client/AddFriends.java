package com.hjf.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JLabel;

public class AddFriends implements ActionListener {
	Socket s;
	OutputStream out;
	InputStream in;
	javax.swing.JTextField jtf1;
	javax.swing.JTextField jtf2;
	String type;

	public AddFriends(Socket s) {
		this.s = s;
		try {
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void AddFriendsUI() {
		// 创建窗体对象
		javax.swing.JFrame jf = new javax.swing.JFrame();
		jf.setSize(320, 200);
		jf.setTitle("添加好友");
		// 创建退出进程
		jf.setDefaultCloseOperation(2);
		// 居中显示
		jf.setLocationRelativeTo(null);
		// 流式布局管理器
		java.awt.FlowLayout f = new java.awt.FlowLayout();
		jf.setLayout(f);// 设置窗体为流式布局
		// 标签
		JLabel jlb1 = new JLabel("请输入您要添加的用户ID ： ");
		jf.add(jlb1);
		// 文本框
		jtf1 = new javax.swing.JTextField();
		java.awt.Dimension d1 = new java.awt.Dimension(200, 30);
		jtf1.setPreferredSize(d1);
		jf.add(jtf1);
		// 按钮
		javax.swing.JButton jbu = new javax.swing.JButton("添加");
		java.awt.Dimension d = new java.awt.Dimension(200, 30);
		jbu.setPreferredSize(d);
		jf.add(jbu);// 把按钮添加到窗体上
		jbu.addActionListener(this);
		// 文本框
		jtf2 = new javax.swing.JTextField();
		java.awt.Dimension d2 = new java.awt.Dimension(250, 30);
		jtf2.setPreferredSize(d2);
		jf.add(jtf2);
		jtf2.setEditable(false);
		// 设置窗体可见
		jf.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// 如果点击添加好友
		if (e.getActionCommand().equals("添加")) {
			String id;
			id = jtf1.getText();
			try {
				// 等待服务器传入名字信息
				// 将要加的对象的ID发给服务器
				out.write(("addfriend|" + id + "\r").getBytes());
				out.flush();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	// public static void main(String[] args) {
	// new AddFriends().AddFriendsUI();
	// }
}
