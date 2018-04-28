package com.hjf.client;

import java.awt.Checkbox;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LoginUI implements ActionListener {
	Graphics g;
	String id;
	String password;
	OutputStream out;
	InputStream in;
	Socket s;
	TextField tf;
	TextField tf2;
	Checkbox cb;
	Checkbox cb2;
	File f = new File("F:\\Java\\io\\client.txt");
	File f1 = new File("F:\\Java\\io\\client1.txt");
	int n;
	javax.swing.JFrame jf;
	List<String> liststr = new ArrayList<String>();

	public LoginUI() {
		try {
			s = new Socket("192.168.31.146", 8888);
			out = s.getOutputStream();
			System.out.println(out);
			in = s.getInputStream();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public LoginUI(List<String> liststr) {
		this.liststr = liststr;
	}

	public void showFrame() {
		// 创建窗体对象
		jf = new javax.swing.JFrame();
		jf.setSize(320, 390);
		jf.setTitle("登录界面");
		// 创建退出进程
		jf.setDefaultCloseOperation(3);
		// 居中显示
		jf.setLocationRelativeTo(null);
		// 流式布局管理器
		java.awt.FlowLayout f = new java.awt.FlowLayout();
		jf.setLayout(f);// 设置窗体为流式布局
		// 画图片
		javax.swing.ImageIcon im = new javax.swing.ImageIcon(this.getClass()
				.getResource("登录图.jpg"));
		// 标签
		javax.swing.JLabel jla = new javax.swing.JLabel(im);
		java.awt.Dimension di = new java.awt.Dimension(300, 200);
		jla.setPreferredSize(di);
		jf.add(jla);
		// 在输入账号密码前先读取输入流里的数据，看是否有上次保存的账号和密码
		try {
			fileInput(this.f);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 文本框
		tf = new TextField(26);
		tf.setText(id);
		jf.add(tf);
		// 注册账号按钮
		javax.swing.JButton jbu1 = new javax.swing.JButton("注册账号");
		jf.add(jbu1);// 把按钮添加到窗体上
		// 文本框
		tf2 = new TextField(26);
		tf2.setText(password);
		tf2.setEchoChar('*');
		jf.add(tf2);
		// 找回密码按钮
		javax.swing.JButton jbu2 = new javax.swing.JButton("找回密码");
		jf.add(jbu2);
		// 复选框
		cb = new Checkbox("记住密码");
		jf.add(cb);
		cb2 = new Checkbox("自动登录");
		jf.add(cb2);
		// 读取io流中保存的n的值
		try {
			fileInput1(f1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * 根据n值判断复选框状态
		 */
		// 如果之前存入的是自动登录
		if (n == 1) {
			cb2.setState(true);
			id = tf.getText();
			password = tf2.getText();
			try {
				// 用户名和密码传给服务器
				String strzh = id;
				String strmm = password;
				out.write(("login|" + strzh + "|" + strmm + "\r").getBytes());
				System.out.println("name" + id);
				System.out.println("password" + password);
				out.flush();
				// 读服务器反馈的数据
				String rdlongin = "";
				byte[] b = new byte[1024];
				in.read(b);
				rdlongin = new String(b).trim();
				System.out.println("rdlongin:" + rdlongin);
				if (rdlongin.equals("success")) {
					in.read(b);
					while (!new String(b).trim().equals("end")) {
						in.read(b);
						liststr.add(new String(b).trim());
					}
					for (int i = 0; i < liststr.size(); i++) {
						System.out.println(liststr.get(i));
					}
					new ChatUI(s, id, liststr).UI();
				} else {
					System.out.println("登录失败");
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		// 如果之前存入的不是自动登录
		if (n == 0) {
			cb2.setState(false);
		}
		// 按钮
		javax.swing.JButton jbu = new javax.swing.JButton("登录");
		java.awt.Dimension d2 = new java.awt.Dimension(200, 30);
		jbu.setPreferredSize(d2);
		jf.add(jbu);// 把按钮添加到窗体上
		// 设置窗体可见
		jf.setVisible(true);
		g = jf.getGraphics();
		g.drawImage(im.getImage(), 0, 0, 200, 200, null);
		jbu.addActionListener(this);
		jbu1.addActionListener(this);
	}

	/*
	 * 保存密码时用的io流储存账号和密码
	 */
	// io数据输出流
	public void fileOutput(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		DataOutputStream dos = new DataOutputStream(fos);
		int length = id.length();
		dos.writeByte(length);
		for (int i = 0; i < length; i++) {
			dos.writeChar(id.charAt(i));
		}
		int length1 = password.length();
		dos.writeByte(length1);
		for (int i = 0; i < length1; i++) {
			dos.writeChar(password.charAt(i));
		}
		fos.flush();
		fos.close();
	}

	// io数据输入流
	public void fileInput(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		int length = dis.readByte();
		StringBuffer strB = new StringBuffer();
		for (int i = 0; i < length; i++) {
			strB.append(dis.readChar());
		}
		String name = new String(strB);
		this.id = name;
		int length1 = dis.readByte();
		StringBuffer strB1 = new StringBuffer();
		for (int i = 0; i < length1; i++) {
			strB1.append(dis.readChar());
		}
		String password = new String(strB1);
		this.password = password;
		n = dis.readInt();
		fis.close();
	}

	/*
	 * 自动登录时用的io流
	 */
	// io数据输出流
	public void fileOutput1(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		DataOutputStream dos = new DataOutputStream(fos);
		dos.writeByte(n);
		fos.flush();
		fos.close();
	}

	// io数据输入流
	public void fileInput1(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		int n = dis.readByte();
		this.n = n;
		fis.close();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("登录")) {
			id = tf.getText();
			password = tf2.getText();
			try {
				// 用户名和密码传给服务器
				String strzh = id;
				String strmm = password;
				out.write(("login|" + strzh + "|" + strmm + "\r").getBytes());
				System.out.println("name" + id);
				System.out.println("password" + password);
				out.flush();
				/*
				 *  读服务器反馈的数据
				 *  先只读一个
				 */
				String rdlongin = "";
				byte[] b = new byte[1024];
				in.read(b);
				rdlongin = new String(b).trim();
				BufferedReader read=new BufferedReader(new InputStreamReader(in));
				String p=null;
				if (rdlongin.equals("success")) {
					//读取好友
					p=read.readLine();
					System.out.println("读取好友的第一个"+p);
					//如果不结束就一直读
					while (!p.trim().equals("end")) {
						liststr.add(p.trim());
						p=read.readLine();
						System.out.println("    "+p);
					}
//					System.out.println("执行");
					//打印读取到的好友
					for (int i = 0; i < liststr.size(); i++) {
						System.out.println(liststr.get(i));
					}
					new ChatUI(s, id, liststr).UI();
					jf.dispose();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		if (e.getActionCommand().equals("注册账号")) {
			new RegisterUI(s).showFrame();
		}
		// 当选择记住密码时
		if (cb.getState()) {
			try {
				fileOutput(f);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// 当选择自动登录时和不选择自动登录时
		if (cb2.getState()) {
			n = 1;
			try {
				fileOutput1(f1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (!cb2.getState()) {
			n = 0;
			try {
				fileOutput1(f1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new LoginUI().showFrame();
	}
}
