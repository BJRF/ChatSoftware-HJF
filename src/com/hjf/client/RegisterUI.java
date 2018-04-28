package com.hjf.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JLabel;

public class RegisterUI implements ActionListener {
	String name,id;
	String password;
	OutputStream out;
	InputStream in;
	Socket s;
	javax.swing.JTextField jtf;
	javax.swing.JTextField jtf2;
	javax.swing.JTextField jtf3;
	javax.swing.JTextField jtf4;
	javax.swing.JTextField jtf5;

	public RegisterUI(Socket s) {
		try {
			this.s = s;
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void showFrame() {
		// 创建窗体对象
		javax.swing.JFrame jf = new javax.swing.JFrame();
		jf.setSize(320, 260);
		jf.setTitle("注册界面");
		// 创建退出进程
		jf.setDefaultCloseOperation(2);
		// 居中显示
		jf.setLocationRelativeTo(null);
		// 流式布局管理器
		java.awt.FlowLayout f = new java.awt.FlowLayout();
		jf.setLayout(f);// 设置窗体为流式布局
		// 标签
		JLabel jlb = new JLabel("  您的ID       ");
		jf.add(jlb);
		// 文本框
		jtf5 = new javax.swing.JTextField();
		java.awt.Dimension d1 = new java.awt.Dimension(200, 30);
		jtf5.setPreferredSize(d1);
		jf.add(jtf5);
		// 标签
		JLabel jlb1 = new JLabel("您的用户名");
		jf.add(jlb1);
		// 文本框
		jtf = new javax.swing.JTextField();
		java.awt.Dimension d = new java.awt.Dimension(200, 30);
		jtf.setPreferredSize(d);
		jf.add(jtf);
		// 标签
		JLabel jlb2 = new JLabel("您的密码    ");
		jf.add(jlb2);
		// 文本框
		jtf2 = new javax.swing.JTextField();
		java.awt.Dimension d3 = new java.awt.Dimension(200, 30);
		jtf2.setPreferredSize(d3);
		jf.add(jtf2);
		// 标签
		JLabel jlb4 = new JLabel("您的密保    ");
		jf.add(jlb4);
		// 文本框
		jtf4 = new javax.swing.JTextField();
		jtf4.setPreferredSize(d3);
		jf.add(jtf4);
		// 按钮
		javax.swing.JButton jbu = new javax.swing.JButton("注册");
		java.awt.Dimension d2 = new java.awt.Dimension(200, 30);
		jbu.setPreferredSize(d2);
		jf.add(jbu);// 把按钮添加到窗体上
		// 文本框
		jtf3 = new javax.swing.JTextField();
		java.awt.Dimension d4 = new java.awt.Dimension(250, 30);
		jtf3.setPreferredSize(d4);
		jf.add(jtf3);
		jtf3.setEditable(false);
		jbu.addActionListener(this);
		// 设置窗体可见
		jf.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("注册")) {
			id=jtf5.getText();
			name = jtf.getText();
			password = jtf2.getText();
			// 判断输入的用户名和密码是否为空
			if (name == null || name.trim().equals("")) {
				jtf3.setText("用户名不能为空\n");
			}
			if (password == null || password.trim().equals("")) {
				jtf3.setText("密码不能为空\n");
			}
			if (id == null || id.trim().equals("")) {
				jtf3.setText("ID不能为空\n");
			}
			// 如果都不是空，则与服务器连接
			if (name != null && !name.trim().equals("") && password != null
					&& !password.trim().equals("")&&id != null && !id.trim().equals("")) {
				try {
					String strmz = name;
					String strmm = password;

					// 用户名和密码传给服务器
					out.write(("register" + "|"+id + "|" + strmz + "|" + strmm + "\r")
							.getBytes());
					out.flush();
					/*
					 * 读服务器反馈的数据首先应该读名字来判断是否私发给你
					 */
					String RegisterResult = "";
					byte[] b = new byte[1024];
					in.read(b);
					RegisterResult = new String(b).trim();
					System.out.println(RegisterResult);
					// 如果得到反馈的是成功
					if (RegisterResult.equals("success")) {
						jtf3.setText("创建用户成功");
					}
					// 如果得到的反馈是失败
					if (RegisterResult.equals("fail")) {
						jtf3.setText("创建用户失败");
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
}
