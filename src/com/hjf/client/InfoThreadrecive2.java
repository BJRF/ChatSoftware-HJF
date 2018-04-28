package com.hjf.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

public class InfoThreadrecive2 implements Runnable{
	InputStream in;
	OutputStream out;
	Socket s;
	String myname;
	String strmz;
	String filename;
	JTextArea jta;
	
	public InfoThreadrecive2(Socket s,String myname,String strmz,JTextArea jta,String filename){
		this.s=s;
		this.myname=myname;
		this.strmz=strmz;
		this.filename=filename;
		this.jta=jta;
		try {
			out=s.getOutputStream();
			in = s.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run() {
		System.out.println(filename.trim());
		File f=new File("F:\\Java\\cs通信文件\\客户端2\\"+filename.trim());
		
		try {
			System.out.println("test1");
//			out.write((myname+"|"+ strmz + "|"+filename+"\r\n")
//					.getBytes());
			out.write((myname+"|"+ strmz + "\r\n")
					.getBytes());
		} catch (IOException e3) {
//			 TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		// 等待服务器传入信息
//				byte[] b = new byte[1024];
//				try {
//					in.read(b);
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//				String s = new String(b);
//				char[] c = s.toCharArray();
//				int one = -1;
//				String type=null;
//				/*
//				 * 读取传入的信息之后开始进行判断 如果遇到了| |前面的则为type类型
//				 */
//				for (int i = 0; i < c.length; i++) {
//					if (c[i] == '|') {
//						// one是第一个|的位置
//						one = i;
//						type = new String(c, 0, i);
//						break;
//					}
//				}	
//				if(type.equals("receivefile")){
//					String id = new String(c, one + 1, c.length - one - 1);
					OutputStream fom=null;
					try {
//						byte[] by=new byte[100*1024];
//						int i=in.read(by);
//						String filename=new String(by);
						
						
//						fom=new FileOutputStream(new File("F:\\Java\\cs通信文件\\客户端2\\"+filename));
						fom=new FileOutputStream(f);
						System.out.println("test2");
//						int t=0;
//						while((t=in.read())!=-1){
//							fom.write(t);
//							System.out.println("t:  "+t);
//							fom.flush();
//						}	
						byte[] k=new byte[1000*1024];
						int l=in.read(k);
						while(l!=-1){
							System.out.println("test3");
							fom.write(k, 0, l);
							l=in.read(k);
						}
						jta.append("文件"+filename+"发送成功" + "\n");
						fom.flush();
						System.out.println("test");
						fom.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
//				}	
				
	}
}
