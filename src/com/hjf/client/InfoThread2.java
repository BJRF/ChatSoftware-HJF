package com.hjf.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTextArea;


public class InfoThread2 implements Runnable{
	InputStream in;
	OutputStream out;
	Socket s;
	String strmz;
	String myname;
	String filename;
	JTextArea jta;
	
	File f =new File("D:\\3.png");
	public InfoThread2(Socket s,String strmz,String myname,String filename){
		this.s=s;
		this.strmz=strmz;
		this.myname=myname;
		this.filename=filename;
		try {
			out=s.getOutputStream();
			in = s.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run() {
		//写信息给服务器
		try {
			out.write((myname+"|"+ strmz + "\r\n")
					.getBytes());
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		//写文件给服务器
		InputStream fim = null;
		try {
			fim = new FileInputStream(f);
			byte[] k = new byte[1000 * 1024];
			int l = fim.read(k);
			while (l != -1) {
				out.write(k, 0, l);
				l = fim.read(k);
				System.out.println("...............................");
			}
			System.out.println("............");
			fim.close();
			s.shutdownOutput();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
