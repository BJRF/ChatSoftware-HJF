package com.hjf.client;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatUI extends JFrame implements ActionListener {
	// 输入区域
	JTextField jtf1;
	JTextField jtf2;
	// 文本域
	JTextArea jta;
	Socket s;
	OutputStream out;
	InputStream in;
	// 发消息者的名字
	String name;
	String info;
	// 自己的名字
	String myname;
	//选择框
	Choice c,c1;
	// 滚动面板
	JScrollPane jsp;
	// 面板
	JPanel j;
	//名字的队列
	List<String> liststr;
	//文件
//	File f =new File("D:\\3.png");
	/*
	 * 因为所有的接受消息都要在同一个线程，所以要先传入添加好友界面的类
	 */
	AddFriends adf;

	public ChatUI() {
	}
	public ChatUI(Socket s, String name,List<String> liststr) {
		try {
			this.liststr=liststr;
			this.s = s;
			this.myname = name;
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void UI() {
		this.setLayout(new BorderLayout());
		// 文本域
		jta = new JTextArea();
		// 滚动面板
		jsp = new JScrollPane(jta);
		// 文本区域不可编辑
		jta.setEditable(false);
		j = new JPanel();
		// 输入区域
		jtf1 = new JTextField(30);
		JButton jb;
		jb = new JButton("发送");
		// 为按钮添加事件
		jb.addActionListener(this);
		// 聊天输入信息的文本
		Label la1 = new Label("聊天信息");
		j.add(la1);
		j.add(jtf1);
		j.add(jb);
		// 文件选择框的按钮
		JButton jb1;
		jb1 = new JButton("选择文件");
		j.add(jb1);
		jb1.addActionListener(this);
		JButton jb2;
		jb2 = new JButton("添加好友");
		j.add(jb2);
		jb2.addActionListener(this);
		// 聊天输入对象的文本
		Label la2 = new Label("聊天对象");
		j.add(la2);
		//聊天显示好友的选择框
		c1=new Choice();
		for(int i=0;i<liststr.size();i++){
			c1.add(liststr.get(i));
		}
		j.add(c1);
		// 输入聊天对象的区域
		jtf2 = new JTextField(6);
		j.add(jtf2);
		//输入群聊私聊的选择框
		c = new Choice();
		c.add("群聊");
		c.add("私聊");
		j.add(c);
		setSize(10, 50);
		this.add(jsp, BorderLayout.CENTER);
		this.add(j, BorderLayout.SOUTH);
		this.setTitle("聊天窗口");
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);
		adf = new AddFriends(s);
		InfoThread inth = new InfoThread(s, jta, adf, this,myname,name);
		inth.start();
	}

	public void actionPerformed(ActionEvent e) {
		// 当点击添加好友的时候
		if (e.getActionCommand().equals("添加好友")) {
			adf.AddFriendsUI();
		}
		// 当点击文件的时候
		if (e.getActionCommand().equals("选择文件")) {
			String filename = null;
			String FILE;
			JFileChooser jfc = new JFileChooser();
			/*
			 * 选择文件的模式
			 * 直选文件或者可以选择目录或者文件
			 */
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  
			//给文件选择框一个界面并命名
		    jfc.showDialog(new JLabel(), "选择");  
		    File file=jfc.getSelectedFile();  
//		    if(file.isDirectory()){  
//		    System.out.println("文件夹:"+file.getAbsolutePath()); 
//		    }
		    if(file.isFile()){  
		       FILE=file.getAbsolutePath(); 
		       System.out.println(FILE);
		       filename=jfc.getSelectedFile().getName();
		       System.out.println(filename);
		    }    
		    //发文件对象的id
		    String strid = jtf2.getText();
		    /*
		     * 先给服务器发一条带有标识符、对象、文件名字的信息
		     * 让服务器打开新的端口并储存发过去的文件名
		     */
		    try {
				out.write(("sendfile" + "|" + strid + "|"+filename+"\r\n")
						.getBytes());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Socket s1 = null;
			/*
			 * 之后客户端打开一个新的端口
			 * 这个端口用来先发消息给服务器的接受文件的端口
			 * 并且用这个端口的线程
			 * 开始发送文件给服务器
			 */
			try {
				s1 = new Socket("192.168.31.146", 8889);
			} catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			InfoThread2 inth2=new InfoThread2(s1,strid,myname,filename);
			new Thread(inth2).start();
		    
			
			//用
//			name = jtf2.getText();
//			String strmz = name;
//			try {
//				out.write(("sendfile" + "|" + strmz + "\r\n")
//						.getBytes());
//			} catch (IOException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//			
//			Socket s1 = null;
//			try {
//				s1 = new Socket("192.168.31.182", 8889);
//			} catch (UnknownHostException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			} catch (IOException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//			InfoThread2 inth2=new InfoThread2(s1,strmz,myname);
//			new Thread(inth2).start();
			
			
			
			
//				InputStream fim=null;
//			try {
//				fim=new FileInputStream(f);
//				byte[] k=new byte[100*1024];
//				int l=fim.read(k);
//				while(l!=-1){
//				out.write(k,0,l);
//				l=fim.read(k);
//				System.out.println("...............................");
//				}
//				System.out.println("............");
//				fim.close();
//				s.shutdownOutput();	
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
			
			
			
		}
		// 当点击发送的时候
		if (e.getActionCommand().equals("发送")) {
			info = jtf1.getText();
			name = jtf2.getText();
			// 群聊
			if (c.getSelectedIndex() == 0) {
				// 判断输入的信息是否为空
				if (info == null || info.trim().equals("")) {
					jta.append("聊天信息不能为空" + "\n");
				}
				// 将输入的文本域内容置为空
				jtf1.setText("");
				// 如果聊天信息不是空，则输出并与服务器连接
				if (info != null && !info.trim().equals("")) {
					try {
						// 自己的名字和发送对象的名字和信息传给服务器
						String strxx = info;
						out.write(("all" + "|" + strxx + "\r").getBytes());
						out.flush();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
			// 私聊
			if (c.getSelectedIndex() == 1) {
				// 判断输入的信息是否为空
				if (info == null || info.trim().equals("")) {
					jta.append("聊天信息不能为空" + "\n");
				}
				// 判断输入的聊天对象是否为空
				if (name == null || name.trim().equals("")) {
					jta.append("聊天对象不能为空" + "\n");
				}
				jtf1.setText("");
				// 如果聊天对象和信息都不为空
				if (info != null && !info.trim().equals("") && name != null
						&& !name.trim().equals("")) {
					try {
						// 自己的名字和发送对象的名字和信息传给服务器
						name = jtf2.getText();
						String strmmz = myname;
						String strmz = name;
						String strxx = info;
						out.write(("single" + "|" + strmz + "|" + strxx + "\r")
								.getBytes());
						System.out.println("我的名字" + strmmz + "名字");
						out.flush();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
		}
	}
//	 public static void main(String[] args) {
//	 new ChatUI().UI();
//	 }
}