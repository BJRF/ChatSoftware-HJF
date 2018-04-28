package com.hjf.client;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

//读服务器消息的线程
public class InfoThread extends Thread {
	Socket s;
	InputStream in;
	OutputStream out;
	JTextArea jta;
	ChatUI chatu;
//	String type;
	AddFriends adf;
	String myname;
	String strmz;

	// 构造方法重传入线程里所需要的属性
	public InfoThread(Socket s, JTextArea jta, AddFriends adf, ChatUI chatu,String myname,String strmz) {
		this.s = s;
		this.jta = jta;
		this.adf = adf;
		this.chatu = chatu;
		this.myname=myname;
		this.strmz=strmz;
		try {
			in = s.getInputStream();
			out = s.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 线程的方法体
	public void run() {
		// read是阻塞方法，可以使用while（true）
		while (true) {
			try {
				// 等待服务器传入信息
				byte[] b = new byte[1024];
				in.read(b);
				String s = new String(b);
				char[] c = s.toCharArray();
				int one = -1;
				String type=null;
				/*
				 * 读取传入的信息之后开始进行判断 如果遇到了| |前面的则为type类型
				 */
				for (int i = 0; i < c.length; i++) {
					if (c[i] == '|') {
						// one是第一个|的位置
						one = i;
						type = new String(c, 0, i);
						break;
					}
				}	
				// 当类型是收发消息的时候
				if (type.equals("send") || type.equals("receive")) {
					int two = -1;
					for (int i = one + 1; i < c.length; i++) {
						if (c[i] == '|' && two == -1) {
							// two是第二个|的位置
							two = i;
							break;
						}
					}
					/*
					 * name是第二个字符串 也就是第一个|和第二个|之间的字符串 info是第三个字符串
					 * 也就是第二个|和第三个|之间的字符串
					 */
					String name = new String(c, one + 1, two - one - 1);
					String info = new String(c, two + 1, c.length - two - 1);

					// 当是发消息的时候
					if (type.equals("send")) {
						// 在文本域里输出聊天信息
						jta.append(name + ": " + chatu.info + "\n");
						// 当时收消息的时候
					} else if (type.equals("receive")) {
						// 在文本域里输出聊天信息
						jta.append(name + ": " + info + "\n");
					}
				}
				// 当类型是添加朋友的时候
				if (type.equals("addfriendsuccess")) {
					// 这里要从第一个|开始循环
					for (int i = one + 1; i < c.length; i++) {
						if (c[i] == '|') {
							break;
						}
					}
					// 添加朋友除了type时只有name一个字符串返回
					String name = new String(c, one + 1, c.length - one - 1);
					adf.jtf2.setText("添加"+name.trim()+"成功");
					// 如果类型是addfriendfail
				} else 
					if (type.equals("addfriendfail")) {
					adf.jtf2.setText("添加失败");
				}
				
				
				/*
				 * 如果收到文件的接受信息
				 * 就开始打开接受文件的端口
				 * 并且开启此端口的线程
				 * 用来接受文件
				 */
				if(type.equals("receivefile")){
				int two = -1;
				for (int i = one + 1; i < c.length; i++) {
					if (c[i] == '|' && two == -1) {
						// two是第二个|的位置
						two = i;
						break;
					}
				}
				/*
				 * name是第二个字符串 也就是第一个|和第二个|之间的字符串 info是第三个字符串
				 * 也就是第二个|和第三个|之间的字符串
				 */
				String id = new String(c, one + 1, two - one - 1);
				String filename = new String(c, two + 1, c.length - two - 1);
//				OutputStream fom=null;
				Socket s2 = null;
				//开一个新端口
				try {
					s2 = new Socket("192.168.31.146", 8889);
				} catch (UnknownHostException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				InfoThreadrecive2 inthre2=new InfoThreadrecive2(s2,myname,id,jta,filename);
				new Thread(inthre2).start();
				
				
//				try {
//					fom=new FileOutputStream(new File("D:\\4.png"));
////					int t=0;
////					while((t=in.read())!=-1){
////						fom.write(t);
////						System.out.println("t:  "+t);
////						fom.flush();
////					}	
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					byte[] k=new byte[100*1024];
//					int l=in.read(k);
//					while(l!=-1){
//						System.out.println("qeqwe");
//						fom.write(k, 0, l);
//						l=in.read(k);
//					}
//					fom.flush();
//					System.out.println("test");
//					fom.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
			}	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
